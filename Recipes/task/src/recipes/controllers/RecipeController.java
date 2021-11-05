package recipes.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import recipes.model.Recipe;
import recipes.services.RecipeService;

import javax.validation.Valid;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
public class RecipeController {
    final RecipeService recipeService;

    @Autowired
    public RecipeController(RecipeService recipeService) {
        this.recipeService = recipeService;
    }

    @GetMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> getRecipe(@PathVariable("id") int id){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        Optional<Recipe> recipeOptional = recipeService.getRecipe(id);
        if (! recipeOptional.isPresent()) {
            return ResponseEntity.status(404)
                    .headers(responseHeaders)
                    .body(null);
        }
        return ResponseEntity.status(200)
                .headers(responseHeaders)
                .body(recipeOptional.get());
    }

    @PostMapping("/api/recipe/new")
    public ResponseEntity<String> setRecipe(@Valid @RequestBody Recipe newRecipe) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        long id = recipeService.setRecipe(newRecipe);
        return ResponseEntity.status(HttpStatus.OK)
                .headers(responseHeaders)
                .body("{ \"id\":" + id + "}");
    }

    @DeleteMapping("/api/recipe/{id}")
    public ResponseEntity<String> deleteRecipe(@PathVariable("id") long id) {
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        HttpStatus status = HttpStatus.NOT_FOUND;
        if (recipeService.deleteRecipe(id)) {
            status =  HttpStatus.NO_CONTENT;
        }
        return ResponseEntity.status(status)
                .headers(responseHeaders)
                .body("");
    }

    @PutMapping("/api/recipe/{id}")
    public ResponseEntity<Recipe> updateRecipe(@PathVariable("id") long id, @Valid @RequestBody Recipe updatedRecipe){
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        HttpStatus status = HttpStatus.NO_CONTENT;
        if (! recipeService.updateRecipe(id, updatedRecipe)) {
            status = HttpStatus.NOT_FOUND;
        }
        return ResponseEntity.status(status)
                .headers(responseHeaders)
                .body(null);
    }

    @GetMapping("/api/recipe/search")
    public ResponseEntity<List<Recipe>> searchRecipes( @RequestParam(required = false) String category,
                                                       @RequestParam(required = false) String name) {
        HttpStatus status = HttpStatus.OK;
        List<Recipe> recipes;
        if (name != null && category == null) {
            recipes = recipeService.findByName(name);
        } else if (name == null && category != null) {
            recipes = recipeService.findByCategory(category);
        } else {
            recipes = Collections.emptyList();
            status = HttpStatus.BAD_REQUEST;
        }
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.set("Content-Type", "application/json");
        return ResponseEntity.status(status)
                .headers(responseHeaders)
                .body(recipes);
    }
}
