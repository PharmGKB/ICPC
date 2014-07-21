package cl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.pharmgkb.ExcelParser;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.CliHelper;
import org.pharmgkb.util.HibernateUtils;
import org.pharmgkb.util.IcpcUtils;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * This class parses data files based on the Excel template file.
 *
 * @author Ryan Whaley
 */
public class TemplateParser {
  private static final Logger sf_logger = Logger.getLogger(TemplateParser.class);
  private File m_templateFile = null;
  private File m_templateDirectory = null;
  private boolean m_analyze = false;

  /**
   * main method, parses command line args and either kicks off the data parsing or analysis
   * @param args array of String comamnd line args
   */
  public static void main(String args[]) {
    try {
      HibernateUtils.init();

      TemplateParser parser = new TemplateParser();
      parser.parseCommandLineArgs(args);

      if (parser.isAnalyze()) {
        parser.analyzeDirectory();
      }
      else {
        parser.parse();
      }
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't run parser", ex);
    }
    finally {
      HibernateUtils.shutdown();
    }
  }

  /**
   * Executes parsing of the data file or files
   * @throws Exception
   */
  private void parse() throws Exception {
    if (getTemplateFile() != null) {
      parseFile(getTemplateFile());
    }
    else if (getTemplateDirectory() != null) {
      parseDirectory(getTemplateDirectory());
    }
  }

  /**
   * Parses the given <code>file</code> and loads the contents into the DB
   * @param file an Excel (.xlsx or .xls) template file with data in it
   * @throws Exception
   */
  private void parseFile(File file) throws Exception {
    Preconditions.checkNotNull(file);
    Preconditions.checkArgument(file.exists(), "File does not exist: %s", file);

    File outputFile = IcpcUtils.getOutputFile(file);

    ExcelParser parser = new ExcelParser(file);
    parser.parse(outputFile);
  }

  /**
   * Given the <code>directory</code>, find all the xlsx and xls files in it and run the <code>parseFile</code> method
   * on it.
   * @param directory a directory holding all the project data files to be parsed
   * @throws Exception can occur from data file parsing IO or DB interaction
   */
  private void parseDirectory(File directory) throws Exception {
    Preconditions.checkNotNull(directory);
    Preconditions.checkArgument(directory.exists(), "File does not exist: %s", directory);
    Preconditions.checkArgument(directory.isDirectory(), "File is not directory: %s", directory);

    for (File file : FileUtils.listFiles(directory, new String[]{"xlsx","xls"}, false)) {
      try {
        if (file.getName().startsWith("~")) {
          continue;
        }
        parseFile(file);
      }
      catch (IllegalArgumentException ex) {
        sf_logger.error("Couldn't parse file "+file, ex);
      }
    }
  }

  /**
   * Analyzes the template file, database properties, and data files. The analysis generates a <code>html</code> file
   * to display the results. This file is placed in the same directory as the template file.
   * @throws Exception can occur from IO or DB interaction.
   */
  private void analyzeDirectory() throws Exception {
    Preconditions.checkNotNull(getTemplateDirectory());
    final String supported = "Currently<br/>Supported";
    final String original = "Original<br/>Template";

    List<String> fileNames = Lists.newArrayList(original,supported);
    Multimap<String,String> columnToFile = TreeMultimap.create();

    Session session = HibernateUtils.getSession();
    List rez = session.createQuery("select description from IcpcProperty").list();
    for (Object result : rez) {
      columnToFile.put(StringUtils.strip((String) result), supported);
    }
    HibernateUtils.close(session);

    if (getTemplateFile() != null && getTemplateFile().exists()) {
      ExcelParser parser = new ExcelParser(getTemplateFile());
      List<String> columns = parser.analyze();

      for (String column : columns) {
        columnToFile.put(StringUtils.strip(column), original);
      }
    }

    for (File file : FileUtils.listFiles(getTemplateDirectory(), new String[]{"xlsx","xls"}, false)) {
      sf_logger.info("analyzing " + file.getName());
      fileNames.add(file.getName());

      ExcelParser parser = new ExcelParser(file);
      List<String> columns = parser.analyze();

      for (String column : columns) {
        columnToFile.put(StringUtils.strip(column), file.getName());
      }
    }

    try (FileWriter fw = new FileWriter(getTemplateFile().getParent() + "/property.analysis.report.html")) {
      fw.write("<html><head><meta charset=\"utf-8\"><title>ICPC sample property analysis</title></head>" +
              "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\">" +
              "<body>" +
              "<ul><li><b style=\"color:green;\">O</b> = column exists (not necessarily filled in)</li>" +
              "<li><b style=\"color:red;\">X</b> = column does not exist</li></ul>" +
              "<table class=\"table table-bordered table-striped\">\n");

      fw.write("<thead>");
      fw.write("<tr><th colspan=\"3\"></th><th style=\"text-align:center;\" colspan=\"17\">Project</th></tr>");
      fw.write("<tr><th>Column Name</th>");
        for (String file : fileNames) {
          String name = file.replace("project","").replace(".xlsx","");
          fw.write("<th>"+ name +"</th>");
        }
      fw.write("</tr>");
      fw.write("</thead><tbody>\n");

      int row=0;
      for (String column : columnToFile.keySet()) {
        row++;
        if (columnToFile.get(column).size()!=fileNames.size()) {
          fw.write("<tr class=\"warning\">");
        } else {
          fw.write("<tr>");
        }
        fw.write("<td>"+column+"</td>");

        if (columnToFile.get(column).size()==1 && columnToFile.get(column).contains(supported)) {
          sf_logger.warn("UNUSED PROPERTY IN DB: "+column);
        }
        if (!columnToFile.get(column).contains(supported)) {
          sf_logger.warn("UNSUPPORTED PROPERTY IN DB: "+column);
        }

        for (String file : fileNames) {
          if (columnToFile.get(column).contains(file)) {
            fw.write("<td><b style=\"color:green;\">O</b></td>");
          }
          else {
            fw.write("<td><b style=\"color:red;\">X</b></td>");
          }
        }
        fw.write("</tr>\n");

        if (row%20 == 0) {
          fw.write("<tr><th>Column Name</th>");
          for (String file : fileNames) {
            String name = file.replace("project", "").replace(".xlsx", "");
            fw.write("<th>" + name + "</th>");
          }
          fw.write("</tr>\n");
        }
      }

      fw.write("</tbody></table></body></html>\n");
    }
    catch (IOException ex) {
      throw new PgkbException("Couldn't write to output file", ex);
    }
  }

  /**
   * Parses the command line args and check for valid values and combinations of flags
   * @param args array of String comamnd line args
   * @throws Exception can occur if args are invalid
   */
  private void parseCommandLineArgs(String args[]) throws Exception{
    CliHelper cliHelper = new CliHelper(getClass(), false);

    cliHelper.addOption("f", "file", "ICPC excel template file to read", "pathToFile");
    cliHelper.addOption("d", "directory", "ICPC excel template directory to read", "pathToDirectory");
    cliHelper.addOption("a", "analyze", "Analyze column data");

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

    if (cliHelper.hasOption("-d")) {
      File templateDirectory = new File(cliHelper.getValue("-d"));
      if (templateDirectory.exists()) {
        setTemplateDirectory(templateDirectory);
      }
      else {
        throw new Exception("Directory doesn't exist "+cliHelper.getValue("-d"));
      }
    }

    setAnalyze(cliHelper.hasOption("-a"));
    if (isAnalyze()) {
      if (getTemplateDirectory() == null || getTemplateFile() == null) {
        throw new Exception("If doing analysis, you must specify the template file and the project data directory");
      }
    }

  }


  /**
   * Gets the data template file. Depending on mode, this is either a blank template file or a tempalte with data
   * @return a File of an Excel data file (.xlsx or .xls)
   */
  public File getTemplateFile() {
    return m_templateFile;
  }

  public void setTemplateFile(File templateFile) {
    m_templateFile = templateFile;
  }

  /**
   * Gets the directory of template files for each of the projects with data (.xls or .xlsx files)
   * @return the directory of template files for each of the projects with data
   */
  public File getTemplateDirectory() {
    return m_templateDirectory;
  }

  public void setTemplateDirectory(File templateDirectory) {
    m_templateDirectory = templateDirectory;
  }

  /**
   * Is this instance of the parser in analyze mode (won't write to the database)
   * @return true if the parser is in analyze mode, false otherwise
   */
  public boolean isAnalyze() {
    return m_analyze;
  }

  public void setAnalyze(boolean m_analyze) {
    this.m_analyze = m_analyze;
  }
}
