package events.paiya.accountmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.paiya.accountmanager.controllers.EventOrganizerController;
import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.domains.OrganizationMember;
import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.exceptions.UserAlreadyExistException;
import events.paiya.accountmanager.mappers.EventOrganizerMapper;
import events.paiya.accountmanager.resources.EventOrganizerResource;
import events.paiya.accountmanager.services.EventOrganizerServiceImpl;
import events.paiya.accountmanager.services.UserServiceImpl;
import org.junit.jupiter.api.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
@ActiveProfiles("integration-test")
public class EventOrganizerControllerIntegrationTest {
    private static final String BASE_URI_TEMPLATE = "/v1/event-organizers";
    private static final String GAOU_ID = "78acee0e2162f374bd192508";
    private static final String MPROD_ID = "847650e2162f374bd192508";
    private static ObjectMapper objectMapper;

    @Autowired
    private EventOrganizerServiceImpl eventOrganizerService;
    @Autowired
    private UserServiceImpl userService;
    @Autowired
    private EventOrganizerMapper eventOrganizerMapper;

    MockMvc mockMvc;

    @BeforeAll
    public static void init(){
        objectMapper = new ObjectMapper();
    }

    @BeforeEach
    void setup(){
        this.mockMvc = MockMvcBuilders.standaloneSetup(new EventOrganizerController(eventOrganizerService, eventOrganizerMapper))
                .build();
    }

    @Test
    @Order(0)
    void create() throws Exception {
        eventOrganizerService.deleteAll();
        EventOrganizerResource resource = this.buildGaouResource();
        mockMvc.perform(post(BASE_URI_TEMPLATE)
                        .content(objectMapper.writeValueAsBytes(resource))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated());
    }

    @Test
    @Order(1)
    void findById() throws Exception {
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/"+ GAOU_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(GAOU_ID))
                .andExpect(jsonPath("$.email").value("event@gaouproduction.com"));
    }

    @Test
    @Order(2)
    void findAll() throws Exception {
        eventOrganizerService.create(this.buildMprodEntity());
        mockMvc.perform(get(BASE_URI_TEMPLATE)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(2));
    }

    @Test
    @Order(3)
    void findByOwner() throws Exception {
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/criteria")
                        .param("parameter", "owner")
                        .param("value", "asalfo@gaouproduction.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].createdBy").value("asalfo@gaouproduction.com"));
    }

    @Test
    @Order(4)
    void findByOrganizationMembersEmail() throws Exception {
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/criteria")
                        .param("parameter", "email")
                        .param("value", "douksaga@mprod.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].organizationMembers[1].email").value("douksaga@mprod.com"));
    }

    @Test
    @Order(5)
    void addMemberByEoId() throws Exception {
        List<String> members = this.buildMagicSystemOtherMemebers();
        mockMvc.perform(patch(BASE_URI_TEMPLATE+"/add-members")
                        .param("eventOrganizerId", GAOU_ID)
                        .content(objectMapper.writeValueAsBytes(members))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(GAOU_ID))
                .andExpect(jsonPath("$.organizationMembers.length()").value(4));
    }

    @Test
    @Order(6)
    void removeMemberByEoId() throws Exception {
        List<String> members = List.of("tino@gaouproduction.com");
        mockMvc.perform(patch(BASE_URI_TEMPLATE+"/remove-members")
                        .param("eventOrganizerId", GAOU_ID)
                        .content(objectMapper.writeValueAsBytes(members))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(GAOU_ID))
                .andExpect(jsonPath("$.organizationMembers.length()").value(3));

        EventOrganizer eventOrganizer = eventOrganizerService.findById(GAOU_ID);
        Assertions.assertFalse(() -> eventOrganizer.getOrganizationMembers()
                                    .stream().anyMatch(member -> member.getEmail().equals("tino@gaouproduction.com")));
    }

    @Test
    @Order(7)
    void update() throws Exception {
        EventOrganizerResource gaouResource = buildGaouResource();
        gaouResource.setName("GAOU's PRODUCTION");
        mockMvc.perform(patch(BASE_URI_TEMPLATE+"/"+GAOU_ID)
                        .content(objectMapper.writeValueAsBytes(gaouResource))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.id").value(GAOU_ID))
                .andExpect(jsonPath("$.name").value("GAOU's PRODUCTION"));
    }

    @Test
    @Order(7)
    void deleteEventOrganizer() throws Exception {
        mockMvc.perform(delete(BASE_URI_TEMPLATE+"/"+MPROD_ID)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent());
    }


    private EventOrganizerResource buildGaouResource(){
        OrganizationMember member = OrganizationMember.builder()
                .email("asalfo@gaouproduction.com")
                .isOrganizationOwner(true)
                .lastname("Salif").lastname("Fofana").isAdmin(true).build();

        return EventOrganizerResource.builder().id(GAOU_ID)
                .email("event@gaouproduction.com")
                .name("GAOU PRODUCTION")
                .phoneNumbers(List.of("+2250707080910"))
                .organizationMembers(List.of(member))
                .createdBy("asalfo@gaouproduction.com")
                .build();
    }

    private EventOrganizer buildMprodEntity(){
        OrganizationMember molar = OrganizationMember.builder()
                .email("molar@mprod.com")
                .isOrganizationOwner(true)
                .lastname("Mohamed").firstname("x").isAdmin(true).build();

        OrganizationMember douksaga = OrganizationMember.builder()
                .email("douksaga@mprod.com")
                .isOrganizationOwner(false)
                .lastname("Stéphane").firstname("Doukouré").isAdmin(true).build();

        return EventOrganizer.builder()
                .id(MPROD_ID)
                .email("event@mprod.com")
                .name("Mprod")
                .organizationMembers(List.of(molar, douksaga))
                .createdBy("molar@mprod.com")
                .build();
    }

    private List<String> buildMagicSystemOtherMemebers() throws UserAlreadyExistException {
        User manadja = User.builder()
                .email("manadja@gaouproduction.com")
                .lastname("Manadja").firstname("Manadja")
                .build();
        userService.createUser(manadja);

        User tino = User.builder()
                .email("tino@gaouproduction.com")
                .lastname("Tino").firstname("Tino")
                .build();
        userService.createUser(tino);

        User goude = User.builder()
                .email("goude@gaouproduction.com")
                .lastname("Goude").firstname("Goude")
                .build();
        userService.createUser(goude);


        return List.of(manadja.getEmail(), tino.getEmail(), goude.getEmail());
    }

}
