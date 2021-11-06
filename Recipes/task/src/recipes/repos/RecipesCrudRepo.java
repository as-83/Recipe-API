package recipes.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import recipes.model.Recipe;

public interface RecipesCrudRepo extends JpaRepository<Recipe, Long> {

}
