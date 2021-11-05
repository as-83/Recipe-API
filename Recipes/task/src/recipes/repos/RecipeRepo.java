package recipes.repos;

import org.springframework.stereotype.Repository;
import recipes.model.Recipe;

import java.util.ArrayList;
import java.util.List;

@Repository
public class RecipeRepo {
    private List<Recipe> recipes = new ArrayList<>();


    public Recipe getRecipe(int id) {
        if (id > 0 && id <= recipes.size()){
            return recipes.get(id - 1);
        }
        return null;
    }

    public int setRecipe(Recipe recipe) {
        int id = recipes.size();
        this.recipes.add(id, recipe);
        return id + 1;
    }
}
