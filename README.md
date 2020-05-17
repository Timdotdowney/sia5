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

