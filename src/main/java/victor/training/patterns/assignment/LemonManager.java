package victor.training.patterns.assignment;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import victor.training.patterns.assignment.Lemon.LemonType;

import javax.sql.DataSource;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LemonManager {
   private static final Logger log = LoggerFactory.getLogger(LemonManager.class);
   private final DataSource dataSource;

   public LemonManager(DataSource dataSource) { // assume a DI container exists
      this.dataSource = dataSource;
   }

   @GetMapping
   List<Lemon> findNearbyLemons(@RequestParam String gisLocation, @RequestParam String username) throws IOException {
      String getUserProfileUrlTemplate = new ConfigManager().getConfig("getUserUrl");
      RequestEntity<?> requestEntity = RequestEntity.get(getUserProfileUrlTemplate, username) // TODO replace the line below
//      RequestEntity<?> requestEntity = RequestEntity.get("http://localhost:8081/user/{username}", username)
          .accept(MediaType.APPLICATION_JSON)
          .header("Api-Token", new ConfigManager().getConfig("apiToken"))
          .build();
      ResponseEntity<UserProfileDto> response = new RestTemplate().exchange(requestEntity, UserProfileDto.class);
      UserProfileDto userProfileDto = response.getBody();

      if (userProfileDto == null) {
         throw new IllegalArgumentException();
      }
      if (userProfileDto.getPhoneNumber() == null) {
         throw new IllegalArgumentException();
      }

      List<Lemon> allLemons = new JdbcTemplate(dataSource) // very ugly
          .query("SELECT ID, GIS_LOCATION, BATTERY_POWER, TYPE FROM LEMON", (rs, i) -> {
             Lemon lemon = new Lemon();
             lemon.setId(rs.getLong(1));
             lemon.setGisLocation(rs.getString(2));
             lemon.setBattery(rs.getInt(3));
             lemon.setType(LemonType.valueOf(rs.getString(4)));
             return lemon;
          });

      List<Lemon> matchingLemons = new ArrayList<>();
      for (Lemon lemon : allLemons) {
         if (GisUtil.getDistance(lemon.getGisLocation(), gisLocation) > 100) {
            log.debug("Reject due to too far");
            continue;
         }
         if (userProfileDto.getAverageRidePower() > lemon.getBattery()) {
            log.debug("Reject due to low power");
            continue;
         }
         if (userProfileDto.getAverageSpeed() > lemon.getType().maxSpeed) {
            log.debug("Reject due to too slow");
            continue;
         }
         matchingLemons.add(lemon);
      }

      new JdbcTemplate(dataSource)
          .update("INSERT INTO SEARCH_AUDIT(SEARCH_GIS, USERNAME, NO_RESULTS, DATE) values (?,?,?, SYSDATE)",
              gisLocation,
              username,
              matchingLemons.size());

      return matchingLemons;
   }


}
