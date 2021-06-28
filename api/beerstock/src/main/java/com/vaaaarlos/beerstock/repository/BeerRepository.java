package com.vaaaarlos.beerstock.repository;

import java.util.Optional;

import com.vaaaarlos.beerstock.entity.Beer;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface BeerRepository extends JpaRepository<Beer, Long> {

	Optional<Beer> findByName(String name);

  @Query("SELECT b FROM Beer b WHERE LOWER(b.name) = LOWER(:name) OR :name IS NULL")
	Page<Beer> pageAll(Pageable pageRequest, @Param("name") String name);

}
