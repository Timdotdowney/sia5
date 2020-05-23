package tacos.baeldung;

import javax.persistence.Entity;
import javax.persistence.Id;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Person {
 
    @Id
    private Long id;
    private String firstName;
    private String lastName;
 
 
}
