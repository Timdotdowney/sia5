package tacos;

import java.util.Date;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Entity
public class Taco {
	
	@NotNull(message="How can name be null???")
	@Size(min=5, message="Name must be at least 5 letters")
	private String name;
	
	@ManyToMany(targetEntity=Ingredient.class)
	@NotNull(message="You must choose at least 1 ingredient")
	private List<Ingredient> ingredients;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<Ingredient> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<Ingredient> ingredients) {
		this.ingredients = ingredients;
	}
	public Taco(String name, List<Ingredient> ingredients) {
		super();
		this.name = name;
		this.ingredients = ingredients;
	}
	
	public Taco() {}
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date createdAt;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	
	@PrePersist
	void createAt() {
		this.createdAt = new Date();
	}
	
	
	
	
}