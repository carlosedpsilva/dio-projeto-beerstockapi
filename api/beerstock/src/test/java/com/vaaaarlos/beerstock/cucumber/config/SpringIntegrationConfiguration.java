package com.vaaaarlos.beerstock.cucumber.config;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;

import io.cucumber.spring.CucumberContextConfiguration;

@SpringBootTest
@CucumberContextConfiguration
@TestExecutionListeners(listeners = {CucumberFeatureDirtyContextTestExecutionListener.class})
public class SpringIntegrationConfiguration { }
