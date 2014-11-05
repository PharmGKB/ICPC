package cl;

import com.google.common.collect.Lists;
import org.pharmgkb.DnaExcelParser;
import org.pharmgkb.RikenParser;
import org.pharmgkb.SupplementalParser;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * Parses the DNA data submission file and loads the contents into the DB
 *
 * @author Ryan Whaley
 */
public class SupplementsParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(SupplementsParser.class);
  List<SupplementalParser> parsers = Lists.newArrayList();

  /**
   * main method, parses the command line args and kicks off the data parser
   * @param args command line args
   */
  public static void main(String args[]) {
    try {
      HibernateUtils.init();

      SupplementsParser parser = new SupplementsParser();
      parser.parseCommandLineArgs(args);
      parser.parse();
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

    cliHelper.addOption("d", "dnaFile", "DNA supplemental file", "pathToFile");
    cliHelper.addOption("r", "rikenFile", "Riken supplemental file", "pathToFile");

    try {
      cliHelper.parse(args);
      if (cliHelper.isHelpRequested()) {
        cliHelper.printHelp();
        System.exit(1);
      }
    } catch (Exception ex) {
      throw new Exception("Error parsing arguments", ex);
    }

    if (cliHelper.hasOption("-d")) {
      File file = new File(cliHelper.getValue("-d"));
      parsers.add(new DnaExcelParser(file));
    }
    if (cliHelper.hasOption("-r")) {
      File file = new File(cliHelper.getValue("-r"));
      parsers.add(new RikenParser(file));
    }
  }

  /**
   * Executes the parsers specified.
   * @throws Exception
   */
  private void parse() throws Exception {
    for (SupplementalParser parser : parsers) {
      parser.parse();
    }
  }
}
