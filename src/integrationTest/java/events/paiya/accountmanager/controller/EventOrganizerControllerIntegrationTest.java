package events.paiya.accountmanager.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import events.paiya.accountmanager.controllers.EventOrganizerController;
import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.mappers.EventOrganizerMapper;
import events.paiya.accountmanager.resources.EventOrganizerResource;
import events.paiya.accountmanager.services.EventOrganizerServiceImpl;
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
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@ActiveProfiles("integration-test")
public class EventOrganizerControllerIntegrationTest {
    private static final String BASE_URI_TEMPLATE = "/v1/event-organizers";
    private static final String GAOU_ID = "78acee0e2162f374bd192508";
    private static final String GAOU_EMAIL = "gaou@gmail.com";
    private static final String GAOU_OWNER = "gaou_owner@gmail.com";
    private static final String MPROD_ID = "847650e2162f374bd192508";
    private static final String MPROD_EMAIL = "mprod@gmail.com";
    private static final String MPROD_OWNER = "mprod_owner@gmail.com";
    private static ObjectMapper objectMapper;

    @Autowired
    private EventOrganizerServiceImpl eventOrganizerService;
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
                .andExpect(jsonPath("$.email").value(GAOU_EMAIL));
    }

    @Test
    @Order(2)
    void findAll() throws Exception {
        eventOrganizerService.create(this.buildMprodEntity());
        mockMvc.perform(get(BASE_URI_TEMPLATE)
                        .param("owner", MPROD_OWNER)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1));
    }

    @Test
    @Order(3)
    void findByStaffEmail() throws Exception {
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/staff")
                        .param("staff-member-mail", "staff1@gmail.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].createdBy").value(MPROD_OWNER));
    }
    
    /*
    @Test
    @Order(4)
    void findByOrganizationMembersEmail() throws Exception {
        eventOrganizerService.deleteAll();
        eventOrganizerService.create(this.buildMprodEntity());
        mockMvc.perform(get(BASE_URI_TEMPLATE+"/criteria")
                        .param("parameter", "email")
                        .param("value", "event@mprod.com")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(1))
                .andExpect(jsonPath("$[0].staffMembers[0]").value("staff1@gmail.com"));
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
                .andExpect(jsonPath("$.staffMembers.length()").value(4));
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
                .andExpect(jsonPath("$.staffMembers.length()").value(3));

        EventOrganizer eventOrganizer = eventOrganizerService.findById(GAOU_ID);
        Assertions.assertFalse(() -> eventOrganizer.getOrganizationMembers()
                                    .stream().anyMatch(member -> member.getEmail().equals("tino@gaouproduction.com")));
    }

    private List<String> buildMagicSystemOtherMemebers() throws UserAlreadyExistException {
        return List.of("manadja@gaouproduction.com", "tino@gaouproduction.com", "goude@gaouproduction.com");
    }
     */
    

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

        return EventOrganizerResource.builder()
                .id(GAOU_ID)
                .email(GAOU_EMAIL)
                .name("GAOU PRODUCTION")
                .phoneNumbers(List.of("+2250707080910"))
                .staffMembers(List.of("gaouproduction@gmail.com"))
                .createdBy(GAOU_OWNER)
                .build();
    }

    private EventOrganizer buildMprodEntity(){
        return EventOrganizer.builder()
                .id(MPROD_ID)
                .email(MPROD_EMAIL)
                .name("Mprod")
                .staffMembers(List.of("staff1@gmail.com", "staff2@gmail.com"))
                .createdBy(MPROD_OWNER)
                .build();
    }

    

}
