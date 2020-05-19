package tacos;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;

@Entity
public class Ingredient {
	
	@Id
	private String id;
	private String name;
	@Enumerated(EnumType.STRING)
	private Type type;

	public static enum Type {
		WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE
	}
	
	public String getId() {
		return id;
	}
	
	public void setId(String id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}

	public Type getType() {
		return type;
	}
	
	public void setType(Type type) {
		this.type = type;
	}

	public Ingredient(String id, String name, Type type) {
		super();
		this.id = id;
		this.name = name;
		this.type = type;
	}
	
	public Ingredient() {
		id = null;
		name = null;
		type = Type.WRAP;
	}
	
	
}