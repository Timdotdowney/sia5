package tacos.web;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import lombok.extern.slf4j.Slf4j;
import tacos.Ingredient;
import tacos.Ingredient.Type;
import tacos.Taco;
import tacos.TacoOrder;
import tacos.data.IngredientRepository;
import tacos.data.TacoRepository;

@Slf4j
@Controller
@RequestMapping("/design")
@SessionAttributes("order")
@EnableWebMvc
public class DesignTacoController {

	List<Ingredient> ingredients = new ArrayList<>();

	private final IngredientRepository ingredientRepo;
	private Type[] types;

	private TacoRepository designRepo;

	@Autowired
	public DesignTacoController(IngredientRepository ingredientRepo, TacoRepository designRepo) {
		this.ingredientRepo = ingredientRepo;
		this.designRepo = designRepo;
	}
	
	@PostConstruct
	void onLoad() {
		this.types = Ingredient.Type.values();
		Iterable<Ingredient> foo = this.ingredientRepo.findAll();
		foo.forEach(i -> ingredients.add(i));
	}

	@GetMapping
	public String showDesignForm(Model model) {
		for (Type type : types) {
			model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
		}
		model.addAttribute("design", new Taco());

		return "design";
	}

	private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients.stream().filter(x -> x.getType().equals(type)).collect(Collectors.toList());
	}

	@ModelAttribute(name = "order")
	public TacoOrder tacoOrder() {
		log.info("created new order");
		return new TacoOrder();
	}

	@PostMapping
	public String processDesign(Model model, 
								//@ModelAttribute("order") Order order,
								@Valid @ModelAttribute("design") Taco design, 
								Errors errors) {
		if (errors.hasErrors()) {
			for (Type type : types) {
				model.addAttribute(type.toString().toLowerCase(), filterByType(ingredients, type));
			}
			return "design";
		}
		// Save the taco design...
		// We'll do this in chapter 3
		Taco saved = designRepo.save(design);
		((TacoOrder) model.getAttribute("order")).addDesign(saved);
		log.info("Processing design: " + design);
		return "redirect:/orders/current";
	}
}
