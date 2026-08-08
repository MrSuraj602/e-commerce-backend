package com.MrSuraj.eco.ecommerce.repo;

import com.MrSuraj.eco.ecommerce.entity.Category;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CategoryRepository extends JpaRepository<Category,Long> {
    Category findByName(String name);

    @Query("SELECT c from Category c WHERE c.name=:name AND c.parentCategory.name=:parentCategoryName")
    Category findByNameAndParant(@Param("name")String name,
                                 @Param("parentCategoryName") String parentCategoryName);
}
