package tacos;

import java.util.Date;
import java.util.Set;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.PrePersist;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;

@Entity
@Data
public class Taco {
	
	@NotNull(message="How can name be null???")
	@Size(min=5, message="Name must be at least 5 letters")
	private String name;
	
	@ManyToMany(targetEntity=Ingredient.class)
	@Size(min=1, message="You must choose at least 1 ingredient")
	@NotNull(message="You must choose at least 1 ingredient")
	private Set<Ingredient> ingredients;
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	private Date createdAt;
	
	@PrePersist
	void createAt() {
		this.createdAt = new Date();
	}	
	
}