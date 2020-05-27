package tacos.web;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

import tacos.Order;
import tacos.User;
import tacos.data.OrderRepository;
import tacos.security.UserRepositoryUserDetailsService;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
public class OrderController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

	private OrderRepository orderRepo;
	
	@Autowired
	private UserRepositoryUserDetailsService userRepo;
	
	public OrderController(OrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid @ModelAttribute("order") Order order,
		   Errors errors,
		   SessionStatus sessionStatus,
		   @AuthenticationPrincipal User user) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		order.setUser(user);
		log.info("Order submitted: " + order);
		orderRepo.save(order);
		sessionStatus.setComplete();
		return "redirect:/";
	}
}
