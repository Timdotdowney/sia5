package tacos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.HashSet;
import java.util.Set;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import tacos.data.TacoRepository;

@SpringBootTest
class TacoTest {

  @Autowired
  private TacoRepository repo;

  @Test
  void savedUserHasRegistrationDate() {
    Taco taco = new Taco();
    taco.setName("testtaco");
    Set<Ingredient> ings = new HashSet<Ingredient>();
    ings.add(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
    taco.setIngredients(ings);
    Taco savedTaco = repo.save(taco);
    assertNotNull(savedTaco.getCreatedAt());
  }

}