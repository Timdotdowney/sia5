package tacos;

import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.ArrayList;
import java.util.List;

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
    List<Ingredient> ings = new ArrayList<Ingredient>();
    ings.add(new Ingredient("FLTO", "Flour Tortilla", Ingredient.Type.WRAP));
    taco.setIngredients(ings);
    Taco savedTaco = repo.save(taco);
    assertNotNull(savedTaco.getCreatedAt());
  }

}