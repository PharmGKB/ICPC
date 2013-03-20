package cl;

import org.apache.log4j.Logger;
import org.pharmgkb.DnaExcelParser;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;

/**
 * @author Ryan Whaley
 */
public class DnaParser {
  private static final Logger sf_logger = Logger.getLogger(DnaParser.class);
  private File m_file = null;

  public static void main(String args[]) {
    try {
      HibernateUtils.init();

      DnaParser parser = new DnaParser();
      parser.parseCommandLineArgs(args);
      parser.parseFile();
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't parse DNA file", ex);
    }
    finally {
      HibernateUtils.shutdown();
    }
  }

  private void parseCommandLineArgs(String args[]) throws Exception {
    CliHelper cliHelper = new CliHelper(getClass(), false);

    cliHelper.addOption("f", "file", "ICPC excel template file to read", "pathToFile");

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
        setFile(templateFile);
      }
      else {
        throw new Exception("File not found "+cliHelper.getValue("-f"));
      }
    }
  }

  private void parseFile() throws Exception {
    DnaExcelParser parser = new DnaExcelParser(getFile());
    parser.parse();
  }

  public File getFile() {
    return m_file;
  }

  public void setFile(File file) {
    m_file = file;
  }
}
