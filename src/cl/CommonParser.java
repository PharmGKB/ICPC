package cl;

import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.CliHelper;

import java.io.File;

/**
 * Common code for the command line parsers
 *
 * @author Ryan Whaley
 */
abstract class CommonParser {
  private File m_dataFile;

  protected abstract void parseData() throws PgkbException;

  abstract String getFileDescriptor();

  void parseCommandLineArgs(String args[]) throws Exception {
    CliHelper cliHelper = new CliHelper(getClass(), false);
    cliHelper.addOption("f", "file", getFileDescriptor(), "pathToFile");

    try {
      cliHelper.parse(args);
      if (cliHelper.isHelpRequested()) {
        cliHelper.printHelp();
        System.exit(1);
      }
    } catch (Exception ex) {
      throw new Exception("Error parsing arguments", ex);
    }

    if (cliHelper.hasOption("-f")) {
      File templateFile = new File(cliHelper.getValue("-f"));
      if (templateFile.exists()) {
        setDataFile(templateFile);
      }
      else {
        throw new Exception("File not found "+cliHelper.getValue("-f"));
      }
    }
  }

  File getDataFile() {
    return m_dataFile;
  }

  protected void setDataFile(File m_dataFile) {
    this.m_dataFile = m_dataFile;
  }
}
