package com.qeema.practicaltest.ordermanagement.bdds.steps.config;

import io.cucumber.spring.CucumberContextConfiguration;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.boot.test.context.SpringBootTest;
import com.qeema.practicaltest.ordermanagement.OrderManagementApplication;

@CucumberContextConfiguration
@SpringBootTest(classes = OrderManagementApplication.class, webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
// Replace with your main application class
@ActiveProfiles("test")
public class CucumberSpringConfiguration {
}