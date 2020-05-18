package tacos;

import java.util.Date;
import java.util.List;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class Taco {
	
	@NotNull(message="How can name be null???")
	@Size(min=5, message="Name must be at least 5 letters")
	private String name;
	
	@NotNull(message="You must choose at least 1 ingredient")
	private List<String> ingredients;
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public List<String> getIngredients() {
		return ingredients;
	}
	public void setIngredients(List<String> ingredients) {
		this.ingredients = ingredients;
	}
	public Taco(String name, List<String> ingredients) {
		super();
		this.name = name;
		this.ingredients = ingredients;
	}
	
	public Taco() {}
	
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
	
	
	
	
}