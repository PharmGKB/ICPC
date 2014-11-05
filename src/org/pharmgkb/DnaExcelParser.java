package org.pharmgkb;

import com.google.common.collect.Maps;
import com.sun.javafx.beans.annotations.NonNull;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * This class parses the companion DNA submission excel file that adds more data to the Subjects. This class assumes
 * that data already exists in the {@link org.pharmgkb.model.Sample} table.
 *
 * @author Ryan Whaley
 */
public class DnaExcelParser extends SupplementalParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(DnaExcelParser.class);
  private static final Pattern sf_patternSampleId = Pattern.compile("([Pp][Aa]\\d+).*");

  private static Map<Integer, Property> sf_columnMap = Maps.newHashMap();
  // maps excel sheet column number to the property name used in the database
  static {
    sf_columnMap.put(1, Property.DNA_PLATE);
    sf_columnMap.put(2, Property.DNA_LOCATION);
    sf_columnMap.put(3, Property.DNA_CONCENTRATION);
    sf_columnMap.put(4, Property.PICOGREEN_CONCENTRATION);
    sf_columnMap.put(5, Property.DNA_UNITS);
    sf_columnMap.put(6, Property.DNA_VOLUME);
    sf_columnMap.put(7, Property.DNA_ICPC_PLATE);
    sf_columnMap.put(8, Property.DNA_ICPC_TUBE);
  }

  /**
   * Public constructor
   *
   * @param file the Excel file that holds the DNA data
   * @throws Exception
   */
  public DnaExcelParser(@NonNull File file) throws Exception {
    super(file);
  }

  /**
   * Parses the file passed in during construction and writes collected data to the database.
   * @throws Exception
   */
  public void parse() throws Exception {
    Session session = null;

    try {
      session = HibernateUtils.getSession();

      for (int i=1; i<getWorkbook().getNumberOfSheets(); i++) {
        Sheet s = getWorkbook().getSheetAt(i);
        sf_logger.info("Processing sheet "+i+": "+s.getSheetName());

        int countSamples = 0;
        for (int r=0; r<s.getLastRowNum(); r++) {
          try {
            Row row = s.getRow(r);
            if (row!=null) {
              Cell cellSampleId = row.getCell(sf_idxSampleId);
              if (cellSampleId!=null && StringUtils.isNotBlank(cellSampleId.getStringCellValue())) {
                String sampleId = StringUtils.strip(cellSampleId.getStringCellValue());
                sampleId = StringUtils.strip(sampleId, ",");
                Matcher m = sf_patternSampleId.matcher(sampleId);
                if (m.matches()) {

                  Sample sample = (Sample)session.get(Sample.class, sampleId);
                  if (sample != null) {
                    countSamples++;
                    for (Integer propIdx : sf_columnMap.keySet()) {

                      try {
                        Cell cell = row.getCell(propIdx);
                        String propValue = ExcelUtils.getStringValue(cell, getEvaluator());
                        sample.getProperties().put(sf_columnMap.get(propIdx), propValue);
                      }
                      catch (Exception ex) {
                        throw new PgkbException("Error in column "+propIdx+" ("+sf_columnMap.get(propIdx)+")", ex);
                      }
                    }
                  }
                }
              }
            }
          }
          catch (Exception ex) {
            throw new PgkbException("Error on row "+(r+1), ex);
          }
        }
        HibernateUtils.commit(session);
        sf_logger.info("Saved data for "+countSamples+" subjects");
      }
    }
    finally {
      HibernateUtils.close(session);
    }
  }
}
