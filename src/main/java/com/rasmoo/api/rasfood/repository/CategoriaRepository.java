package com.rasmoo.api.rasfood.repository;

import com.rasmoo.api.rasfood.entity.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoriaRepository extends JpaRepository<Categoria, Integer> {
}
