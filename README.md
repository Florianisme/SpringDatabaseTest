# Spring Database Test
## Summary
This library provides a simple annotation for your JUnit Database Tests in your Spring Boot application.
It supports liquibase changelogs which will be run before each Testcase

## Example
```java
// Use the @DatabaseTest annotation
@DatabaseTest(annotatedPackages = "de.florianisme.sprinttest.entities")
public class InitTest {

   
	@Test
	public void testSessionInitialization(@Autowired Session session) {
		// Get a fresh session for each test
		// Sessions are automatically closed after each test
	}

	@Test
	public void testSessionFactory(@Autowired SessionFactory sessionFactoryBean) {
		// Or access the SessionFactory directly
	}
}
```
