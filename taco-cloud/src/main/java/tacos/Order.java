package tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

import lombok.Data;

@Entity
@Table(name="Taco_Order")
@Data
public class Order {
	@NotBlank(message = "Name is required")
	private String name = null;
	@NotBlank(message = "Street is required")
	private String street = null;
	@NotBlank(message = "City is required")
	private String city = null;
	@NotBlank(message = "State is required")
	private String state = null;
	@NotBlank(message = "Zip is required")
	private String zip = null;
	@CreditCardNumber(message = "Not a valid credit card number")
	private String ccNumber = null;
	@Pattern(regexp = "^(0[1-9]|1[0-2])([\\/])([1-9][0-9])$", message = "Must be formatted MM/YY")
	private String ccExpiration = null;
	@Digits(integer = 3, fraction = 0, message = "Invalid CVV")
	private String ccCVV = null;

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date placedAt;

	@ManyToMany(targetEntity=Taco.class)
	private List<Taco> tacos = new ArrayList<>();

	public void addDesign(Taco design) {
		this.tacos.add(design);
	}

	@PrePersist
	void placedAt() {
		this.placedAt = new Date();
	}
	
}
