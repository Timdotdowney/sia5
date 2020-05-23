package tacos.web;

import javax.transaction.Transactional;
import javax.validation.Valid;

import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.Order;
import tacos.data.OrderRepository;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

	private OrderRepository orderRepo;

	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}
	

	@ModelAttribute(name = "order")
	public Order order() {
		log.info("created new order in design controller");
		Order order = new Order();
		return order;
	}

	@PostMapping
	@Transactional
	public String processOrder(@Valid @ModelAttribute("order") Order order,
							   Errors errors,
							   SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		log.info("Order submitted: " + order);
		orderRepo.save(order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
}
