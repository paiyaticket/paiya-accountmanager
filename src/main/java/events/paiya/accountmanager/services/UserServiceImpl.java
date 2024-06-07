package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException.NotFound;

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
    public boolean isUserExist(String userId){
        return this.userRepository.existsById(userId);
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
    public void deleteUser(String userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public User changeUserAccountActiveStatus(String userId, boolean status) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isPresent()){
            User user = userOptional.get();
            if (status){
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
    public User findByEmail(String email) throws NotFound {
        return userRepository.findByEmailAndActiveIsTrue(email).orElseThrow();
    }

}
