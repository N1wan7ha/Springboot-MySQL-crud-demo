package org.example.web;

import org.example.model.UserNS;
import org.example.repo.UserNSRepository;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserNSController {

    private final UserNSRepository repo;
    public UserNSController(UserNSRepository repo) { this.repo = repo; }

    // CREATE
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UserNS create(@RequestBody UserNS user) {
        return repo.save(user);
    }

    // READ all
    @GetMapping
    public List<UserNS> getAll() {
        return repo.findAll();
    }

    // READ one
    @GetMapping("/{id}")
    public UserNS getOne(@PathVariable Long id) {
        return repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
    }

    // UPDATE
    @PutMapping("/{id}")
    public UserNS update(@PathVariable Long id, @RequestBody UserNS body) {
        UserNS u = repo.findById(id).orElseThrow(() ->
                new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found"));
        u.setName(body.getName());
        u.setEmail(body.getEmail());
        return repo.save(u);
    }

    // DELETE
    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long id) {
        if (!repo.existsById(id)) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "User not found");
        }
        repo.deleteById(id);
    }
}
