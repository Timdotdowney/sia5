package tacos.web;

import static org.hamcrest.Matchers.containsString;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.persistence.EntityGraph;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Order;
import tacos.Taco;
import tacos.baeldung.Comment;
import tacos.baeldung.Post;
import tacos.baeldung.User;
import tacos.data.OrderRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class DesignTacoControllerTest {

	Taco taco1;
	Taco taco2;
	Order order1;
	Order orderSaved = null;

	@Autowired
	private MockMvc mockMvc;

	MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();

	public DesignTacoControllerTest() {
		taco1 = new Taco();
		taco1.setName("taco1");
		Set<Ingredient> taco1List = new HashSet<Ingredient>();
		taco1List.add(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
		taco1List.add(new Ingredient("GRBF", "Flour Tortilla", Ingredient.Type.PROTEIN));
		taco1List.add(new Ingredient("TMTO", "Flour Tortilla", Ingredient.Type.VEGGIES));
		taco1.setIngredients(taco1List);
		taco2 = new Taco();

		taco2.setName("taco2");
		Set<Ingredient> taco2List = new HashSet<Ingredient>();
		taco2List.add(new Ingredient("CHED", "Cheddar", Ingredient.Type.CHEESE));
		taco2List.add(new Ingredient("SLSA", "Salsa", Ingredient.Type.SAUCE));
		taco2List.add(new Ingredient("COTO", "Corn Tortilla", Ingredient.Type.WRAP));
		taco1.setIngredients(taco1List);

		requestParams.add("name", "Tim Downey");
		requestParams.add("street", "6551 SW 76 ST");
		requestParams.add("city", "South Miami");
		requestParams.add("state", "FL");
		requestParams.add("zip", "33143");
		requestParams.add("ccNumber", "1111222233334444");
		requestParams.add("ccExpiration", "12/34");
		requestParams.add("ccCVV", "123");

		order1 = new Order();

	}

	@Autowired
	private OrderRepository repoOrder;

	// @Test
	void injectedComponentsAreNotNull() {
		assertNotNull(repoOrder);
	}

	// @Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"))
				.andExpect(content().string(containsString("Welcome to...")));
	}

	// @Test
	public void testShowDesign() throws Exception {
		mockMvc.perform(get("/design")).andExpect(status().isOk()).andExpect(view().name("design"))
				.andExpect(content().string(containsString("Design your taco!")));
	}

	// @Test
	public void testProcessDesign() throws Exception {
		mockMvc.perform(
				post("/design").sessionAttr("order", order1).param("name", "test1").param("ingredients", "FLTO"))
				.andExpect(redirectedUrl("/orders/current"));
	}

	// @Test
	public void testProcessDesignGet() throws Exception {
		mockMvc.perform(get("/orders/current").requestAttr("design", taco1).sessionAttr("order", order1))
				.andExpect(status().isOk()).andExpect(view().name("orderForm"))
				.andExpect(content().string(containsString("Order your taco creations!")));
	}

	@PersistenceContext
	EntityManager entityManager;

	@Test
	public void testProcessDesignPost() throws Exception {
		mockMvc.perform(
				post("/design")
				.sessionAttr("order", order1)
				.param("name", "test1")
				.param("ingredients", "FLTO"))
				.andExpect(redirectedUrl("/orders/current"));

		mockMvc.perform(post("/orders")
				.requestAttr("design", taco1)
				.sessionAttr("order", order1)
				.params(requestParams))
				.andExpect(redirectedUrl("/"));

		Iterable<Order> orders = repoOrder.findAll();
		assertNotNull(orders);
		Iterator<Order> it = orders.iterator();
		assertTrue(it.hasNext());
		Order orderNext = it.next();
		assertEquals(orderNext.getId(), order1.getId());
		assertNotNull(orderNext.getTacos());
		Iterator<Taco> itTaco = orderNext.getTacos().iterator();
		assertTrue(itTaco.hasNext());
		Taco tacoNext = itTaco.next();
		assertEquals(tacoNext.getId(), taco1.getId());
		log.info("processed design");
	}

	public List<?> listPosts(String hint, String graphName) {
		return entityManager.createNamedQuery("Post.findAll").setHint(hint, entityManager.getEntityGraph(graphName))
				.getResultList();
	}

	@Transactional
	public void insertPost(Post post, EntityManager entityManager) {
		entityManager.createNativeQuery("INSERT INTO post (id, subject, user_id) VALUES (?,?,?)")
				.setParameter(1, post.getId())
				.setParameter(2, post.getSubject())
				.setParameter(3, post.getUser())
				.executeUpdate();
	}

	@Transactional
	public void insertUser(User user, EntityManager entityManager) {
		entityManager.createNativeQuery("INSERT INTO user (id, email, name) VALUES (?,?,?)")
				.setParameter(1, user.getId()).setParameter(2, user.getEmail()).setParameter(3, user.getName())
				.executeUpdate();
	}

	@Transactional
	public void insertComment(Comment comment, EntityManager entityManager) {
		entityManager.createNativeQuery("INSERT INTO comment (id, post_id, reply, user_id) VALUES (?,?,?,?)")
				.setParameter(1, comment.getId())
				.setParameter(2, comment.getPost())
				.setParameter(3, comment.getReply())
				.setParameter(4, comment.getUser()).executeUpdate();
	}

	public Post findWithEntity(Post post, EntityManager entityManager) {
		EntityGraph<?> entityGraph = entityManager.getEntityGraph("post-entity-graph-with-comment-users");
		Map<String, Object> properties = new HashMap<>();
		properties.put("javax.persistence.fetchgraph", entityGraph);
		return entityManager.find(Post.class, post.getId(), properties);
	}

	//@Test
	@Transactional
	public void testBaeldung() {
		User user = new User();
		Post post = new Post();
		List<Comment> comments = new ArrayList<Comment>(Arrays.asList(new Comment(1L, "good", post, user),
				new Comment(2L, "better", post, user), new Comment(3L, "best", post, user)));

		post.setId(100L);
		post.setSubject("Hello Baeldung");
		post.setUser(user);

		user.setId(10L);
		user.setEmail("downeyt@fiu.edu");
		user.setName("Tim Downey");

		insertUser(user, entityManager);
		insertPost(post, entityManager);
		comments.forEach(c -> insertComment(c, entityManager));

//		User userFound = entityManager.find(User.class,10L);
//		Post postFound = entityManager.find(Post.class, 100L);
//		Post postEager = findWithEntity(post, entityManager);
//		List<Post> postList = (List<Post>) listPosts("javax.persistence.fetchgraph", 
//				                                     "post-entity-graph-with-comment-users");
		
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
		
		CriteriaQuery<String> q = cb.createQuery(String.class);
		Root<Comment> c = q.from(Comment.class);
		q.select(c.get("reply"));
		List<String> results = entityManager.createQuery(q).getResultList();
		
		TypedQuery<String> query2 = entityManager.createQuery(
			      "SELECT c.reply FROM Comment AS c", String.class);
		List<String> results2 = query2.getResultList();
		TypedQuery<Object[]> query3 = entityManager.createQuery(
			      "SELECT c, p.subject FROM Comment AS c JOIN c.post p", Object[].class);
		List<Object[]> results3 = query3.getResultList();
		


		log.info("retreieved post");

	}

}
