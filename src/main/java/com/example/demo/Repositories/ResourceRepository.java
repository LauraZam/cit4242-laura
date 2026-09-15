package com.example.demo.Repositories;

import com.example.demo.Models.SavedResource;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ResourceRepository extends JpaRepository<SavedResource, Long> {
}
