package recipes;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.Size;
import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(name = "recipes")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Recipe {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    @Getter(onMethod_=@JsonIgnore)
    long id;



    @NotBlank(message = "Recipe name must not be blank!")
    private String name;

    @NotBlank(message = "Category must not be blank!")
    @Column(name = "category")
    private String category;

    @Column(name = "creation_date")

    private LocalDateTime date;

    @NotBlank(message = "Recipe name must not be blank!")
    private String  description;

    @ElementCollection
    @CollectionTable(name = "ingredients", joinColumns = @JoinColumn(name = "recipe_id"))
    @Column(name = "ingredients")
    @NotEmpty
    @Size(min = 1)
    private List<String> ingredients;

    @ElementCollection
    @CollectionTable(name = "directions", joinColumns = @JoinColumn(name = "recipe_id"))
    @NotEmpty
    @Size(min = 1)
    private List<String> directions;
}
