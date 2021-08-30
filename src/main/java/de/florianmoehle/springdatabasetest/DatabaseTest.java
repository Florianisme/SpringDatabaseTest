package de.florianmoehle.springdatabasetest;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@ExtendWith({SpringExtension.class})
@ContextConfiguration(classes = EmptyTestContext.class)
@TestExecutionListeners(mergeMode =
		TestExecutionListeners.MergeMode.MERGE_WITH_DEFAULTS,
		listeners = { TestExecutionFinishedListener.class}
)
public @interface DatabaseTest {

	/**
	 * @return The JDBC Url for the database instance which should be used during test execution
	 */
	String dbJdbcPath() default "jdbc:h2:mem:database";

	/**
	 * @return Username for login on database
	 */
	String dbUser() default "sa";

	/**
	 * @return Password for login on database
	 */
	String dbPassword() default "";

	/**
	 * @return Whether to run the Liquibase changelogs before the Test executes
	 * The path to the changelogs can be set with the property {@link #changelogPath()}
	 */
	boolean enableLiquibase() default true;

	/**
	 * @return Whether to clean the database after Test execution
	 */
	boolean cleanDatabase() default true;

	/**
	 * @return Path to the Liquibase changelog files
	 */
	String changelogPath() default "classpath:/db/changelog/db.changelog-master.xml";

	/**
	 * @return Array of all annotated classes for Hibernate to use
	 * Either this or {@link #annotatedPackages()} should be used
	 */
	Class[] annotatedClasses() default {};

	/**
	 * @return Array of all packages in which Hibernate should search for Entities
	 * Either this or {@link #annotatedClasses()} should be used
	 */
	String[] annotatedPackages() default {};

}
