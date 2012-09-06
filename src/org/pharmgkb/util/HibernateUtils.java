package org.pharmgkb.util;

import com.google.common.base.Joiner;
import com.google.common.base.Preconditions;
import com.google.common.collect.Sets;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;

import java.sql.Driver;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.Set;

/**
 * Created by IntelliJ IDEA.
 * User: whaleyr
 * Date: 9/5/12
 */
public class HibernateUtils {
  private static final Set<String> sf_driverNames = Sets.newHashSet();
  private static final Logger sf_logger = Logger.getLogger(HibernateUtils.class);
  private static SessionFactory s_sessionFactory = null;

  public static void init() {
    if (s_sessionFactory == null) {
      Configuration configuration = new Configuration().configure("hibernate.cfg.xml");
      ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties()).buildServiceRegistry();
      sf_driverNames.add(configuration.getProperty("connection.driver_class"));
      s_sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }
  }

  public static void shutdown() {
    if (s_sessionFactory!=null) {
      s_sessionFactory.close();
    }
    // deregister
    Enumeration<Driver> drivers = DriverManager.getDrivers();
    while (drivers.hasMoreElements()) {
      Driver driver = drivers.nextElement();
      String driverName = driver.getClass().getName();
      if (sf_driverNames.contains(driverName)) {
        try {
          DriverManager.deregisterDriver(driver);
        } catch (Exception ex) {
          sf_logger.error("Unable to deregister " + driver.getClass(), ex);
        }
      } else {
        sf_logger.warn(String.format("Not deregistering %s (not used by Hibernate)",driverName));
      }
    }
  }

  public static Session getSession() {
    Preconditions.checkState(s_sessionFactory!=null, "SessionFactory has not been initiated");

    Session session = s_sessionFactory.openSession();
    session.beginTransaction();
    return session;
  }

  public static void commit(Session session) {
    if (session != null) {
      Transaction transaction = session.getTransaction();
      if (transaction != null && transaction.isActive()) {
        transaction.commit();
        session.beginTransaction();
      } else {
        throw new IllegalStateException("No transaction to commit");
      }
    }
  }

  public static void close(Session session) {
    try {
      if (session != null && session.isOpen()) {
        Transaction transaction = session.getTransaction();
        if (transaction != null) {
          if (transaction.isActive()) {
            transaction.rollback();
          } else {
            sf_logger.warn("Closing a session where transaction is not active!");
          }
        }
        session.clear();
        session.close();
        if (sf_logger.isDebugEnabled()) {
          StackTraceElement[] trace = Thread.currentThread().getStackTrace();
          sf_logger.debug("session closed\n\t" + Joiner.on("\n\t").join(Arrays.asList(trace).subList(0,4)));
        }
      }
    } catch (Exception ex) {
      sf_logger.error("Error closing session", ex);
    }
  }
}
