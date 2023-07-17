package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.mappers.UserMapper;
import events.paiya.accountmanager.resources.UserResource;
import events.paiya.accountmanager.services.UserService;
import events.paiya.accountmanager.services.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.log4j.Log4j2;
import org.apache.logging.log4j.Level;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/users")
@Log4j2
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;
    private UserController(UserServiceImpl userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> findAllUser(){
        return ResponseEntity.ok(this.userService.findAllUser()
                .stream().map(userMapper::userToUserResource).collect(Collectors.toList()));
    }

    @GetMapping("/paginated")
    public ResponseEntity<List<UserResource>> findUserPaginatedList(@RequestParam int page, @RequestParam int size){
        Page<User> paginatedUserList = this.userService.findPaginatedUserList(page, size);
        List<UserResource> userResourceList = paginatedUserList.stream().map(userMapper::userToUserResource).toList();
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.add("Total-Elements", String.valueOf(paginatedUserList.getTotalElements()));
        httpHeaders.add("Total-Pages", String.valueOf(paginatedUserList.getTotalPages()));
        return ResponseEntity.ok().headers(httpHeaders).body(userResourceList);
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findUserById(@PathVariable String id){
        User user = this.userService.findByUserId(id);
        return ResponseEntity.ok(userMapper.userToUserResource(user));
    }

    @PostMapping
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody User user) throws UserAlreadyExistException {
        URI uri = URI.create("/v1/users");
        User createdUser = this.userService.createUser(user);
        return ResponseEntity.created(uri).body(userMapper.userToUserResource(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@PathVariable String id, @Valid @RequestBody UserResource userResource){
        log.log(Level.INFO, userResource.toString());
        User user = userMapper.userResourceToUser(userResource);
        log.log(Level.INFO, user.toString());
        User updatedUser = this.userService.updateUser(id, userMapper.userResourceToUser(userResource));
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        this.userService.deleteUser(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/status")
    public ResponseEntity<UserResource> changeUserAccountActiveStatus (@PathVariable String id, @PathParam("status") boolean status){
        User updatedUser = this.userService.changeUserAccountActiveStatus(id, status);
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }

}
