package org.pharmgkb.util;

import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import org.apache.commons.lang.StringUtils;
import org.hibernate.Session;
import org.pharmgkb.enums.Property;
import org.pharmgkb.model.Sample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * Helper methods for dealing with specific ICPC needs
 *
 * @author Ryan Whaley
 */
public class IcpcUtils {
  public static final String NA = "NA";
  public static final Pattern VALIDATOR_BINARY_REQ = Pattern.compile("0|1|[Yy]es|[Nn]o");
  public static final Pattern VALIDATOR_BINARY = Pattern.compile("([01]|99)");
  public static final Pattern VALIDATOR_THREE = Pattern.compile("([012]|99)");
  public static final Pattern VALIDATOR_BASES = Pattern.compile("^[AaTtGgCc/]+$");
  public static final Pattern VALIDATOR_NUMBER = Pattern.compile("(-?(\\d+)?\\.?(\\d+)?)(\\s+)?(mg|mg/day|mg/L|uM)?");
  public static final Pattern VALIDATOR_TIME = Pattern.compile("(\\d{1,2}:\\d{2}:\\d{2})|((\\d+)?\\.?(\\d+)?)");

  private static final SimpleDateFormat m_fileDateFormatter = new SimpleDateFormat("yyyyMMdd-HHmm");
  private static final List<Integer> sf_projectsWithBadCreatinine = Lists.newArrayList(7, 37);
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
  private static final Logger sf_logger = LoggerFactory.getLogger(IcpcUtils.class);

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
   * @param string a String
   * @return true if the string has any known "blank" value, including null
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

    String newExtension = new StringBuilder()
        .append(".")
        .append(getTimestamp())
        .append(".xls")
        .toString();
    String outputFileName = inputFile.getName().replaceAll("\\.xls", newExtension);

    return new File(outputDirectoryName, outputFileName);
  }

  /**
   * Make a RegEx pattern that will validate data against the given enum.
   * @param enumToValidate the enum to validate the data against
   * @return a RegEx pattern that can be used to validate data
   */
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

  /**
   * Calculate the BMI for a subject using the properties available for it, or if the BMI has already been specified
   * use that. If BMI is specified as "0", try to calculate it.
   * Assumes height is stored in cm and weight is stored in kg.
   *
   * @param sample a Sample object
   * @return the BMI for the Sample as a String to two decimal
   */
  public static String calculateBmi(Sample sample) {
    if (!isBlank(sample.getProperties().get(Property.BMI)) && !sample.getProperties().get(Property.BMI).equals("0")) {
      return sample.getProperties().get(Property.BMI);
    }
    else if (!isBlank(sample.getProperties().get(Property.WEIGHT)) && !isBlank(sample.getProperties().get(Property.HEIGHT))) {
      Double weight = Double.valueOf(sample.getProperties().get(Property.WEIGHT));
      Double height = Double.valueOf(sample.getProperties().get(Property.HEIGHT))/100;

      if (height==0d || weight==0d) {
        return IcpcUtils.NA;
      }

      Double bmi = weight/(Math.pow(height, 2d));
      return String.format("%.1f", bmi);
    }
    else {
      return NA;
    }
  }

  /**
   * Converts the creatinine level from µmol/L to mg/dL. Assumes the given sample is storing the Creatinine level in
   * µmol/L.
   * @param sample a Sample object
   * @return the Creatinine level in mg/dL
   */
  public static String convertCreatinine(Sample sample) {
    if (isBlank(sample.getProperties().get(Property.CREATININE))) {
      return NA;
    }
    if (sf_projectsWithBadCreatinine.contains(sample.getProject())) {
      Double level = Double.valueOf(sample.getProperties().get(Property.CREATININE));

      return String.format("%.2f", level/88.4d);
    }
    else {
      return sample.getProperties().get(Property.CREATININE);
    }
  }

  /**
   * Make the category value for creatinine levels, less than 2.5 gets a "0", more gets a "1", all other values get
   * "NA". If the number can not be converted from the String will return an "NA" and log a warning. If the creatinine
   * category already exists it will return that.
   * @param sample a Sample object
   * @return a string, enumerated value for the creatinine category
   */
  public static String calculateCreatinineCat(Sample sample) {
    if (!isBlank(sample.getProperties().get(Property.CREATININE_CAT))) {
      return sample.getProperties().get(Property.CREATININE_CAT);
    }

    if (isBlank(sample.getProperties().get(Property.CREATININE))) {
      return NA;
    }
    else {
      try {
        Double level = Double.valueOf(sample.getProperties().get(Property.CREATININE));

        if (level < 2.5d) {
          return "0";
        } else {
          return "1";
        }
      }
      catch(Exception ex) {
        sf_logger.warn("Error making value for {}: {}", sample.getSubjectId(), sample.getProperties().get(Property.CREATININE));
        return NA;
      }
    }
  }

  public static String getTimestamp() {
    return m_fileDateFormatter.format(new Date());
  }
}
