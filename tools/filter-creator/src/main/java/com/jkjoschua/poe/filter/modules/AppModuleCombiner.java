package com.jkjoschua.poe.filter.modules;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Arrays;
import org.apache.commons.io.FileUtils;
import com.jkjoschua.poe.filter.modules.config.ConfigHandler;
import com.jkjoschua.poe.filter.modules.config.Config;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AppModuleCombiner {
  private static final String LINE =
      "# ===============================================================================================================";
  private static final String LINE_SEPERATOR = System.lineSeparator();
  private static Config config;

  public static void main(String[] args) throws IOException {
    config = ConfigHandler.getInstance().getConfig();

    AppModuleCombiner app = new AppModuleCombiner();
    app.start();
  }

  public void start() throws IOException {
    File[] files = getAllFiles();
    deleteTargetFileIfExists();

    File targetFile = createTargetFile();
    try (FileWriter fileWriter = new FileWriter(targetFile, true)) {

      for (File file : files) {
        String fileName = file.getName().replace(".filter", "");

        @SuppressWarnings("deprecation")
        String contentToWrite = FileUtils.readFileToString(file);

        fileWriter.write(LINE + LINE_SEPERATOR);
        fileWriter.write("# " + fileName.replace(".filter", ""));
        fileWriter.write(LINE_SEPERATOR);
        fileWriter.write(LINE + LINE_SEPERATOR);
        fileWriter.write(LINE_SEPERATOR);
        fileWriter.write(contentToWrite);
        fileWriter.write(LINE_SEPERATOR);
        fileWriter.write(LINE_SEPERATOR);

        log.info("combined file '{}'", fileName);
      }
    }
  }

  private File[] getAllFiles() {
    File[] files = new File(config.getModulesFolder()).listFiles();
    Arrays.sort(files);
    return files;
  }

  private void deleteTargetFileIfExists() throws IOException {
    Files.deleteIfExists(new File(config.getOutputFileName()).toPath());
  }

  private File createTargetFile() {
    return new File(config.getOutputFolder(), config.getOutputFileName());
  }

}
