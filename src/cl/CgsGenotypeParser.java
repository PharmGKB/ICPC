package cl;

import org.hibernate.Query;
import org.hibernate.Session;
import org.pharmgkb.Global;
import org.pharmgkb.enums.Property;
import org.pharmgkb.exception.PgkbException;
import org.pharmgkb.model.Sample;
import org.pharmgkb.util.HibernateUtils;
import org.pharmgkb.util.IcpcUtils;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.SortedSet;
import java.util.TreeSet;
import java.util.stream.Collectors;

/**
 * @author Ryan Whaley
 */
public class CgsGenotypeParser extends CommonParser {
  private static final String sf_fileDescriptor = "Genotypes for CGS subjects, space-separated, 38 columns";
  private static final Map<Property,String> sf_otherAlleleMap = new HashMap<>();
  static {
    sf_otherAlleleMap.put(Property.RS1045642, "G");
    sf_otherAlleleMap.put(Property.RS4244285, "G");
    sf_otherAlleleMap.put(Property.RS4986893, "G");
    sf_otherAlleleMap.put(Property.RS28399504, "A");
    sf_otherAlleleMap.put(Property.RS56337013, "C");
    sf_otherAlleleMap.put(Property.RS72552267, "G");
    sf_otherAlleleMap.put(Property.RS72558186, "T");
    sf_otherAlleleMap.put(Property.RS41291556, "T");
    sf_otherAlleleMap.put(Property.RS6413438, "C");
    sf_otherAlleleMap.put(Property.RS12248560, "C");
    sf_otherAlleleMap.put(Property.RS662, "T");
    sf_otherAlleleMap.put(Property.RS854560, "A");
    sf_otherAlleleMap.put(Property.RS4803418, "C");
    sf_otherAlleleMap.put(Property.RS48034189, "C");
    sf_otherAlleleMap.put(Property.RS8192719, "C");
    sf_otherAlleleMap.put(Property.RS2279343, "A");
    sf_otherAlleleMap.put(Property.RS2242480, "C");
    sf_otherAlleleMap.put(Property.RS3213619, "A");
    sf_otherAlleleMap.put(Property.RS2032582, "A");
    sf_otherAlleleMap.put(Property.RS1057910, "A");
    sf_otherAlleleMap.put(Property.RS71647871, "C");
    sf_otherAlleleMap.put(Property.RS3745274, "G");
    sf_otherAlleleMap.put(Property.RS7254579, "T");
    sf_otherAlleleMap.put(Property.RS2286823, "G");
    sf_otherAlleleMap.put(Property.RS1057868, "C");
    sf_otherAlleleMap.put(Property.RS2046934, "A");
    sf_otherAlleleMap.put(Property.RS1472122, "G");
    sf_otherAlleleMap.put(Property.RS1799853, "C");
    sf_otherAlleleMap.put(Property.RS28371685, "C");
    sf_otherAlleleMap.put(Property.RS28365085, "A");
    sf_otherAlleleMap.put(Property.RS12041331, "G");
    sf_otherAlleleMap.put(Property.RS1128503, "G");
    sf_otherAlleleMap.put(Property.RS168753, "A");
    sf_otherAlleleMap.put(Property.RS5918, "T");
    sf_otherAlleleMap.put(Property.RS762551, "A");
    sf_otherAlleleMap.put(Property.RS2244613, "T");
    sf_otherAlleleMap.put(Property.RS2302429, "G");
    sf_otherAlleleMap.put(Property.RS4803419, "C");
  }

  private Map<Integer,Property> m_propertyMap = new HashMap<>();
  private Map<Integer,String> m_alleleMap = new HashMap<>();

  public static void main(String[] args) {
    HibernateUtils.init();
    CgsGenotypeParser parser = new CgsGenotypeParser();
    try {
      parser.parseCommandLineArgs(args);
      parser.parseData();
    }
    catch (Exception e) {
      e.printStackTrace();
      System.exit(1);
    }
    finally {
      Global.shutdown();
    }
    System.exit(0);
  }

  protected void parseData() throws PgkbException {

    Session session = null;
    try (FileReader fr = new FileReader(getDataFile()); BufferedReader br = new BufferedReader(fr)) {
      session = HibernateUtils.getSession();
      Query sampleQuery = session.createQuery("from Sample s where s.subjectId=:id");

      String header = br.readLine();
      loadMaps(header);

      System.out.println("loaded properties: "+m_propertyMap.size());
      System.out.println("loaded alleles: "+m_alleleMap.size());

      String line;
      while ((line = br.readLine()) != null) {
        String[] fields = line.split("\\s");
        String subjectId = fields[0];

        Sample sample = (Sample)sampleQuery.setString("id", subjectId).uniqueResult();
        if (sample == null) {
          System.out.println("sample not found: "+subjectId);
          continue;
        }
        sample.addProperty(Property.CGS, "1");

        for (Integer i : m_propertyMap.keySet()) {
          if (fields[i].equals(IcpcUtils.NA)) {
            continue;
          }

          Integer alleleCount = Integer.valueOf(fields[i]);
          String allele = m_alleleMap.get(i);
          String refAllele = sf_otherAlleleMap.get(m_propertyMap.get(i));
          Property prop = m_propertyMap.get(i);

          if (alleleCount == 2) {
            sample.addProperty(prop, allele+"/"+allele);
          }
          else if (alleleCount == 1) {
            SortedSet<String> alleles = new TreeSet<>();
            alleles.add(allele);
            alleles.add(refAllele);
            sample.addProperty(prop, alleles.stream().collect(Collectors.joining("/")));
          }
          else {
            sample.addProperty(prop, refAllele+"/"+refAllele);
          }
        }
      }
      HibernateUtils.commit(session);
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      HibernateUtils.close(session);
    }
  }

  private void loadMaps(String header) throws PgkbException {
    String[] columns = header.split("\\s");

    for (int i = 6; i < columns.length; i++) {
      String column = columns[i];
      String[] tokens = column.split("_");

      Property property = Property.lookupByName(tokens[0]);
      if (property == null) {
        if (tokens[0].equals("rs3745274")) {
          m_propertyMap.put(i, Property.RS3745274);
        } else {
          throw new PgkbException("No property found for heading "+tokens[0]);
        }
      } else {
        m_propertyMap.put(i, property);
      }
      String varAllele = tokens[1];
      String refAllele = sf_otherAlleleMap.get(property);
      if (varAllele.equals(refAllele)) {
        throw new PgkbException("Ref and Var alleles the same for "+tokens[0]);
      }
      m_alleleMap.put(i, varAllele);
    }
  }

  public String getFileDescriptor() {
    return sf_fileDescriptor;
  }
}
