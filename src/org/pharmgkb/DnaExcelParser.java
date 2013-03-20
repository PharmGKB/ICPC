package org.pharmgkb;

import com.google.common.collect.Maps;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.*;
import org.hibernate.Session;
import org.pharmgkb.util.HibernateUtils;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * @author Ryan Whaley
 */
public class DnaExcelParser {
  private static final Logger sf_logger = Logger.getLogger(DnaExcelParser.class);
  private static final Pattern sf_patternSampleId = Pattern.compile("([Pp][Aa]\\d+).*");

  private static final Integer sf_idxSampleId = 0;
  private static Map<Integer, String> sf_columnMap = Maps.newHashMap();
  // maps excel sheet column number to the property name used in the database
  static {
    sf_columnMap.put(1, "dna_plate");
    sf_columnMap.put(2, "dna_location");
    sf_columnMap.put(3, "dna_conc");
    sf_columnMap.put(4, "dna_units");
    sf_columnMap.put(5, "dna_volume");
    sf_columnMap.put(6, "dna_icpc_plate");
    sf_columnMap.put(7, "dna_icpc_tube");
  }

  private Workbook m_workbook = null;

  public DnaExcelParser(File file) throws Exception {
    if (file == null || !file.exists()) {
      throw new Exception("No file specified");
    }

    InputStream inputStream = null;
    try {
      inputStream = new FileInputStream(file);
      setWorkbook(WorkbookFactory.create(inputStream));
    }
    finally {
      IOUtils.closeQuietly(inputStream);
    }
  }

  public void parse() throws Exception {
    Session session = null;
    NumberFormat idFormatter = new DecimalFormat("##########.##");

    try {
      session = HibernateUtils.getSession();

      for (int i=1; i<getWorkbook().getNumberOfSheets(); i++) {
        Sheet s = getWorkbook().getSheetAt(i);
        sf_logger.info("Processing sheet "+i+": "+s.getSheetName());

        int countSamples = 0;
        for (int r=0; r<s.getLastRowNum(); r++) {
          Row row = s.getRow(r);
          if (row!=null) {
            Cell cellSampleId = row.getCell(sf_idxSampleId);
            if (cellSampleId!=null && StringUtils.isNotBlank(cellSampleId.getStringCellValue())) {
              String sampleId = StringUtils.strip(cellSampleId.getStringCellValue());
              sampleId = StringUtils.strip(sampleId, ",");
              Matcher m = sf_patternSampleId.matcher(sampleId);
              if (m.matches()) {

                Subject subject = (Subject)session.get(Subject.class, sampleId);
                if (subject != null) {
                  countSamples++;
                  Map<String,String> propValues = Maps.newHashMap();
                  for (Integer propIdx : sf_columnMap.keySet()) {

                    Cell cell = row.getCell(propIdx);
                    String propValue = null;
                    if (cell.getCellType()==Cell.CELL_TYPE_STRING) {
                      propValue = row.getCell(propIdx).getStringCellValue();
                      propValue = StringUtils.strip(propValue);
                    } else if (cell.getCellType()==Cell.CELL_TYPE_NUMERIC) {
                      propValue = idFormatter.format(row.getCell(propIdx).getNumericCellValue());
                    }
                    propValues.put(sf_columnMap.get(propIdx), propValue);



                  }
                  subject.addProperties(propValues);
                }
              }
            }
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

  public Workbook getWorkbook() {
    return m_workbook;
  }

  public void setWorkbook(Workbook workbook) {
    m_workbook = workbook;
  }
}
