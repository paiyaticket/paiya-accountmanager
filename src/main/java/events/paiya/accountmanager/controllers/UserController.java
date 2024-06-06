package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.mappers.UserMapper;
import events.paiya.accountmanager.resources.UserResource;
import events.paiya.accountmanager.services.UserService;
import events.paiya.accountmanager.services.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;


@RestController
@RequestMapping("/v1/users")
@CrossOrigin(origins = "http://localhost:4200", maxAge = 3600)
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    public UserController(UserServiceImpl userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/isexist")
    public ResponseEntity<Boolean> isUserExist(@RequestParam(value = "userId") String id) {
        return ResponseEntity.ok(userService.isUserExist(id));
    }
    

    @PostMapping
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody UserResource userResource) throws UserAlreadyExistException {
        URI uri = URI.create("/v1/users");
        if (SecurityContextHolder.getContext().getAuthentication() != null &&
                !"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            userResource.setId(userId);
        }
        User user = userMapper.userResourceToUser(userResource);
        User createdUser = this.userService.createUser(user);
        return ResponseEntity.created(uri).body(userMapper.userToUserResource(createdUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> findAllUser(){
        return ResponseEntity.ok(this.userService.findAllUser()
                .stream().map(userMapper::userToUserResource).collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<UserResource> findUserById(@PathVariable String id){
        User user = this.userService.findByUserId(id);
        return ResponseEntity.ok(userMapper.userToUserResource(user));
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


    @PatchMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@PathVariable String id, @Valid @RequestBody UserResource userResource){
        User user = userService.findByUserId(id);
        userMapper.updateUserFromResource(userResource, user);
        User updatedUser = this.userService.updateUser(user);
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable String id){
        this.userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

    // TODO: Add a body to this put Method
    @PutMapping("/{id}/status")
    public ResponseEntity<UserResource> changeUserAccountActiveStatus (@PathVariable String id, @PathParam("status") boolean status){
        User updatedUser = this.userService.changeUserAccountActiveStatus(id, status);
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }

}
