package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.mappers.UserMapper;
import events.paiya.accountmanager.resources.UserResource;
import events.paiya.accountmanager.services.UserService;
import events.paiya.accountmanager.services.UserServiceImpl;
import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    private UserController(UserServiceImpl userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> findAllUser(){
        return ResponseEntity.ok(this.userService.findAllUser().stream().map(userMapper::userToUserResource).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findUserById(@PathVariable String id){
        User user = this.userService.findByUserId(id);
        return ResponseEntity.ok(userMapper.userToUserResource(user));
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody User user){
        URI uri = URI.create("/v1/users");
        User createdUser = this.userService.createUser(user);
        return ResponseEntity.created(uri).body(userMapper.userToUserResource(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@PathVariable String id, @Valid @RequestBody User user){
        User updatedUser = this.userService.updateUser(id, user);
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id){
        this.userService.deleteUser(id);
    }
}
