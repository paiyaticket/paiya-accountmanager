package events.paiya.accountmanager.controllers;

import events.paiya.accountmanager.domains.Address;
import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.mappers.AddressMapper;
import events.paiya.accountmanager.mappers.UserMapper;
import events.paiya.accountmanager.resources.AddressResource;
import events.paiya.accountmanager.resources.UserResource;
import events.paiya.accountmanager.services.UserService;
import events.paiya.accountmanager.services.UserServiceImpl;
import jakarta.validation.Valid;
import jakarta.websocket.server.PathParam;
import lombok.extern.slf4j.Slf4j;
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
@Slf4j
public class UserController {
    private final UserService userService;

    private final UserMapper userMapper;
    private final AddressMapper addressMapper;
    private UserController(UserServiceImpl userService, UserMapper userMapper, AddressMapper addressMapper){
        this.userService = userService;
        this.userMapper = userMapper;
        this.addressMapper = addressMapper;
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
    public ResponseEntity<UserResource> createUser(@Valid @RequestBody UserResource userResource) throws UserAlreadyExistException {
        URI uri = URI.create("/v1/users");
        if (!"anonymousUser".equals(SecurityContextHolder.getContext().getAuthentication().getName())){
            String userId = SecurityContextHolder.getContext().getAuthentication().getName();
            userResource.setId(userId);
        }
        User user = userMapper.userResourceToUser(userResource);
        User createdUser = this.userService.createUser(user);
        return ResponseEntity.created(uri).body(userMapper.userToUserResource(createdUser));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserResource> updateUser(@PathVariable String id, @Valid @RequestBody UserResource userResource){
        User user = userMapper.userResourceToUser(userResource);
        User updatedUser = this.userService.updateUser(id, user);
        return ResponseEntity.ok(userMapper.userToUserResource(updatedUser));
    }
    @PutMapping("/{id}/address")
    public ResponseEntity<UserResource> updateUserAddress(@PathVariable String id, @Valid @RequestBody AddressResource addressResource) {
        Address address = addressMapper.addressResourceToAddress(addressResource);
        this.userService.updateUserAddress(id, address);
        return ResponseEntity.ok(userMapper.userToUserResource(this.userService.findByUserId(id)));
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
