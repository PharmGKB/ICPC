package org.pharmgkb.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Helper methods for dealing with specific ICPC needs
 *
 * @author whaleyr
 */
public class IcpcUtils {
  public static final String NA = "NA";
  public static final Pattern VALIDATOR_BINARY_REQ = Pattern.compile("0|1|[Yy]es|[Nn]o");
  public static final Pattern VALIDATOR_BINARY = Pattern.compile("([01]|99)");
  public static final Pattern VALIDATOR_THREE = Pattern.compile("([012]|99)");
  public static final Pattern VALIDATOR_BASES = Pattern.compile("^[AaTtGgCc/]+$");
  public static final Pattern VALIDATOR_NUMBER = Pattern.compile("((\\d+)?\\.?(\\d+)?)(\\s+)?(mg|mg/day|mg/L|uM)?");
  public static final Pattern VALIDATOR_TIME = Pattern.compile("(\\d{1,2}:\\d{2}:\\d{2})|((\\d+)?\\.?(\\d+)?)");

  private static final SimpleDateFormat m_fileDateFormatter = new SimpleDateFormat("yyyyMMdd-HHmm");
  private static final Set<String> sf_blankWords = Sets.newHashSet();
  static {
    sf_blankWords.add("99");
    sf_blankWords.add("na");
    sf_blankWords.add("n/a");
    sf_blankWords.add("unknown");
    sf_blankWords.add("not known");
    sf_blankWords.add("unavailable");
    sf_blankWords.add("not available");
    sf_blankWords.add("not determined");
  }

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
    String trimString = StringUtils.trimToNull(StringUtils.lowerCase(string));
    return StringUtils.isBlank(trimString) || sf_blankWords.contains(trimString);
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

  public static Pattern makeEnumValidator(Class enumToValidate) {
    try {
      Object[] constants = enumToValidate.getEnumConstants();
      Method m = enumToValidate.getDeclaredMethod("getShortName");

      Set<String> validNameSet = Sets.newHashSet();
      for (Object constant : constants) {
        String token = (String) m.invoke(constant);
        validNameSet.add(token);
        validNameSet.add(token.toLowerCase());
      }

      m = enumToValidate.getDeclaredMethod("getDisplayName");
      for (Object constant : constants) {
        String token = (String) m.invoke(constant);
        validNameSet.add(token);
        validNameSet.add(token.toLowerCase());
      }

      return Pattern.compile(Joiner.on("|").join(validNameSet));
    } catch (Exception e) {
      throw new RuntimeException("Couldn't make enum validator for "+enumToValidate.toString());
    }
  }

  public static String lookupFormat(Session session, String name) {
    return (String)session.createSQLQuery("select format from propertynames where name=:name")
            .setString("name", name).uniqueResult();
  }
}
