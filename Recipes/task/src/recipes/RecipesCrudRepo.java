package recipes;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;


public interface RecipesCrudRepo extends JpaRepository<Recipe, Long> {

    @Query("SELECT r  FROM Recipe r WHERE r.name LIKE ?1  order by r.date DESC ")
    //@Query("SELECT r  FROM Recipe r")
    List<Recipe> findByNameIgnoreCase(String name);

    @Query("SELECT r  FROM Recipe r WHERE r.category LIKE  ?1  order by r.date DESC ")
    //@Query("SELECT r  FROM Recipe r")
    List<Recipe> findByCategoryIgnoreCase(String category);
}
