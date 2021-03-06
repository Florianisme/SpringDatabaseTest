# Spring Database Test

[![CD](https://github.com/Florianisme/SpringDatabaseTest/actions/workflows/cd.yml/badge.svg?branch=0.0.3)](https://github.com/Florianisme/SpringDatabaseTest/actions/workflows/cd.yml)
![CI](https://github.com/Florianisme/SpringDatabaseTest/actions/workflows/ci.yml/badge.svg)

## Summary

This library provides a simple annotation for your JUnit Database Tests in your Spring Boot application to give you a fresh database session
for each run. Liquibase changelogs can be run before each Testcase.

## Usage

### 1. Add Dependency

<table>
  <tr>
    <th>Gradle</th>
    <th>Maven</th>
  </tr>
  <tr>
  <td>

```groovy
testImplementation 'de.florian-moehle:spring-database-test:0.0.3'
```

   </td>
   <td>

```xml

<dependency>
    <groupId>de.florian-moehle</groupId>
    <artifactId>spring-database-test</artifactId>
    <version>0.0.3</version>
    <scope>test</scope>
</dependency>
```

   </td>
  </tr>
</table>

### 2. Add Annotation to Testclasses

```java
@DatabaseTest(annotatedPackages = "your_entities_package")
public class DatabaseTest {
	// ...
}
```

Additional Configuration options and their defaults are listed [here](#configuration).

## Examples

A fresh Session or SessionFactory instance for each Test method

```java
// Use the @DatabaseTest annotation
@DatabaseTest(annotatedPackages = "de.florianmoehle.springdatabasetest.entities")
public class InitTest {

	@Test
	public void testSession(@Autowired Session session) {
		// Get a fresh session for each test
		// Sessions are automatically closed after each test
	}

	@Test
	public void testSessionFactory(@Autowired SessionFactory sessionFactoryBean) {
		// Or access the SessionFactory directly
	}
}
```

A new Session for each test with a single annotation

```java
@DatabaseTest(annotatedPackages = "de.florianmoehle.springdatabasetest.entities")
public class InitTest {

	private Session session;

	@BeforeEach
	public void beforeEach(@Autowired Session session) {
		this.session = session;
	}

	@Test
	public void testSessionAlreadyInitialized() {
		// session.save(...)
	}
}
```
[Take a look at the tests](https://github.com/Florianisme/SpringDatabaseTest/tree/master/src/test/java) for some additional examples.

## Configuration

Configuration works by passing parameters in the `@DatabaseTest` annotation. Here are all properties and their default values.

| Parameter        | Default Value |  Additional Information   |
| ------------- |:-------------|:------------------|
| dbJdbcPath      | `jdbc:h2:mem:database` |  Only H2 is currently supported      |
| dbUser      | `sa`      |        |
| dbPassword |       |        |
| enableLiquibase |    `true`   |        |
| cleanDatabase |    `true`   |        |
| changelogPath |    `classpath:/db/changelog/db.changelog-master.xml`   |        |
| annotatedClasses |    `[]`   |    Either this or `annotatedPackages` has to be set    |
| annotatedPackages |    `[]`   |    Either this or `annotatedClasses` has to be set    |