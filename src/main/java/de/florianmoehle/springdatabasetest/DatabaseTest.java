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

	String dbJdbcPath() default "jdbc:h2:mem:database";
	String dbUser() default "sa";
	String dbPassword() default "";

	boolean enableLiquibase() default true;
	boolean cleanDatabase() default true;
	String changelogPath() default "classpath:/db/changelog/db.changelog-master.xml";
	Class[] annotatedClasses() default {};
	String[] annotatedPackages() default {};

}
