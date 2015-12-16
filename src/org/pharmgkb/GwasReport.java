package org.pharmgkb;

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableList;
import org.apache.poi.ss.usermodel.Row;
import org.hibernate.Session;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.ExcelUtils;
import org.pharmgkb.util.HibernateUtils;
import org.pharmgkb.util.IcpcUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.invoke.MethodHandles;
import java.util.List;

/**
 * @author Ryan Whaley
 */
public class GwasReport extends AbstractReport {
  private static final Logger sf_logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

  private static final int sf_defaultColumnWidth = 20;
  private static final String sf_filename = "gwas.report.xlsx";
  private static final String sf_sheetName = "Subjects";
  private static final List<Property> sf_columns = ImmutableList.of(
      Property.SUBJECT_ID,
      Property.RACE_SELF,
      Property.RACE_OMB,
      Property.DIABETES,
      Property.EVER_SMOKED,
      Property.CURRENT_SMOKER,
      Property.CREATININE,
      Property.CREATININE_CAT,
      Property.CLOPIDOGREL,
      Property.DOSE_CLOPIDOGREL,
      Property.DURATION_CLOPIDOGREL,
      Property.ASPIRN,
      Property.DOSE_ASPIRIN,
      Property.DURATION_ASPIRIN,
      Property.STATINS,
      Property.PPI,
      Property.PPI_NAME,
      Property.CALCIUM_BLOCKERS,
      Property.BETA_BLOCKERS,
      Property.ACE_INH,
      Property.ANG_INH_BLOCKERS,
      Property.EZETEMIB,
      Property.GLYCOPROTEIN_IIAIIIB_INHIBITOR,
      Property.BMI,
      Property.AGE,
      Property.GENDER,
      Property.TTF_ACS,
      Property.ACS_DURING_FOLLOWUP,
      Property.ANGINA,
      Property.TIME_ANGINA,
      Property.PCI_INFORMATION,
      Property.CLINICAL_SETTING,
      Property.INDICATION_CLOPIDOGREL
  );

  public GwasReport(File dir) {
    super(sf_defaultColumnWidth);
    Preconditions.checkNotNull(dir);
    setFile(new File(dir, sf_filename));
  }

  @Override
  public void generate() throws PgkbException {
    Session session = null;
    try(FileOutputStream out = new FileOutputStream(getFile())) {
      session = HibernateUtils.getSession();

      Row codeRow = getNextRow();
      Row titleRow = getNextRow();
      Row formatRow = getNextRow();

      for (int i=0; i<sf_columns.size(); i++) {
        Property property = sf_columns.get(i);

        ExcelUtils.writeCell(titleRow, i, property.getDisplayName(), getHeaderStyle());
        ExcelUtils.writeCell(codeRow, i, property.getShortName(), getMonospaceStyle());
        ExcelUtils.writeCell(formatRow, i, IcpcUtils.lookupFormat(session, property), getCodeStyle());
      }

      //noinspection unchecked
      List<String> rez = session.createQuery("select s.subjectId from Sample s order by s.subjectId").list();
      for (String sampleId : rez) {
        Sample sample = (Sample)session.get(Sample.class, sampleId);

        Row sampleRow = getNextRow();
        for (int i=0; i<sf_columns.size(); i++) {
          ExcelUtils.writeCell(sampleRow, i, sample.getProperties().get(sf_columns.get(i)));
        }
      }

      saveToOutputStream(out);
    }
    catch (Exception ex) {
      throw new PgkbException("Error writing report",ex);
    }
    finally {
      HibernateUtils.close(session);
    }
    sf_logger.info("done with {}",this.getClass().getSimpleName());
  }

  public String getSheetName() {
    return sf_sheetName;
  }
}
