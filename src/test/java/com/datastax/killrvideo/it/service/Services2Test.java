package com.datastax.killrvideo.it.service;

import cucumber.api.CucumberOptions;
import cucumber.api.junit.Cucumber;
import org.junit.runner.RunWith;

@RunWith(Cucumber.class)
@CucumberOptions(
        strict=false,
        plugin = {"progress", "html:/tmp/cucumber-report"},
        features = "src/test/resources/service2")
public class Services2Test {
}
