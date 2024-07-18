package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.mappers.UserMapper;
import events.paiya.accountmanager.resources.StatusChangeResource;
import events.paiya.accountmanager.resources.UserResource;
import events.paiya.accountmanager.services.UserService;
import events.paiya.accountmanager.services.UserServiceImpl;
import jakarta.validation.Valid;

import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@CrossOrigin(origins = "http://localhost:4200", methods = {RequestMethod.GET, RequestMethod.POST})
@RestController
@RequestMapping("/v1/users")
public class UserController {
    private final UserService userService;
    private final UserMapper userMapper;
    public UserController(UserServiceImpl userService, UserMapper userMapper){
        this.userService = userService;
        this.userMapper = userMapper;
    }

    @GetMapping("/isexist")
    public ResponseEntity<Boolean> isUserExist(@RequestParam(value = "email") String email) {
        return ResponseEntity.ok(userService.isUserExist(email));
    }
    

    @PostMapping
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody UserResource userResource) throws UserAlreadyExistException {
        URI uri = URI.create("/v1/users");
        User user = userMapper.userResourceToUser(userResource);
        User createdUser = this.userService.createUser(user);
        return ResponseEntity.created(uri).body(userMapper.userToUserResource(createdUser));
    }

    @GetMapping
    public ResponseEntity<List<UserResource>> findAllUser(){
        return ResponseEntity.ok(this.userService.findAllUser()
                .stream().map(userMapper::userToUserResource).collect(Collectors.toList()));
    }

    @GetMapping("/{email}")
    public ResponseEntity<UserResource> findUserByEmail(@PathVariable(name = "email") String email){
        User user = this.userService.findByEmail(email);
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


    @PatchMapping("/{email}")
    public ResponseEntity<UserResource> updateUser(@PathVariable(name = "email") String email, 
                                                    @Valid @RequestBody UserResource userResource){
        User user = userService.findByEmail(email);
        userMapper.updateUserFromResource(userResource, user);
        User updatedUser = this.userService.updateUser(user);
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }

    @DeleteMapping("/{email}")
    public ResponseEntity<?> delete(@PathVariable(name = "email") String email){
        this.userService.deleteUser(email);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/status/change")
    public ResponseEntity<UserResource> changeUserAccountActiveStatus (@RequestBody StatusChangeResource statusChange){
        User updatedUser = this.userService.changeUserAccountActiveStatus(statusChange);
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }

}
