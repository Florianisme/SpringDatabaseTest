import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.florianmoehle.springdatabasetest.DatabaseTest;

@DatabaseTest(annotatedPackages = "de.florianisme.springtest", enableLiquibase = false)
public class InitTest {

	@Test
	public void testSessionFactoryInitialization(@Autowired SessionFactory sessionFactoryBean) {
		assertNotNull(sessionFactoryBean);
		assertFalse(sessionFactoryBean.isClosed());
	}
	@Test
	public void testSessionInitialization(@Autowired Session session) {
		assertNotNull(session);
		assertTrue(session.isConnected());
	}
}
