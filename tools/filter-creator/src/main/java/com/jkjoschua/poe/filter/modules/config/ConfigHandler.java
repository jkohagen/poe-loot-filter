package com.jkjoschua.poe.filter.modules.config;

import java.io.File;
import java.io.IOException;
import lombok.Getter;

public class ConfigHandler {
  private static ConfigHandler configHandler;
  @Getter
  private static String configName = "config.json";
  private Config config;
  private File configFile = new File("config.json");

  private ConfigHandler() {}

  public static ConfigHandler getInstance() {
    if (configHandler == null) {
      configHandler = new ConfigHandler();
    }

    return configHandler;
  }

  public Config getConfig() throws IOException {
    if (config == null) {
      loadConfig();
    }

    return config;
  }

  private void loadConfig() throws IOException {
    if (configFile.exists()) {
      config = Config.loadFromFile(configFile);
    } else {
      config = Config.createDefault();
      config.saveToFile(configFile);
    }
  }

}
