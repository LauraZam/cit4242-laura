package com.example.demo.Controllers;

import com.example.demo.Entity.Dto.ColumnResponseDto;
import com.example.demo.Entity.User;
import com.example.demo.Requests.HelloRequest;
import com.example.demo.Responses.HelloResponse;
import com.example.demo.Models.SavedResource;
import com.example.demo.Repositories.ResourceRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
public class TestController {

    private final ResourceRepository repository;

    public TestController(ResourceRepository repository) {
        this.repository = repository;
    }

    @PostMapping("/create")
    public ResponseEntity<HelloResponse> createResource(@RequestBody HelloRequest request) {
        SavedResource resource = new SavedResource(request.getName());
        SavedResource saved = repository.save(resource);
        return new ResponseEntity<>(new HelloResponse("Saved to H2 with ID " + saved.getId() + ": " + saved.getName()), HttpStatus.CREATED);
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<HelloResponse> updateResource(@PathVariable Long id, @RequestBody HelloRequest request) {
        Optional<SavedResource> existingRecord = repository.findById(id);
        if (existingRecord.isPresent()) {
            SavedResource resource = existingRecord.get();
            String oldName = resource.getName();
            resource.setName(request.getName());
            repository.save(resource);
            return new ResponseEntity<>(new HelloResponse("Rewrote ID " + id + " from '" + oldName + "' to '" + request.getName() + "'"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HelloResponse("ID " + id + " not found in DB"), HttpStatus.NOT_FOUND);
    }

    @GetMapping("/all")
    public ResponseEntity<List<SavedResource>> getAllResources() {
        List<SavedResource> resources = repository.findAll(); // Берем всё из H2
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<HelloResponse> deleteResource(@PathVariable Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return new ResponseEntity<>(new HelloResponse("Resource with ID " + id + " successfully deleted"), HttpStatus.OK);
        }
        return new ResponseEntity<>(new HelloResponse("ID " + id + " not found in DB"), HttpStatus.NOT_FOUND);
    }

    @PersistenceContext
    private EntityManager entityManager;

    @PostMapping("/users")
    @Transactional
    public ColumnResponseDto createUser(@RequestBody HelloRequest request) {
        User user = new User(
                request.getFirstName(),
                request.getLastName(),
                request.getEmail()
        );
        entityManager.persist(user);

        return new ColumnResponseDto(
                user.getId(),
                user.getFirstName(),
                user.getLastName(),
                user.getEmail()
        );
    }

    @GetMapping("/users")
    public ResponseEntity<List<ColumnResponseDto>> getAllUsers() {
        List<User> users = entityManager.createQuery("SELECT u FROM User u", User.class).getResultList();

        List<ColumnResponseDto> response = users.stream()
                .map(user -> new ColumnResponseDto(
                        user.getId(),
                        user.getFirstName(),
                        user.getLastName(),
                        user.getEmail()
                ))
                .toList();

        return new ResponseEntity<>(response, HttpStatus.OK);
    }
}
