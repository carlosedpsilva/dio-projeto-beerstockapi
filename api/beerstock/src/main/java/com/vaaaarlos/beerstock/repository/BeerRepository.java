package com.vaaaarlos.beerstock.repository;

import com.vaaaarlos.beerstock.entity.Beer;

import org.springframework.data.jpa.repository.JpaRepository;

public interface BeerRepository extends JpaRepository<Beer, Long> { }
