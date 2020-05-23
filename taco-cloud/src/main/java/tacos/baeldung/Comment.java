package tacos.baeldung;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
 
    private String reply;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private Post post;
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;

}
