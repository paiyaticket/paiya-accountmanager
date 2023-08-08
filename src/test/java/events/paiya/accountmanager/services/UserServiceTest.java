package events.paiya.accountmanager.services;

import events.paiya.accountmanager.configs.ApiSecurityConfig;
import events.paiya.accountmanager.domains.Address;
import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.enumerations.Gender;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.repositories.UserRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = ApiSecurityConfig.class)
class UserServiceTest {
    private final String USER_ID = "64acee0e2162f374bd198208";
    @Mock
    private UserRepository userRepository;
    @InjectMocks
    private UserServiceImpl userService;

    @Test
    void givenId_whenFound_thenReturnUser() {
        Mockito.when(userRepository.findUserByIdAndActiveIsTrue(Mockito.anyString())).thenReturn(Optional.of(this.buildUser()));
        User user = userService.findByUserId(USER_ID);
        Assert.notNull(user, "User ne doit pas être nul");
        Assertions.assertEquals(USER_ID, user.getId());
        Assertions.assertEquals("Johny", user.getFirstname());
    }

    @Test
    void givenId_whenNotFound_thenThrowException() {
        Mockito.when(userRepository.findUserByIdAndActiveIsTrue(Mockito.anyString())).thenThrow(NoSuchElementException.class);
        Assertions.assertThrows(NoSuchElementException.class, () -> userService.findByUserId(USER_ID));
    }

    @Test
    void findAllUser() {
        Mockito.when(userRepository.findAllByActiveIsTrue()).thenReturn(List.of(this.buildUser()));
        List<User> users = userService.findAllUser();
        Assertions.assertEquals(1, users.size());
    }

    @Test
    void findPaginatedUserList() {
        Page<User> page = new PageImpl<>(List.of(buildUser()));
        Mockito.when(userRepository.findAllByActiveIsTrue(Mockito.any())).thenReturn(page);

        Page<User> users = userService.findPaginatedUserList(1,1);
        Assertions.assertEquals(users.getTotalElements(), 1);
        Assertions.assertEquals(users.getTotalPages(), 1);

    }

    @Test
    void givenUser_whenEmailNotAlreadyExist_thenCreatUser() throws UserAlreadyExistException {
        Mockito.when(userRepository.existsByEmailAndActiveIsTrue(Mockito.anyString())).thenReturn(false);
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(this.buildUser());
        User createdUser = userService.createUser(User.builder().email("johnylafleur@gmail.com").build());
        Assert.notNull(createdUser, "User is null");
        Assertions.assertEquals(createdUser.getId(), USER_ID);
    }

    @Test
    void givenUser_whenEmailAlreadyExist_thenThrowException(){
        Mockito.when(userRepository.existsByEmailAndActiveIsTrue(Mockito.anyString())).thenReturn(true);
        Assertions.assertThrows(UserAlreadyExistException.class, () -> userService.createUser(User.builder().email("johnylafleur@gmail.com").build()));
    }

    @Test
    void givenIdAndUser_whenExist_thenUpdate() {
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(this.buildUser()));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(this.buildUserUpdate());

        User updateUser = userService.updateUser(USER_ID, this.buildUserUpdate());

        Assert.notNull(updateUser, "User is null");
        Assertions.assertEquals(updateUser.getId(), USER_ID);
        Assertions.assertEquals(updateUser.getFirstname(), "Johnnas");
    }

    @Test
    void userUpdateAddress(){
        Mockito.doNothing().when(userRepository).updateUserAddressById(Mockito.anyString(), Mockito.any(Address.class));
        userService.updateUserAddress(USER_ID, new Address());
        Mockito.verify(userRepository, Mockito.times(1)).updateUserAddressById(USER_ID, new Address());
    }

    @Test
    void deleteUser() {
        Mockito.doNothing().when(userRepository).deleteById(Mockito.anyString());
        userService.deleteUser(USER_ID);
        Mockito.verify(userRepository, Mockito.times(1)).deleteById(USER_ID);
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void givenIdAndStatus_whenStatusIsTrue_thenChangeActiveStatus(boolean b) {
        User user = this.buildUser();
        Mockito.when(userRepository.findById(Mockito.anyString())).thenReturn(Optional.of(user));
        Mockito.when(userRepository.save(Mockito.any(User.class))).thenReturn(user);

        User updatedUser = userService.changeUserAccountActiveStatus(USER_ID, b);
        Mockito.verify(userRepository, Mockito.times(1)).save(user);

        if (b){
            Assertions.assertTrue(updatedUser.isActive());
        } else {
            Assertions.assertFalse(updatedUser.isActive());
        }

    }

    private User buildUser(){
        return User.builder().id(USER_ID)
                        .firstname("Johny")
                        .lastname("LaFleur")
                .email("johnylafleur@gmail.com")
                .phoneNumber("22507544542")
                .gender(Gender.MAN)
                .active(true)
                .build();
    }

    private User buildUserUpdate(){
        return User.builder().id(USER_ID)
                .firstname("Johnnas")
                .lastname("LaRose")
                .email("johnnaslarose@gmail.com")
                .phoneNumber("22507544542")
                .gender(Gender.MAN)
                .active(true)
                .build();
    }
}