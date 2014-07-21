package cl;

import org.apache.log4j.Logger;
import org.pharmgkb.DnaExcelParser;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;

/**
 * Parses the DNA data submission file and loads the contents into the DB
 *
 * @author Ryan Whaley
 */
public class DnaParser {
  private static final Logger sf_logger = Logger.getLogger(DnaParser.class);
  private File m_file = null;

  /**
   * main method, parses the command line args and kicks off the data parser
   * @param args
   */
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

  /**
   * Parses the command line args with a <code>CliHelper</code> and validates the args
   * @param args array of String command line args
   * @throws Exception
   */
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

  /**
   * Executes the parser in {@link org.pharmgkb.DnaExcelParser}.
   * @throws Exception
   */
  private void parseFile() throws Exception {
    DnaExcelParser parser = new DnaExcelParser(getFile());
    parser.parse();
  }

  /**
   * Gets the DNA data file in Excel (.xlsx or .xls) format
   * @return the DNA data file in Excel (.xlsx or .xls) format
   */
  public File getFile() {
    return m_file;
  }

  public void setFile(File file) {
    m_file = file;
  }
}
