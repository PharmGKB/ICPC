package org.pharmgkb.util;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author whaleyr
 */
public class IcpcUtils {
  public static final String NA = "NA";

  private static final Logger sf_logger = Logger.getLogger(IcpcUtils.class);
  private static final Pattern sf_alleleRegex = Pattern.compile("\\*\\d+");
  private static final SimpleDateFormat m_fileDateFormatter = new SimpleDateFormat("yyyyMMdd-HHmm");

  public static boolean isBlank(String string) {
    String trimString = StringUtils.trimToNull(string);
    return StringUtils.isBlank(trimString) || trimString.equalsIgnoreCase("na") || trimString.equalsIgnoreCase("n/a") || trimString.equalsIgnoreCase("unknown") || trimString.equalsIgnoreCase("not available");
  }

  public static File getOutputFile(File inputFile) {
    String outputDirectoryName = inputFile.getParent()+"/output";

    Date date = new Date();
    String newExtension = new StringBuilder()
        .append(".")
        .append(m_fileDateFormatter.format(date))
        .append(".xls")
        .toString();
    String outputFileName = inputFile.getName().replaceAll("\\.xls", newExtension);

    return new File(outputDirectoryName, outputFileName);
  }

  /**
   * This method takes a String allele and returns a stripped version of that allele.  This way we don't have to store
   * every version of each allele.  For instance, *4K is stripped down to *4 for processing and mapping.
   * @param allele a String allele
   * @return a stripped version of <code>allele</code>
   */
  public static String alleleStrip(String allele) {
    String alleleClean = null;

    Matcher m = sf_alleleRegex.matcher(allele);
    if (allele.equalsIgnoreCase("Unknown")) {
      alleleClean = "Unknown";
    }
    else if (m.find()) {
      alleleClean = allele.substring(m.start(),m.end());
      if (allele.toLowerCase().contains("xn")) {
        alleleClean += "XN";
      }
      else if (allele.equalsIgnoreCase("*2a")) {
        alleleClean = "*2A";
      }
    }
    else {
      sf_logger.warn("Malformed allele found: " + allele);
    }

    return alleleClean;
  }
}
