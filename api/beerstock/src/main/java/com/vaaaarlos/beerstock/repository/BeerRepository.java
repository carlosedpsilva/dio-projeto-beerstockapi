package com.vaaaarlos.beerstock.repository;

import java.util.Optional;

import com.vaaaarlos.beerstock.entity.Beer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> {

	Optional<Beer> findByName(String name);

}
