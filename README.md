# sia5
Spring in Action 5 - Example Code 

Chapter 1

Default is JAR, can change to WAR if not run in Spring.

SpringBootApplication is SpringBootConfiguration, EnableAutoConfiguration, @ComponentScan

On its own, @Controller
doesn’t do much. Its primary purpose is to identify this class as a component for component
scanning.

SpringRunner is alias for current SpringJUnit4ClassRunner.

To test with main application tests, need
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
@AutoConfigureMockMvc before class
(https://mkyong.com/spring-boot/spring-boot-test-unable-to-autowired-mockmvc/)

Did not need @RunWith(SpringRunner.class)

Edit Starters is in Spring menu when right-click pom.

Added h2 database in pom.

Add to main/resources/application.properties to enable h2-console
spring.h2.console.enabled=true
spring.h2.console.path=/h2-console

Chapter 2

Could not initialize Ingredients.
List<Ingredient> ingredients = Arrays.asList( new Ingredient[] { ...

I cannot get lombok code to compile. I have confirmed that annotations are executed at
compile time. I have run the lombok installer. I have added lombok.jar to the VM options.
I give up.

Error in filterByType: <Ingredient> should be List<Ingredient>
private List<Ingredient> filterByType(List<Ingredient> ingredients, Type type) {
		return ingredients
				.stream()
				.filter(x -> x.getType().equals(type))
				.collect(Collectors.toList());
	}

Taco class does not exist yet. Must create it.
	
@Controller marks for component scan. Spring will discover, create bean in context.

@RequestMapping defines prefix of url that directs here

@GetMapping will define that the method will handle Get requests for the RequestMapping path.

Had to create taco class in order to run 2.1

Slf4j does not work. Had to insert code. Probable same problem as lombok.
private static final org.slf4j.Logger log =
org.slf4j.LoggerFactory.getLogger(DesignTacoController.class);

Does not run with validation enabled.
Caused by: java.lang.IllegalStateException: Neither BindingResult nor plain target object for bean name 'design' available as request attribute
The Taco bean is not passed to the view. It must be added to the model. 
public String processDesign(Model model, @Valid @ModelAttribute("design") Taco design, Errors errors) {
Similar correction for the order.

If the data has an error, then the type categories are not sent to the view. No check boxes appear.

Error messages do not appear.
		<span class="validationError"
			th:if="${#fields.hasErrors('ingredients')}"
			th:errors="*{ingredients}">Ingredient Error</span>
			
To make the checkboxes sticky, add th:fields="*{list name in bean}"
<input name="ingredients" type="checkbox" th:field="*{ingredients}"
						th:value="${ingredient.id}" />
						

