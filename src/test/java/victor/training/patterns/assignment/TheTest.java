package victor.training.patterns.assignment;

import com.github.tomakehurst.wiremock.junit5.WireMockExtension;
import com.github.tomakehurst.wiremock.junit5.WireMockTest;
import org.assertj.core.api.Assertions;
import org.hamcrest.collection.IsCollectionWithSize;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.RegisterExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import javax.annotation.RegEx;
import javax.sql.DataSource;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@WireMockTest(httpPort = 8081)
public class TheTest {

   @Autowired
   MockMvc mockMvc;

   @Autowired
   DataSource dataSource;

   @Test
   void test() throws Exception {
       mockMvc.perform(get("/?username=john&gisLocation=abc"))
           .andExpect(status().isOk())
           .andExpect(jsonPath("$", hasSize(1)));

      Long auditNumber = new JdbcTemplate(dataSource).queryForObject("SELECT COUNT(*) FROM SEARCH_AUDIT", Long.class);
      assertThat(auditNumber).isEqualTo(1);
   }
}
