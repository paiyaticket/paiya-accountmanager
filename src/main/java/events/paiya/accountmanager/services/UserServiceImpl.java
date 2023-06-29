package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.repositories.UserRepository;
import org.springframework.data.domain.Pageable;

import java.util.List;
import java.util.Optional;

public class UserServiceImpl implements UserService{

    private final UserRepository userRepository;

    public UserServiceImpl(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Override
    public Optional<User> findByUserId(String userId) {
        return this.userRepository.findById(userId);
    }

    @Override
    public List<User> findAllUser() {
        return this.userRepository.findAll();
    }

    @Override
    public List<User> findPaginatedUserList() {
        // Pageable pageable = Pageable.ofSize(10);
        return null;
    }

    @Override
    public User createUser(User user) {
        return null;
    }

    @Override
    public User updateUser(String userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            user.disableUser();
            this.userRepository.save(user);
        });
        return null;
    }

    @Override
    public void deleteUser(String userId) {
        this.userRepository.deleteById(userId);
    }

    @Override
    public void disableUserAccount(String userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            user.disableUser();
            this.userRepository.save(user);
        });
    }

    @Override
    public void enableUserAccount(String userId) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        userOptional.ifPresent(user -> {
            user.enableUser();
            this.userRepository.save(user);
        });
    }
}
