package com.jkjoschua.poe.filter.modules.config;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import lombok.Data;

@Data
public class Config {
  private String modulesFolder = "all-modules";
  private String outputFolder = "/";
  private String outputFileName = "LootFilter.filter";

  private Config() {}

  public String toJsonString() {
    return new GsonBuilder().setPrettyPrinting().create().toJson(this);
  }

  public static Config fromJsonString(String json) {
    return new Gson().fromJson(json, Config.class);
  }

  public static Config loadFromFile(File file) throws IOException {
    return fromJsonString(new String(Files.readAllBytes(file.toPath())));
  }

  public void saveToFile(File file) throws IOException {
    try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
      writer.write(this.toJsonString());
    }
  }

  public static Config createDefault() {
    return new Config();
  }

}
