package com.tkachev.adboard.repositories;

import com.tkachev.adboard.entity.Category;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryRepository extends JpaRepository<Category, Long> {
}
