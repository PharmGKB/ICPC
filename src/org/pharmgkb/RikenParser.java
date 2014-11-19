package org.pharmgkb;

import com.google.common.collect.Maps;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.pharmgkb.enums.Property;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.Map;

/**
 * Parser for the Riken data file
 *
 * @author Ryan Whaley
 */
public class RikenParser extends SupplementalParser {
  private static final Logger sf_logger = LoggerFactory.getLogger(DnaExcelParser.class);
  private static Map<Integer, Property> sf_columnMap = Maps.newHashMap();
  // maps excel sheet column number to the property name used in the database
  static {
    sf_columnMap.put(1, Property.RIKEN_PLATE_NUM);
    sf_columnMap.put(2, Property.RIKEN_LOCATION_NUM);
    sf_columnMap.put(3, Property.RIKEN_LOCATION);
    sf_columnMap.put(4, Property.RIKEN_ID);
  }

  public RikenParser(File file) throws Exception {
    super(file);
  }

  public void parse() throws Exception {
    sf_logger.info("Processing Riken file");
    Session session = null;
    try {
      session = HibernateUtils.getSession();
      Sheet sheet = getWorkbook().getSheetAt(0);

      for (int r=1; r<sheet.getLastRowNum(); r++) {
        Row row = sheet.getRow(r);
        if (row == null) {
          continue;
        }
        Cell sampleIdCell = row.getCell(sf_idxSampleId);
        String sampleId = StringUtils.strip(sampleIdCell.getStringCellValue());
        Sample sample = (Sample)session.get(Sample.class, sampleId);
        if (sampleId == null || sample == null) {
          continue;
        }

        for (Integer colIdx : sf_columnMap.keySet()) {
          Cell cell = row.getCell(colIdx);
          String cellValue = ExcelUtils.getStringValue(cell, getEvaluator());
          sample.getProperties().put(sf_columnMap.get(colIdx), cellValue);
        }
      }
      HibernateUtils.commit(session);
    }
    finally {
      HibernateUtils.close(session);
    }
  }
}
