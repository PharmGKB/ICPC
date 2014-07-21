package org.pharmgkb.util;

import org.apache.commons.lang.StringUtils;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Helper methods for dealing with specific ICPC needs
 *
 * @author whaleyr
 */
public class IcpcUtils {
  public static final String NA = "NA";

  private static final SimpleDateFormat m_fileDateFormatter = new SimpleDateFormat("yyyyMMdd-HHmm");

  /**
   * Determines whether a String value can be considered "blank". Blank in this case meaning:
   * <ul>
   *   <li>null value</li>
   *   <li>all whitespace</li>
   *   <li>equals "NA" (case-insensitive)</li>
   *   <li>equals "N/A" (case-insensitive)</li>
   *   <li>equals "unknown" (case-insensitive)</li>
   *   <li>equals "not available" (case-insensitive)</li>
   * </ul>
   * @param string
   * @return
   */
  public static boolean isBlank(String string) {
    String trimString = StringUtils.trimToNull(string);
    return StringUtils.isBlank(trimString) || trimString.equalsIgnoreCase("na") || trimString.equalsIgnoreCase("n/a") || trimString.equalsIgnoreCase("unknown") || trimString.equalsIgnoreCase("not available");
  }

  /**
   * Constructs the default name and path for an ouptut file based on the input data file.
   * @param inputFile the input data file
   * @return a new filename to ouput to
   */
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
}
