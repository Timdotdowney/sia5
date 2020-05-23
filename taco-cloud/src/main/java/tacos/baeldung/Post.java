package tacos.baeldung;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedAttributeNode;
import javax.persistence.NamedEntityGraph;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.NamedSubgraph;
import javax.persistence.OneToMany;

import lombok.Data;

@NamedQueries({
    @NamedQuery(name ="Post.findAll", query = "SELECT p FROM Post p")
})

@NamedEntityGraph(
		  name = "post-entity-graph-with-comment-users",
		  attributeNodes = {
		    @NamedAttributeNode("subject"),
		    @NamedAttributeNode("user"),
		    @NamedAttributeNode(value = "comments", subgraph = "comments-subgraph"),
		  },
		  subgraphs = {
		    @NamedSubgraph(
		      name = "comments-subgraph",
		      attributeNodes = {
		        @NamedAttributeNode("user")
		      }
		    )
		  }
		)
@Entity
@Data
public class Post {
 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    
    private String subject;
    
    @OneToMany(mappedBy = "post")
    private List<Comment> comments = new ArrayList<>();
 
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn
    private User user;
    
}
