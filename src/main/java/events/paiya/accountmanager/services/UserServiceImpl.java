package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.repositories.UserRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
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
    public Page<User> findPaginatedUserList(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return this.userRepository.findAll(pageable);
    }

    @Override
    public User createUser(User user) {
        return this.userRepository.save(user);
    }

    @Override
    public User updateUser(String userId, User user) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        User oldUser = null;
        if (userOptional.isPresent()){
            oldUser = userOptional.get();
            if (!user.getFirstname().equals(oldUser.getFirstname())) oldUser.setFirstname(user.getFirstname());
            if (!user.getLastname().equals(oldUser.getLastname())) oldUser.setLastname(user.getLastname());
            if (!user.getEmail().equals(oldUser.getEmail())) oldUser.setEmail(user.getEmail());
            if (!user.getGender().equals(oldUser.getGender())) oldUser.setGender(user.getGender());
            if (!user.getPhoneNumber().equals(oldUser.getPhoneNumber())) oldUser.setPhoneNumber(user.getPhoneNumber());
            if (!user.getOrganizer().equals(oldUser.getOrganizer())) oldUser.setOrganizer(user.getOrganizer());
            oldUser = this.userRepository.save(oldUser);
        }
        return oldUser;
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
