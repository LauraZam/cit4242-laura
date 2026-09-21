package com.example.demo.Requests;

public record HelloRequest(
        String name,
        String firstName,
        String lastName,
        String email
) {}