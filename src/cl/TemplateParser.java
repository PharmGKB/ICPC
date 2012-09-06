package cl;

import org.apache.log4j.Logger;
import org.pharmgkb.ExcelParser;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 8/28/12
 */
public class TemplateParser {
  private static final Logger sf_logger = Logger.getLogger(TemplateParser.class);
  private File m_templateFile = null;

  public static void main(String args[]) {
    try {
      HibernateUtils.init();

      TemplateParser parser = new TemplateParser();
      parser.parseCommandLineArgs(args);
      parser.parseFile();
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't run parser", ex);
    }
    finally {
      HibernateUtils.shutdown();
    }
  }

  private void parseFile() throws Exception {
    ExcelParser parser = new ExcelParser(getTemplateFile());
    parser.parse();
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
      File templateFile = new File(cliHelper.getValue("-f"));
      if (templateFile.exists()) {
        setTemplateFile(templateFile);
      }
      else {
        throw new Exception("File not found "+cliHelper.getValue("-f"));
      }
    }
  }


  public File getTemplateFile() {
    return m_templateFile;
  }

  public void setTemplateFile(File templateFile) {
    m_templateFile = templateFile;
  }
}
