package events.paiya.accountmanager.services;

import events.paiya.accountmanager.domains.Address;
import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.repositories.UserRepository;
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
    public User updateUser(String userId, User user) {
        Optional<User> userOptional = this.userRepository.findById(userId);
        if (userOptional.isPresent()){
            User oldUser = userOptional.get();
            if (!user.getFirstname().equals(oldUser.getFirstname())) oldUser.setFirstname(user.getFirstname());
            if (!user.getLastname().equals(oldUser.getLastname())) oldUser.setLastname(user.getLastname());
            if (!user.getEmail().equals(oldUser.getEmail())) oldUser.setEmail(user.getEmail());
            if (!user.getGender().equals(oldUser.getGender())) oldUser.setGender(user.getGender());
            if (!user.getPhoneNumber().equals(oldUser.getPhoneNumber())) oldUser.setPhoneNumber(user.getPhoneNumber());
            if (!user.isActive() == oldUser.isActive()) oldUser.setActive(user.isActive());
            return this.userRepository.save(oldUser);
        } else {
            throw new NoSuchElementException();
        }
    }

    @Override
    public void updateUserAddress(String id, Address address) {
        userRepository.updateUserAddressById(id, address);
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

}
