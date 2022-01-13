package victor.training.patterns.assignment;

import java.io.IOException;
import java.util.Properties;

public class ConfigManager {
   // TODO implement singleton (getInstance) to make sure properties are loaded a single time
   public String getConfig(String propertyKey) throws IOException {
      Properties properties = new Properties();
      System.out.println("Loading properties from disk");
      properties.load(LemonManager.class.getResourceAsStream("/application.properties"));

      return properties.getProperty(propertyKey);
   }
}
