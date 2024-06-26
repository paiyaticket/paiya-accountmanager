package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.resources.StatusChangeResource;

import org.springframework.data.domain.Page;
import org.springframework.web.client.HttpClientErrorException;

import java.util.List;

public interface UserService {
    boolean isUserExist(String email);
    User findByEmail(String email) throws HttpClientErrorException.NotFound;
    User findByUserId(String userId) throws HttpClientErrorException.NotFound;
    List<User> findAllUser();
    Page<User> findPaginatedUserList(int page, int size);
    User createUser(User user) throws UserAlreadyExistException;
    User updateUser(User user);
    void deleteUser(String email);
    User changeUserAccountActiveStatus(StatusChangeResource statusChange);

    void deleteAll();
}
