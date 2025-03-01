package events.paiya.accountmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.paiya.accountmanager.controllers.UserController;
import events.paiya.accountmanager.domains.Country;
import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.enumerations.Gender;
import events.paiya.accountmanager.mappers.AddressMapper;
import events.paiya.accountmanager.mappers.UserMapper;
import events.paiya.accountmanager.resources.AddressResource;
import events.paiya.accountmanager.resources.UserResource;
import events.paiya.accountmanager.services.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
public class UserControllerIntegrationTest {
    private final String JOHNY_ID = "64acee0e2162f374bd198208";
    private final String JOHNY_EMAIL = "johnylafleur@gmail.com";
    private final String DJEDJE_ID = "68acee0e2162f374bd198208";
    private final String DJEDJE_EMAIL = "ernestodjedje@gmail.com";
    private static final String BASE_URI_TEMPLATE = "/v1/users";
    private static ObjectMapper objectMapper;

    @Autowired
    UserServiceImpl userService;
    @Autowired
    UserMapper userMapper;
    @Autowired
    AddressMapper addressMapper;
    MockMvc mockMvc;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new UserController(userService, userMapper))
                .build();
    }

    @Test
    @Order(0)
    void createUser() throws Exception {
        userService.deleteAll();
        User user = this.buildUserJohny();
        UserResource userResource = userMapper.userToUserResource(user);
        mockMvc.perform(post(BASE_URI_TEMPLATE)
                        .content(objectMapper.writeValueAsBytes(userResource))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    void findAll() throws Exception {
        userService.createUser(this.buildUserErnesto());
        mockMvc.perform(get(BASE_URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }
    @Test
    @Order(3)
    void findUserByEmail() throws Exception {
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/"+JOHNY_EMAIL)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(JOHNY_ID))
                .andExpect(jsonPath("$.email").value(JOHNY_EMAIL));
    }

    @Test
    @Order(4)
    void findUserPaginatedList() throws Exception {
        String page = "1";
        String size = "1";
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/paginated")
                        .param("page", page)
                        .param("size", size)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(header().stringValues("Total-Elements", "2"))
                .andExpect(header().stringValues("Total-Pages", "2"));
    }

    @Test
    @Order(5)
    void updateUser() throws Exception {
        UserResource userResource = this.buildUserErnestoUpdateResource();
        mockMvc.perform(patch(BASE_URI_TEMPLATE+"/"+DJEDJE_EMAIL)
                        .content(objectMapper.writeValueAsBytes(userResource))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.address").isNotEmpty())
                .andExpect(jsonPath("$.email").value("e.djedje@gmail.com"));
    }

    @Test
    @Order(5)
    void deleteUser() throws Exception {
        mockMvc.perform(delete(BASE_URI_TEMPLATE+"/"+DJEDJE_ID))
                .andExpect(status().isNoContent());
    }


    private User buildUserJohny(){
        return User.builder().id(JOHNY_ID)
                .firstname("Johny")
                .lastname("LaFleur")
                .email("johnylafleur@gmail.com")
                .phoneNumber("2250707544542")
                .gender(Gender.MAN)
                .active(true)
                .build();
    }

    private User buildUserErnesto(){
        return User.builder().id(DJEDJE_ID)
                .firstname("Ernesto")
                .lastname("Djédjé")
                .email("ernestodjedje@gmail.com")
                .phoneNumber("2250708544542")
                .gender(Gender.MAN)
                .active(true)
                .build();
    }

    private UserResource buildUserErnestoUpdateResource(){
        AddressResource addressResource = AddressResource.builder()
                                                        .country(Country.builder().code("CIV").name("Côte d'Ivoire").build())
                                                        .city("Abidjan").build();
        return UserResource.builder().address(addressResource)
                .firstname("Ernesto")
                .lastname("Djédjé")
                .phoneNumber("2250505544542")
                .email("e.djedje@gmail.com").build();
    }
}
