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
import java.util.Iterator;
import java.util.List;

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
import tacos.data.OrderRepository;

@SpringBootTest
@AutoConfigureMockMvc
@Slf4j
class DesignTacoControllerTest {
	
	Taco taco1;
	Taco taco2;
	Order order1 = new Order();
	
	MultiValueMap<String, String> requestParams = new LinkedMultiValueMap<>();
	
	public DesignTacoControllerTest() {
		taco1 = new Taco();
		taco1.setName("taco1");
		List<Ingredient> taco1List = new ArrayList<Ingredient>();
		taco1List.add(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
		taco1List.add(new Ingredient("GRBF", "Flour Tortilla", Ingredient.Type.PROTEIN));
		taco1List.add(new Ingredient("TMTO", "Flour Tortilla", Ingredient.Type.VEGGIES));
		taco1.setIngredients(taco1List);
		taco2 = new Taco();
		
		taco2.setName("taco2");
		List<Ingredient> taco2List = new ArrayList<Ingredient>();
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
		
	}
	
	@Autowired
	private OrderRepository repoOrder;

	@Autowired
	private MockMvc mockMvc;

	@Test
	void injectedComponentsAreNotNull() {
		assertNotNull(repoOrder);
	}
	
	@Test
	public void testHomePage() throws Exception {
		mockMvc.perform(get("/")).andExpect(status().isOk()).andExpect(view().name("home"))
				.andExpect(content().string(containsString("Welcome to...")));
	}

	@Test
	public void testShowDesign() throws Exception {
		mockMvc.perform(get("/design"))
			.andExpect(status().isOk())
			.andExpect(view().name("design"))
			.andExpect(content().string(containsString("Design your taco!")));
	}

	
	@Test
	public void testProcessDesign() throws Exception {
		mockMvc.perform(
				post("/design").sessionAttr("order", order1).param("name", "test1").param("ingredients", "FLTO"))
				.andExpect(redirectedUrl("/orders/current"));
	}

	@Test
	public void testProcessDesignGet() throws Exception {
		mockMvc.perform(get("/orders/current").requestAttr("design", taco1).sessionAttr("order", order1))
				.andExpect(status().isOk()).andExpect(view().name("orderForm"))
				.andExpect(content().string(containsString("Order your taco creations!")));
	}
	
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

	@Test
	public void testProcessOrder() throws Exception {
		mockMvc.perform(post("/orders")
				.requestAttr("design", taco1)
				.sessionAttr("order", order1)
				.params(requestParams))
			.andExpect(redirectedUrl("/"));
	}

}
