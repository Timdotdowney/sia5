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


Add the following to the application properties to create a static URL for the h2 console. 
spring.datasource.url=jdbc:h2:mem:mydb

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
						
Among the many helpful bits of development-time help offered by DevTools, it
will disable caching for all template libraries but will disable itself (and thus reenable
template caching) when your application is deployed.

JDBC API in Spring Starter is spring-boot-start-JDBC

Strange. I guess that it took some time to download the starter, since I had an error that
JdbcTemplate class could not be found. After wasting time looking for JAR files, the code
started working.

Strange error. As soon as I created data.sql, I had an error that it could not be empty.
Even after I filled it, I had the error. I removed most of the content except one line
and still had the error. I thought for a bit, then it worked. Yikes!

h2-console works. The URL is listed when the app starts. Username "user", Password "".

I had to add the following to application.properties to have data.sql read.
spring.datasource.initialization-mode=always
The default is embedded. This is the second time I have created the database code. The
first time I did not have to modify application.properties. I do not know why h2 is
considered an external database this time but not the first time.

Order does not have addDesign method. Added in next section on JPA.

ModelAttribute on method adds return value to model to initialize it.
https://examples.javacodegeeks.com/enterprise-java/spring/spring-modelattribute-annotation-example/

Add method for order and annotate with ModelAttribute(name="order") along with 
SessionAttribute("order"). The first call, order will be created and added to model. In
subsequent calls, it is in the model but not recreated.

The save method for design is incorrect. The ingredients are an array of strings, not
ingredients. Each string is the id for an ingredient. Use the value in the string as the
id in the taco and ingredients table.

	@Override
	public Taco save(Taco taco) {
		long tacoId = saveTacoInfo(taco);
		taco.setId(tacoId);
		for (String id : taco.getIngredients()) {
			//for each ingredient, add an entry in the join table
			saveIngredientToTaco(id, tacoId);
		}
		return taco;
	}
	
	private void saveIngredientToTaco(String id, long tacoId) {
		jdbc.update("insert into Taco_Ingredients (taco, ingredient) " + "values (?, ?)", 
				    tacoId, id);
	}
	
Names of columns in data.sql did not match names in order. Change them to agree with order.

PreparedStatementCreatorFactory does not return keys unless it is told to. Change Taco
save code and put factory in constructor.
in constructor

		 this.factory = new PreparedStatementCreatorFactory(
				"insert into Taco (name, createdAt) values (?, ?)", 
				Types.VARCHAR, Types.TIMESTAMP);
		 this.factory.setReturnGeneratedKeys(true);
		 
in save

	    taco.setCreatedAt(new Date());
		PreparedStatementCreator psc = factory.newPreparedStatementCreator(
			Arrays.asList(taco.getName(), new Timestamp(taco.getCreatedAt().getTime())));
		KeyHolder keyHolder = new GeneratedKeyHolder();
		jdbc.update(psc, keyHolder);
		Number num = keyHolder.getKey();
		return num.longValue();
		
Page 78, List<Ingredient> did not work for JDBC, it had to be List<String>. Perhaps JPA
will work with Ingredient.

Hibernate changes Enumerated types to int but the code uses string. Use the 
Enumerated annotation in the bean for Ingredients.
@Enumerated(EnumType.STRING)

JPA does not read schema.sql, it builds tables automatically from beans. Rename it so it
is not called.

Ingredient must have a default constructor in order for hibernate to insert with it. I
missed that instruction, since I could not use lombok.

Hibernate does not like new line in the middle of an insert. Had to modify import.sql to
remove new line before values.

JDBC I could retrieve data for ingredients in constructor. JPA does not work in 
constructor. I need a method after construction to read the database. Annotate a method
in the controller with @PostConstruct. Place initialization code there.

----------------------------------------------------
I am investigating why Slf4j and lombok do not work.

Help on understanding maven scope
https://reflectoring.io/maven-scopes-gradle-configurations/

lombok is working. I did not notice that I had Eclipse in addition to STS. Only Eclipse
appeared in the installer. I specified location of spring .ini. After installation, I
checked that the .ini file had the javaagent set for lombok. All good.

Slf4j is working too.

https://projectlombok.org/features/all

Required fields are uninitialized final and NotNull annotated. AllArgsConstructor is 
needed in Ingredient, not RequiredArgsConstructor

Code runs with lombok simplified beans. Log works too.
----------------------------------------------------

Now for testing.

This site does not recommend using SpringBootTest, since it takes too long.
https://reflectoring.io/unit-testing-spring-boot/

I used SpringBootTest, since it is easy.

The difficulty was testing the nested tables. I had to change the Lists to Sets in order 
to allow a Cartesian Product of tables. Lists do not support that. It is not recommended 
for the size of the product, but I would have to issue a select for each taco to retrieve 
its ingredients and then add them to the result set.
-----------------------------------------------------
Chapter 4

It appears that a custom login page is provided by default, not the default HTTP login
dialog box. It appears that the simple WebSecurity is enabled by default.

4.2.4
UserDetails is a security interface.

StandardPasswordEncoder is not secure, so it is deprecated but will be supported. 
Digest based password encoding is not considered secure. Instead use an adaptive one way 
function like BCryptPasswordEncoder, Pbkdf2PasswordEncoder, or SCryptPasswordEncoder. 
Even better use DelegatingPasswordEncoder which supports password upgrades. 

I will upgrade password encoder later.

I can no longer access h2-console

4.3.4 explains Cross-site request forgery (CSRF). This is why h2-console is forbidden.

https://springframework.guru/using-the-h2-database-console-in-spring-boot-with-spring-security/
explains how to enable h2-console, at the cost of disabling CSRF. While not recommended
in production, I will do so while testing.

Added security to testing. Used 
	@WithMockUser(value = "downeyt")
"downeyt" is the user I added through import.sql. Using SpringBootTest and AutoConfigureMockMvc.

The url for security for /orders allows access to /orders/current without authentication.
Change the url to /orders/**. The ** means include paths in the wild card.

I replaced 
return new StandardPasswordEncoder() 
with 
return PasswordEncoderFactories.createDelegatingPasswordEncoder()

4.4
Added test for adding user to the order.

Replace WithMockUser with WithUserDetails to access custom user details. It would be better
to separate the User from the the UserDetails. The user details interface items should
not be persisted. Create userdetails that extends user and implements userdetails. It has 
a copy constructor to construct from existing user. userdetailsservice looks up user and
contructs a new userdetails from it. Add a field for the user with getter. Set the
expression to the simplified getter name in applicationprincipal to retrieve the user directly.







		