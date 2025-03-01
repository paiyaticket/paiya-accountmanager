package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.repositories.UserRepository;
import events.paiya.accountmanager.resources.StatusChangeResource;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.ResourceBundle;

@Component
public class UserServiceImpl implements UserService{

    private final ResourceBundle resourceBundle = ResourceBundle.getBundle("messages");
    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public boolean isUserExist(String email){
        return this.userRepository.existsByEmailAndActiveIsTrue(email);
    }

    @Override
    public User findByUserId(String userId) {
        Optional<User> userOptional = this.userRepository.findUserByIdAndActiveIsTrue(userId);
        return userOptional.orElseThrow();
    }

    @Override
    public List<User> findAllUser() {
        return this.userRepository.findAllByActiveIsTrue();
    }

    @Override
    public Page<User> findPaginatedUserList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.userRepository.findAllByActiveIsTrue(pageable);
    }

    @Override
    public User createUser(User user) throws UserAlreadyExistException {
        if (this.userRepository.existsByEmailAndActiveIsTrue(user.getEmail()))
            throw new UserAlreadyExistException(resourceBundle.getString("user.already.exist"));

        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public void deleteUser(String email) {
        this.userRepository.deleteByEmail(email);
    }

    @Override
    public User changeUserAccountActiveStatus(StatusChangeResource statusChange) {
        Optional<User> userOptional = this.userRepository.findByEmail(statusChange.getEmail());
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if (statusChange.isStatus()){
                user.enableUser();
            } else {
                user.disableUser();
            }
            return this.userRepository.save(user);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void deleteAll() {
        userRepository.deleteAll();
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmailAndActiveIsTrue(email).orElseThrow();
    }

}
