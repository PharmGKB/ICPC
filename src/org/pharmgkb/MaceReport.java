package org.pharmgkb;

import com.google.common.collect.ImmutableList;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;

import java.io.File;
import java.util.List;

/**
 * @author Ryan Whaley
 */
public class MaceReport extends AbstractReport {
  private static final int sf_defaultColumnWidth = 20;
  private static final String sf_filename = "mace.report.xlsx";
  private static final String sf_sheetName = "Subject Data Subset";

  private static final List<Property> sf_columns = ImmutableList.of(
      Property.SUBJECT_ID,
      Property.PROJECT,
      Property.CARDIOVASCULAR_DEATH,
      Property.STEMI,
      Property.NSTEMI,
      Property.STROKE,
      Property.STENT_THROMB,
      Property.MI_DURING_FOLLOWUP,
      Property.MI_PHENO,
      Property.MACE_PHENO2,
      Property.MACE_PHENO2_EX_STROKE,
      Property.MACE_CRITERIA_3,
      Property.MACE_CRITERIA_4,
      Property.MACE_CRITERIA_5
  );

  public MaceReport(File dir) {
    super(sf_defaultColumnWidth);
    setFile(new File(dir, sf_filename));
  }
  
  @Override
  public void generate() throws PgkbException {
    writeSampleProperties(sf_columns);
  }

  @Override
  public String getSheetName() {
    return sf_sheetName;
  }
}
