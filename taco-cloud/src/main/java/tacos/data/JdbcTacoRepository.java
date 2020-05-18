package tacos.data;

import java.sql.Timestamp;
import java.sql.Types;
import java.util.Arrays;
import java.util.Date;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.PreparedStatementCreatorFactory;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import tacos.Taco;

@Repository
public class JdbcTacoRepository implements TacoRepository {
	
	private JdbcTemplate jdbc;
	PreparedStatementCreatorFactory factory;

	public JdbcTacoRepository(JdbcTemplate jdbc) {
		this.jdbc = jdbc;

		 this.factory = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)", 
				Types.VARCHAR, Types.TIMESTAMP);
		 this.factory.setReturnGeneratedKeys(true);
	}

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for (String ingId : taco.getIngredients()) {
			//for each ingredient, add an entry in the join table
			saveIngredientToTaco(ingId, tacoId);
		}
		return taco;
	}
	
	private void saveIngredientToTaco(String ingId, long tacoId) {
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) " + "values (?, ?)", 
				    tacoId, ingId);
	}

	private long saveTacoInfo(Taco taco) {
		taco.setCreatedAt(new Date());
		PreparedStatementCreator psc = factory.newPreparedStatementCreator(
			Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		Number num = keyHolder.getKey();
		return num.longValue();
	}
}
