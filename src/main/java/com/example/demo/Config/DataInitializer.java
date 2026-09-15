package com.example.demo.Config;

import com.example.demo.Entity.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;

import jakarta.transaction.Transactional;
import org.springframework.boot.CommandLineRunner;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;

@Component
public class DataInitializer implements CommandLineRunner {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    @Transactional
    public void run(@NonNull String... args) {
        entityManager.persist(new User("wiucwnc", "chwcuiw", "eine@gmail.com"));
    }
}