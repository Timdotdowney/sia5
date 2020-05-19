package tacos;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.validation.constraints.Digits;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

import org.hibernate.validator.constraints.CreditCardNumber;

@Entity
@Table(name="Taco_Order")
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

	public Order() {
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getStreet() {
		return street;
	}

	public void setStreet(String street) {
		this.street = street;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}

	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}

	public String getCcNumber() {
		return ccNumber;
	}

	public void setCcNumber(String ccNumber) {
		this.ccNumber = ccNumber;
	}

	public String getCcExpiration() {
		return ccExpiration;
	}

	public void setCcExpiration(String ccExpiration) {
		this.ccExpiration = ccExpiration;
	}

	public String getCcCVV() {
		return ccCVV;
	}

	public void setCcCVV(String ccCVV) {
		this.ccCVV = ccCVV;
	}

	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date placedAt;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Date getPlacedAt() {
		return placedAt;
	}

	public void setPlacedAt(Date placedAt) {
		this.placedAt = placedAt;
	}

	@ManyToMany(targetEntity=Taco.class)
	private List<Taco> tacos = new ArrayList<>();

	public void addDesign(Taco design) {
		this.tacos.add(design);
	}

	void placedAt() {
		this.placedAt = new Date();
	}

	public List<Taco> getTacos() {
		return tacos;
	}

	public void setTacos(List<Taco> tacos) {
		this.tacos = tacos;
	}
	
	
}
