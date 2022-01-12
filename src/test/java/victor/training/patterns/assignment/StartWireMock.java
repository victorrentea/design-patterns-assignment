package victor.training.patterns.assignment;

import com.github.tomakehurst.wiremock.standalone.WireMockServerRunner;

import java.io.File;

public class StartWireMock {
   public static void main(String[] args) {
      WireMockServerRunner.main(
          "--port", "8081",
          "--root-dir", new File(".", "src/test/resources").getAbsolutePath()
      );
   }
}
