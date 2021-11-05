package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.repos.RecipesCrudRepo;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    RecipesCrudRepo recipeRepo;

    @Autowired
    public RecipeService(RecipesCrudRepo recipeRepo) {
        this.recipeRepo = recipeRepo;
    }

    public Optional<Recipe> getRecipe(long id) {
        return recipeRepo.findById(id);
    }

    public long setRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        return recipeRepo.save(recipe).getId();
    }

    public boolean deleteRecipe(long id) {
        Optional<Recipe> recipeOptional = recipeRepo.findById(id);
        if (recipeOptional.isPresent()) {
            recipeRepo.delete(recipeOptional.get());
            return true;
        }
        return false;
    }


    public boolean updateRecipe(long id, Recipe updatedRecipe) {
        Optional<Recipe> optionalRecipe = recipeRepo.findById(id);
        if (optionalRecipe.isPresent()) {
            Recipe recipe = optionalRecipe.get();
            recipe.setDate(LocalDateTime.now());
            recipe.setCategory(updatedRecipe.getCategory());
            recipe.setDescription(updatedRecipe.getDescription());
            recipe.setDirections(updatedRecipe.getDirections());
            recipe.setIngredients(updatedRecipe.getIngredients());
            recipe.setName(updatedRecipe.getName());
            recipeRepo.save(recipe);
            return true;
        }
        return false;
    }

    public List<Recipe> findByName(String name) {
        //var recipes = recipeRepo.findByNameIgnoreCase("\"%" + name + "%\"");
        //var recipes = recipeRepo.findAll();
        //recipes.sort(Comparator.comparing(Recipe::getDate));
        //recipes.forEach(System.out::println);

        return recipeRepo.findAll().stream().filter(r -> r.getName().toUpperCase()
                .matches("(.+ +)?" + name.toUpperCase() + "( +.+)?"))
                .sorted(Comparator.comparing(Recipe::getDate).reversed())
                .collect(Collectors.toList());

    }

    public List<Recipe> findByCategory(String category) {
        //var recipes = recipeRepo.findByCategoryIgnoreCase("\"%" + category + "%\"");
        //System.out.println("\"%" + category + "%\"");
        //recipes.sort(Comparator.comparing(Recipe::getDate));
        //recipes.forEach(System.out::println);
        return  recipeRepo.findAll().stream().filter(r -> r.getCategory().toUpperCase()
                .matches("(.+ +)?" + category.toUpperCase() + ".*"))
                .sorted(Comparator.comparing(Recipe::getDate).reversed())
                .collect(Collectors.toList());
    }
}
