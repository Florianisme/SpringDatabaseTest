import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.hibernate.Session;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import de.florianmoehle.springtest.DatabaseTest;
import entities.Post;

@DatabaseTest(changelogPath = "classpath:/db/changelog/liquibaseTest.xml", annotatedClasses = Post.class)
public class LiquibaseTest {

	@Test
	public void testPostsTableCreated(@Autowired Session session) {
		session.get(Post.class, 0L);
		// No exception means the table was created
	}

	@Test
	public void testPostsCanBeInsertedAndRead(@Autowired Session session) {
		Post originalPost = new Post(0L, "Test Title", "Florian Moehle", "This is my first post");
		session.save(originalPost);

		Post post = session.get(Post.class, 0L);

		assertNotNull(post);
		assertEquals(0L, post.getId());
		assertEquals("Test Title", post.getTitle());
		assertEquals("Florian Moehle", post.getAuthor());
		assertEquals("This is my first post", post.getContent());
	}


}
