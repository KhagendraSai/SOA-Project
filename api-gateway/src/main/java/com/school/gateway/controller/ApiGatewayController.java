package com.school.gateway.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiGatewayController {
	@GetMapping("/")
    public String home() {
        return "API Gateway is running successfully!";
    }
}
