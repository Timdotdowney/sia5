package tacos.data;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import tacos.TacoOrder;

public interface TacoOrderRepository extends CrudRepository<TacoOrder, Long> {
	
	@Override
	@EntityGraph(value = "Taco_Order.detail", type = EntityGraphType.LOAD)
	Iterable<TacoOrder> findAll();
	
}