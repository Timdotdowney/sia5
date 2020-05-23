package tacos.data;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Iterator;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tacos.Ingredient;
import tacos.Ingredient.Type;


@SpringBootTest
class IngredientRepositoryTest {
	
	@Autowired
	private IngredientRepository repo;
	
	@Test
	void injectedComponentsAreNotNull() {
		assertNotNull(repo);
	}

	@Test
	void testCreateIngredient() throws Exception {
		Ingredient ing = new Ingredient("FLTO", "Flour Tortilla", Type.WRAP);
		Ingredient savedIng = this.repo.save(ing);
		assertEquals(savedIng.getName(), ing.getName());
	}

	@Test
	void testFindAll() throws Exception {
		Iterable<Ingredient> ings = repo.findAll();
		int count = 0;
		Iterator<Ingredient> it = ings.iterator();
		while (it.hasNext()){
			it.next();
			count++;
		}
		assertEquals(count, 10);
	}

}
