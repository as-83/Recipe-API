package recipes.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import recipes.model.Recipe;
import recipes.model.User;
import recipes.repos.RecipesCrudRepo;
import recipes.repos.UserRepo;

import java.time.LocalDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class RecipeService {
    RecipesCrudRepo recipeRepo;
    UserRepo userRepo;

    @Autowired
    public RecipeService(RecipesCrudRepo recipeRepo, UserRepo userRepo ) {
        this.recipeRepo = recipeRepo;
        this.userRepo = userRepo;
    }

    public Optional<Recipe> getRecipe(long id) {
        return recipeRepo.findById(id);
    }

    public long setRecipe(Recipe recipe) {
        recipe.setDate(LocalDateTime.now());
        recipe.setAuthor(getCurrentUser());
        return recipeRepo.save(recipe).getId();
    }

    public HttpStatus deleteRecipe(long id) {
        Optional<Recipe> recipeOptional = recipeRepo.findById(id);
        if (recipeOptional.isPresent()) {
            User currentUser = getCurrentUser();
            if (currentUser == recipeOptional.get().getAuthor()) {
                recipeRepo.delete(recipeOptional.get());
                return HttpStatus.NO_CONTENT;
            }
            return HttpStatus.FORBIDDEN;
        }
        return HttpStatus.NOT_FOUND;
    }

    private User getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext()
                .getAuthentication();
        return userRepo.findByEmail(authentication.getName()).get();
    }


    public HttpStatus updateRecipe(long id, Recipe updatedRecipe) {
        Optional<Recipe> optionalRecipe = recipeRepo.findById(id);
        if (optionalRecipe.isPresent()) {
            if (getCurrentUser() == optionalRecipe.get().getAuthor()) {
                Recipe recipe = optionalRecipe.get();
                recipe.setDate(LocalDateTime.now());
                recipe.setCategory(updatedRecipe.getCategory());
                recipe.setDescription(updatedRecipe.getDescription());
                recipe.setDirections(updatedRecipe.getDirections());
                recipe.setIngredients(updatedRecipe.getIngredients());
                recipe.setName(updatedRecipe.getName());
                recipe.setAuthor(getCurrentUser());
                recipeRepo.save(recipe);
                return HttpStatus.NO_CONTENT;
            }
            return HttpStatus.FORBIDDEN;
        }
        return HttpStatus.NOT_FOUND;
    }

    public List<Recipe> findByName(String name) {

        return recipeRepo.findAll().stream().filter(r -> r.getName().toUpperCase()
                .matches("(.+ +)?" + name.toUpperCase() + "( +.+)?"))
                .sorted(Comparator.comparing(Recipe::getDate).reversed())
                .collect(Collectors.toList());

    }

    public List<Recipe> findByCategory(String category) {
        return  recipeRepo.findAll().stream().filter(r -> r.getCategory().toUpperCase()
                .matches("(.+ +)?" + category.toUpperCase() + ".*"))
                .sorted(Comparator.comparing(Recipe::getDate).reversed())
                .collect(Collectors.toList());
    }
}
