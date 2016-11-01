package cl;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Multimap;
import com.google.common.collect.TreeMultimap;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringEscapeUtils;
import org.apache.commons.lang.StringUtils;
import org.pharmgkb.ExcelParser;
import org.pharmgkb.Global;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.util.HibernateUtils;
import org.pharmgkb.util.IcpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author Ryan Whaley
 */
public class AnalyzePropertyCoverage extends CommonParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());
  private static final String sf_fileDescriptor = "A data file or file template to analyze";

  public static void main(String[] args) {
    HibernateUtils.init();
    
    try {
      AnalyzePropertyCoverage analyzePropertyCoverage = new AnalyzePropertyCoverage();
      analyzePropertyCoverage.parseCommandLineArgs(args);
      analyzePropertyCoverage.parseData();
    }
    catch (Exception ex) {
      sf_logger.error("Error updating samples", ex);
    }

    Global.shutdown();
    System.exit(0);
  }

  /**
   * Analyzes the template file, database properties, and data files. The analysis generates a <code>html</code> file
   * to display the results. This file is placed in the same directory as the template file.
   * @throws PgkbException can occur from IO or DB interaction.
   */
  public void parseData() throws PgkbException {
    Preconditions.checkNotNull(getDataFile());
    final String supported = "Currently<br/>Supported";
    final String original = "Original<br/>Template";

    List<String> fileNames = Lists.newArrayList(original,supported);
    Multimap<String,String> columnToFile = TreeMultimap.create(String.CASE_INSENSITIVE_ORDER, String.CASE_INSENSITIVE_ORDER);

    for (Property property : Property.values()) {
      columnToFile.put(StringUtils.strip(property.getDisplayName()), supported);
    }

    ExcelParser tParser;
    try {
      tParser = new ExcelParser();
    } catch (Exception e) {
      throw new PgkbException("Error initializing excel parser", e);
    }
    
    List<String> tColumns = tParser.analyze();
    for (String column : tColumns) {
      columnToFile.put(StringUtils.strip(column), original);
    }

    for (File file : FileUtils.listFiles(getDataFile(), new String[]{"xlsx","xls"}, false)) {
      sf_logger.info("analyzing " + file.getName());
      fileNames.add(file.getName());

      ExcelParser parser;
      try {
        parser = new ExcelParser(file);
      } catch (Exception ex) {
        throw new PgkbException("Error initializing excel parser", ex);
      }
      List<String> columns = parser.analyze();

      for (String column : columns) {
        columnToFile.put(StringUtils.strip(column), file.getName());
      }
    }

    try (FileWriter fw = new FileWriter(getDataFile() + "/output/property.analysis.report."+ IcpcUtils.getTimestamp()+".html")) {
      fw.write("<html><head><meta charset=\"utf-8\"><title>ICPC sample property analysis</title></head>" +
          "<link rel=\"stylesheet\" href=\"https://maxcdn.bootstrapcdn.com/bootstrap/3.2.0/css/bootstrap.min.css\">" +
          "<body>" +
          "<ul><li><b style=\"color:green;\">O</b> = column exists (not necessarily filled in)</li>" +
          "<li><b style=\"color:red;\">X</b> = column does not exist</li></ul>" +
          "<table class=\"table table-bordered table-striped\">\n");

      fw.write("<thead>");
      fw.write("<tr><th colspan=\"4\"></th><th style=\"text-align:center;\" colspan=\"17\">Project</th></tr>");
      fw.write("<tr><th>#</th><th>Column Name</th>");
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
        fw.write("<td>");
        fw.write(Integer.toString(row));
        fw.write("</td><td>");
        fw.write(StringEscapeUtils.escapeHtml(column));
        Property property = Property.lookupByName(column);
        if (property != null) {
          fw.write("<div style=\"margin-left:1em;\"><small>");
          fw.write(StringEscapeUtils.escapeHtml(property.getShortName()));
          fw.write("</small></div>");
        }
        fw.write("</td>");

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
          fw.write("<tr><th>#</th><th>Column Name</th>");
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

  String getFileDescriptor() {
    return sf_fileDescriptor;
  }
}
