package com.example.demo.Controllers;

import com.example.demo.Requests.HelloRequest;
import com.example.demo.Responses.HelloResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController("")
public class TestController {
    @GetMapping("/hello")
    public HelloResponse hello(@RequestParam String name) {
            return new HelloResponse("Hello " + name);
    }
    @PostMapping("/hello")
    public HelloResponse postHello(@RequestBody HelloRequest request) {
        return new HelloResponse("Received POST for: " + request.getName());
    }
    @PostMapping("/create")
    public ResponseEntity<HelloResponse> createResource(@RequestBody HelloRequest request) {
        HelloResponse response = new HelloResponse("Created resource for: " + request.getName());
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }
}