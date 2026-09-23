package com.school.result.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "STUDENT-SERVICE")
public interface StudentClient {

    @GetMapping("/api/students/{id}")
    Object getStudent(@PathVariable("id") Long id);
}
