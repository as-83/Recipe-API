package recipes.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Collection;

@Entity
@Table(name = "usr")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "user_id")
    @Getter(onMethod_=@JsonIgnore)
    private Long id;

    @Email()
    @Pattern(regexp= ".+@.+\\..+")
    @Column(name = "email")
    private String email;

    @Column(name = "password")
    @Size(min = 8)
    @NotBlank
    private String password;

    @Column(name = "role")
    private String role = "ROLE_USER";

    @Column(name = "recipes")
    @OneToMany(mappedBy = "author", fetch=FetchType.EAGER)
    private Collection<Recipe> recipes;

}
