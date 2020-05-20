package tacos;

import lombok.Data;

@Data
public class FooBean {
	private final String id;
	private final String name;
	private final Type type;
	public static enum Type {
	WRAP, PROTEIN, VEGGIES, CHEESE, SAUCE}
}
