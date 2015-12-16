package cl;

import org.pharmgkb.AbstractReport;
import org.pharmgkb.CombinedDataReport;
import org.pharmgkb.GwasReport;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;

/**
 * This class is the command line utility for generating reports based on data that's stored in the ICPC database.
 *
 * @author Ryan Whaley
 */
public class ReportGeneratorCLI {
  private static final Logger sf_logger = LoggerFactory.getLogger(ReportGeneratorCLI.class);
  private File m_outputDirectory = null;
  private Integer m_project = null;
  private String m_report = null;

  public static void main(String args[]) {
    try {
      HibernateUtils.init();

      ReportGeneratorCLI generator = new ReportGeneratorCLI();
      generator.parseCommandLineArgs(args);
      generator.make();
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't make report", ex);
      System.exit(1);
    }
    finally {
      HibernateUtils.shutdown();
    }
    System.exit(0);
  }

  private void parseCommandLineArgs(String args[]) throws Exception{
    CliHelper cliHelper = new CliHelper(getClass(), false);

    cliHelper.addOption("r", "report", "which report to generate", "report", true);
    cliHelper.addOption("d", "directory", "directory path to write to", "pathToDirectory", true);
    cliHelper.addOption("p", "project", "project to output", "projectId", false);

    try {
      cliHelper.parse(args);
      if (cliHelper.isHelpRequested()) {
        cliHelper.printHelp();
        System.exit(1);
      }
    } catch (Exception ex) {
      throw new Exception("Error parsing arguments", ex);
    }

    if (cliHelper.hasOption("-r")) {
      m_report = cliHelper.getValue("-r");
    }

    if (cliHelper.hasOption("-d")) {
      File file = new File(cliHelper.getValue("-d"));
      setOutputDirectory(file);
    }

    if (cliHelper.hasOption("-p")) {
      setProject(cliHelper.getIntValue("-p"));
    }
  }

  /**
   * Makes the report by kicking off the {@link org.pharmgkb.CombinedDataReport} generate method and outputs to the
   * specified file
   * @throws PgkbException
   */
  private void make() throws PgkbException {
    AbstractReport report;
    if (m_report.equalsIgnoreCase("combined")) {
      report = new CombinedDataReport(getOutputDirectory(), getProject());
    }
    else if (m_report.equalsIgnoreCase("gwas")) {
      report = new GwasReport(getOutputDirectory());
    }
    else {
      throw new PgkbException("No report type found for "+m_report);
    }
    report.generate();
  }

  /**
   * Gets the file to output to, will be overwritten if it already exists
   * @return the file to output to
   */
  public File getOutputDirectory() {
    return m_outputDirectory;
  }

  public void setOutputDirectory(File outputDirectory) {
    m_outputDirectory = outputDirectory;
  }

  public Integer getProject() {
    return m_project;
  }

  public void setProject(Integer project) {
    m_project = project;
  }
}
