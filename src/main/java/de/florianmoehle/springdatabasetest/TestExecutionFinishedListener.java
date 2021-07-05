package de.florianmoehle.springdatabasetest;

import java.util.function.Supplier;
import javax.sql.DataSource;

import org.apache.commons.dbcp2.BasicDataSource;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.test.context.TestContext;
import org.springframework.test.context.support.AbstractTestExecutionListener;

import liquibase.exception.LiquibaseException;
import liquibase.integration.spring.SpringLiquibase;

class TestExecutionFinishedListener extends AbstractTestExecutionListener {

	private DatabaseTest databaseTest;
	private LocalSessionFactoryBean sessionFactory;
	private Session session;

	@Override
	public void beforeTestMethod(TestContext testContext) {
		databaseTest = getAnnotationInstance(testContext);

		registerBeans(testContext);
	}

	private void registerBeans(TestContext testContext) {
		GenericApplicationContext applicationContext = (GenericApplicationContext) testContext.getApplicationContext();

		applicationContext.registerBean(LocalSessionFactoryBean.class, sessionFactorySupplier());
		applicationContext.registerBean(Session.class, sessionSupplier(applicationContext));

		if (databaseTest.enableLiquibase()) {
			applicationContext.registerBean(SpringLiquibase.class, liquibaseSupplier());
			applicationContext.getBean(SpringLiquibase.class); // Needed to initialize Liquibase before first test
		}
	}

	private Supplier<Session> sessionSupplier(GenericApplicationContext applicationContext) {
		return () -> {
			session = applicationContext.getBean(SessionFactory.class).openSession();
			return session;
		};
	}

	private DatabaseTest getAnnotationInstance(TestContext testContext) {
		DatabaseTest annotation = testContext.getTestClass().getAnnotation(DatabaseTest.class);
		if (annotation == null) {
			throw new IllegalStateException("No DatabaseTest annotation found");
		}
		return annotation;
	}

	private Supplier<LocalSessionFactoryBean> sessionFactorySupplier() {
		return () -> {
			sessionFactory = new LocalSessionFactoryBean();
			sessionFactory.setDataSource(dataSource());
			if (databaseTest.annotatedClasses() != null && databaseTest.annotatedClasses().length != 0) {
				sessionFactory.setAnnotatedClasses(databaseTest.annotatedClasses());
			} else if (databaseTest.annotatedPackages() != null && databaseTest.annotatedPackages().length != 0) {
				sessionFactory.setAnnotatedPackages(databaseTest.annotatedPackages());
			} else {
				throw new IllegalArgumentException("Neither annotatedClasses nor annotatedPackages was set");
			}

			return sessionFactory;
		};
	}

	private Supplier<SpringLiquibase> liquibaseSupplier() {
		return () -> {
			System.out.println("### Liquibase initialization before Test ###");
			SpringLiquibase liquibase = new SpringLiquibase();
			liquibase.setDataSource(dataSource());
			liquibase.setChangeLog(databaseTest.changelogPath());
			return liquibase;
		};
	}

	public DataSource dataSource() {
		BasicDataSource dataSource = new BasicDataSource();
		dataSource.setDriverClassName("org.h2.Driver");
		dataSource.setUrl(databaseTest.dbJdbcPath());
		dataSource.setUsername(databaseTest.dbUser());
		dataSource.setPassword(databaseTest.dbPassword());

		return dataSource;
	}


	@Override
	public void afterTestMethod(TestContext testContext) throws LiquibaseException {
		if (databaseTest.enableLiquibase() && databaseTest.cleanDatabase()) {
			System.out.println("### Liquibase Cleanup after Test ###");
			reinitializeDatabase(testContext);
		}
		closeOldSession();
	}

	private void closeOldSession() {
		if (session != null && session.isOpen()) {
			session.close();
		}
		session = null;
		if (sessionFactory != null) {
			sessionFactory.destroy();
		}
		sessionFactory = null;
	}

	private void reinitializeDatabase(TestContext testContext) throws LiquibaseException {
		ApplicationContext app = testContext.getApplicationContext();
		SpringLiquibase springLiquibase = app.getBean(SpringLiquibase.class);
		springLiquibase.setDropFirst(true);
		springLiquibase.afterPropertiesSet();
	}
}