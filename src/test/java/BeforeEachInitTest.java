import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hibernate.Session;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.florianmoehle.springtest.DatabaseTest;

@DatabaseTest(annotatedPackages = "de.florianisme.springtest", enableLiquibase = false)
public class BeforeEachInitTest {

	private Session session;

	@BeforeEach
	public void beforeEach(@Autowired Session session) {
		this.session = session;
	}

	@Test
	public void testSessionAlreadyInitialized() {
		assertNotNull(session);
	}

}