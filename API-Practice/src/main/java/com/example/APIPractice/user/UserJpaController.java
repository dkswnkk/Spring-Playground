package com.example.APIPractice.user;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/jpa")
@Slf4j
public class UserJpaController {

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/users")
    public List<User> retrieveAllUsers() {
        return userRepository.findAll();
    }

    @GetMapping("/users/{id}")
//    @Transactional
    public Optional<User> retrieveUser(@PathVariable int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFountException(String.format("ID: %s not found", id));
        }
        return user;
    }

    @DeleteMapping("/users/{id}")
    public void deleteUser(@PathVariable int id) {
        userRepository.deleteById(id);
    }

    @PostMapping("/users")
    public ResponseEntity<User> createUser(@Valid @RequestBody User user) {
        User saveUser = userRepository.save(user);
        URI location = ServletUriComponentsBuilder.fromCurrentRequest()
                .path("/{id}")
                .buildAndExpand(saveUser.getId())
                .toUri();
        return ResponseEntity.created(location).build();
    }


    @GetMapping("/users/{id}/posts")
    public List<PostDto> retrieveAllPostsByUser(@PathVariable int id) {
//        Optional<User> user = userRepository.findById(id);
//        if (user.isEmpty()) {
//            throw new UserNotFountException(String.format("ID: %s not found", id));
//        }
//        return user.get().getPosts();
        return dd(id);
    }

    @Transactional
    public List<PostDto> dd(int id) {
        Optional<User> user = userRepository.findById(id);
        if (user.isEmpty()) {
            throw new UserNotFountException(String.format("ID: %s not found", id));
        }

        List<PostDto> postDtos = user.get().getPosts().stream()
                .map(post -> new PostDto(post.getId(), post.getDescription()))
                .collect(Collectors.toList());

        return postDtos;
    }

}
