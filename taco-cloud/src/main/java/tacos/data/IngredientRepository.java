package tacos.data;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import tacos.Ingredient;
import tacos.Order;

public interface IngredientRepository extends CrudRepository<Ingredient, String> {
	@Query(
			  value = "SELECT * FROM Ingredient i", 
			  nativeQuery = true)
	@Override
	List<Ingredient> findAll();
}
