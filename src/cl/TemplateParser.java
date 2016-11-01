package cl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import org.apache.commons.io.FileUtils;
import org.hibernate.Session;
import org.pharmgkb.ExcelParser;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.List;

/**
 * This class parses data files based on the Excel template file.
 *
 * @author Ryan Whaley
 */
public class TemplateParser extends CommonParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(TemplateParser.class);
  private static final String sf_fileDescriptor = "";
  private static final List<String> sf_truncateTableQueries = Lists.newArrayList(
          "truncate table samplesources cascade",
          "truncate table properties cascade",
          "truncate table samples cascade");

  /**
   * main method, parses command line args and either kicks off the data parsing or analysis
   * @param args array of String comamnd line args
   */
  public static void main(String args[]) {
    try {
      HibernateUtils.init();

      TemplateParser parser = new TemplateParser();
      parser.parseCommandLineArgs(args);
      parser.parseData();
    }
    catch (Exception ex) {
      sf_logger.error("Couldn't run parser", ex);
      System.exit(1);
    }
    finally {
      HibernateUtils.shutdown();
    }
    System.exit(0);
  }

  /**
   * Executes parsing of the data file or files
   */
  protected void parseData() throws PgkbException {
    try {
      if (getDataFile().isDirectory()) {
        parseDirectory(getDataFile());
      } else {
        parseFile(getDataFile());
      }
    }
    catch (Exception e) {
      throw new PgkbException("Error processing "+getDataFile(), e);
    }
  }

  /**
   * Parses the given <code>file</code> and loads the contents into the DB
   * @param file an Excel (.xlsx or .xls) template file with data in it
   */
  private void parseFile(File file) throws Exception {
    Preconditions.checkNotNull(file);
    Preconditions.checkArgument(file.exists(), "File does not exist: %s", file);

    ExcelParser parser = new ExcelParser(file);
    parser.clearSubjects();
    parser.parse();
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

    sf_logger.info("clearing the db tables for entry");
    Session session = null;
    try {
      session = HibernateUtils.getSession();
      for (String truncateQuery : sf_truncateTableQueries) {
        session.createSQLQuery(truncateQuery).executeUpdate();
      }
      HibernateUtils.commit(session);
    }
    finally {
      HibernateUtils.close(session);
    }

    for (File file : FileUtils.listFiles(directory, new String[]{"xlsx","xls"}, false)) {
      if (file.getName().startsWith("~")) {
        continue;
      }
      parseFile(file);
    }
  }

  @Override
  String getFileDescriptor() {
    return sf_fileDescriptor;
  }
}
