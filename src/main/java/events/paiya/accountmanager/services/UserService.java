package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.resources.StatusChangeResource;

import org.springframework.data.domain.Page;

import java.util.List;

public interface UserService {
    boolean isUserExist(String email);
    User findByEmail(String email);
    User findByUserId(String userId);
    List<User> findAllUser();
    Page<User> findPaginatedUserList(int page, int size);
    User createUser(User user) throws UserAlreadyExistException;
    User updateUser(User user);
    void deleteUser(String email);
    User changeUserAccountActiveStatus(StatusChangeResource statusChange);

    void deleteAll();
}
