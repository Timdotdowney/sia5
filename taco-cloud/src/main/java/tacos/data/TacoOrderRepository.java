package tacos.data;

import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.EntityGraph.EntityGraphType;
import org.springframework.data.repository.CrudRepository;

import tacos.Order;

public interface OrderRepository extends CrudRepository<Order, Long> {
	
	@Override
	@EntityGraph(value = "Taco_Order.detail", type = EntityGraphType.LOAD)
	Iterable<Order> findAll();
	
}