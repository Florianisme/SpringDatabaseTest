# Spring Database Test
## Summary
This library provides a simple annotation for your JUnit Database Tests in your Spring Boot application.
It supports liquibase changelogs which will be run before each Testcase

## Example
This is a short example on how to get a Session instance
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
## Use this library
### Gradle
```groovy
testImplementation 'de.florianisme:springtest:0.0.1'
```
### Maven
```xml
<dependency>
	<groupId>de.florianisme</groupId>
	<artifactId>springtest</artifactId>
	<version>0.0.1</version>
</dependency>
```
