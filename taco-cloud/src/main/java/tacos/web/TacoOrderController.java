package tacos.web;

import javax.validation.Valid;

import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import tacos.LoginUser;
import tacos.TacoOrder;
import tacos.data.TacoOrderRepository;
import tacos.security.LoginUserDetails;

@Controller
@RequestMapping("/orders")
@SessionAttributes("order")
@EnableWebMvc
@EnableWebSecurity
public class TacoOrderController {
	private static final org.slf4j.Logger log = org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

	private TacoOrderRepository orderRepo;

	public TacoOrderController(TacoOrderRepository orderRepo) {
		this.orderRepo = orderRepo;
	}

	@GetMapping("/current")
	public String orderForm() {
		return "orderForm";
	}

	@PostMapping
	public String processOrder(@Valid @ModelAttribute("order") TacoOrder tacoOrder,
		   Errors errors,
		   @AuthenticationPrincipal(errorOnInvalidType = true, expression="loginUser") LoginUser loginUser,
		   SessionStatus sessionStatus) {
		if (errors.hasErrors()) {
			return "orderForm";
		}
		tacoOrder.setLoginUser(loginUser);
		log.info("Order submitted: " + tacoOrder);
		orderRepo.save(tacoOrder);
		sessionStatus.setComplete();
		return "redirect:/";
	}
}
