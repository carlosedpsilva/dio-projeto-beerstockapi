package com.vaaaarlos.beerstock.cucumber;

import org.junit.runner.RunWith;

import io.cucumber.junit.Cucumber;
import io.cucumber.junit.CucumberOptions;

@RunWith(Cucumber.class)
@CucumberOptions(
    features = "classpath:features",
    extraGlue = { "com.vaaaarlos.beerstock.stepdef" },
    plugin = { "pretty", "html:build/reports/cucumber/cucumber-report.html" })
public class CucumberTestRunner { }
