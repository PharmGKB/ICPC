package cl;

import org.apache.log4j.Logger;
import org.pharmgkb.CombinedDataReport;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;

/**
 * @author Ryan Whaley
 */
public class ReportGeneratorCLI {
  private static final Logger sf_logger = Logger.getLogger(ReportGeneratorCLI.class);
  private File m_outputFile = null;

  public static void main(String args[]) {
    try {
      HibernateUtils.init();

      ReportGeneratorCLI generator = new ReportGeneratorCLI();
      generator.parseCommandLineArgs(args);
      generator.make();
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't make report", ex);
    }
    finally {
      HibernateUtils.shutdown();
    }
  }

  private void parseCommandLineArgs(String args[]) throws Exception{
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
      File file = new File(cliHelper.getValue("-f"));
      setOutputFile(file);
    }
  }

  private void make() throws PgkbException {
    CombinedDataReport report = new CombinedDataReport(getOutputFile());
    report.generate();
  }

  public File getOutputFile() {
    return m_outputFile;
  }

  public void setOutputFile(File outputFile) {
    m_outputFile = outputFile;
  }
}
