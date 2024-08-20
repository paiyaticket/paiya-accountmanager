package events.paiya.accountmanager.services;

import events.paiya.accountmanager.configs.DisableSecurityConfiguration;
import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.domains.SocialMedia;
import events.paiya.accountmanager.mappers.projection.OrganizationMemberProjectionMapper;
import events.paiya.accountmanager.repositories.EventOrganizerRepository;
import events.paiya.accountmanager.repositories.UserRepository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.util.Assert;

import java.util.*;
@ExtendWith(MockitoExtension.class)
@ContextConfiguration(classes = DisableSecurityConfiguration.class)
class EventOrganizerServiceImplTest {

    private final String EVENT_ORGANIZER_ID = UUID.randomUUID().toString();
    private final String EVENT_ORGANIZER_EMAIL = "jp@gmail.com";
    // private final String USER_ID = UUID.randomUUID().toString();
    // private final String USER_EMAIL = "johnylafleur@gmail.com";
    @Mock
    private EventOrganizerRepository eventOrganizerRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private OrganizationMemberProjectionMapper organizationMemberProjectionMapper;
    @InjectMocks
    private EventOrganizerServiceImpl eventOrganizerService;

    @Test
    void givenEventOrganizer_whenEventOrganizer_thenCreate(){
        Mockito.when(eventOrganizerRepository.save(Mockito.any(EventOrganizer.class))).thenReturn(this.buildEventOrganizer());
        EventOrganizer eventOrganizer = eventOrganizerService.create(EventOrganizer.builder().build());
        Assertions.assertNotNull(eventOrganizer);
        Mockito.verify(eventOrganizerRepository).save(EventOrganizer.builder().build());
    }

    @Test
    void givenId_whenId_thenDeleteById(){
        Mockito.doNothing().when(eventOrganizerRepository).deleteById(Mockito.anyString());
        eventOrganizerService.delete(EVENT_ORGANIZER_ID);
        Mockito.verify(eventOrganizerRepository).deleteById(EVENT_ORGANIZER_ID);
    }

    @Test
    void givenId_whenExist_thenFindById(){
        Mockito.when(eventOrganizerRepository.findById(EVENT_ORGANIZER_ID)).thenReturn(Optional.ofNullable(this.buildEventOrganizer()));
        EventOrganizer eventOrganizer = eventOrganizerService.findById(EVENT_ORGANIZER_ID);
        Assertions.assertNotNull(eventOrganizer);
        Mockito.verify(eventOrganizerRepository).findById(EVENT_ORGANIZER_ID);
    }

    @Test
    void givenId_whenNotExist_thenFindById(){
        Mockito.when(eventOrganizerRepository.findById(Mockito.anyString())).thenThrow(NoSuchElementException.class);
        Assertions.assertThrowsExactly(NoSuchElementException.class, () -> eventOrganizerService.findById(EVENT_ORGANIZER_ID));
        Mockito.verify(eventOrganizerRepository).findById(EVENT_ORGANIZER_ID);
    }

    @Test
    void givenEmail_whenMembersExist_thenFindByOrganizationMembersEmail(){
        Mockito.when(eventOrganizerRepository.findByStaffMembers(Mockito.anyString()))
                .thenReturn(List.of(this.buildEventOrganizer()));
        List<EventOrganizer> eventOrganizerList = eventOrganizerService.findByOrganizationMembersEmail(EVENT_ORGANIZER_EMAIL);
        Assert.notEmpty(eventOrganizerList, "Event list must not be empty");
        Mockito.verify(eventOrganizerRepository).findByStaffMembers(EVENT_ORGANIZER_EMAIL);
    }

    @Test
    void givenEmail_whenEmailExist_thenFindByCreatedBy(){
        Mockito.when(eventOrganizerRepository.findByCreatedBy(Mockito.anyString()))
                .thenReturn(List.of(this.buildEventOrganizer()));
        List<EventOrganizer> eventOrganizerList = eventOrganizerService.findByCreatedBy(EVENT_ORGANIZER_EMAIL);
        Assert.notEmpty(eventOrganizerList, "Event list must not be empty");
        Mockito.verify(eventOrganizerRepository).findByCreatedBy(EVENT_ORGANIZER_EMAIL);
    }

    /*
    @Test
    void givenIdAndUserMailList_whenOrganizerMemberFound_ThenAddMemberToEventOrganizer(){
        List<String> userEmailList = List.of(USER_EMAIL);
        String[] userEmailArray = userEmailList.toArray(new String[0]);
        Mockito.when(userRepository.findByEmailIn(userEmailArray)).thenReturn(this.buildOrganizationMemberProjection());
        Mockito.when(organizationMemberProjectionMapper.toEntityList(this.buildOrganizationMemberProjection()))
               .thenReturn(this.buildOrganizationMemberList());
        Mockito.when(eventOrganizerRepository.findById(EVENT_ORGANIZER_ID)).thenReturn(Optional.ofNullable(this.buildEventOrganizer()));
        Mockito.when(eventOrganizerRepository.save(Mockito.any(EventOrganizer.class))).thenReturn(this.buildEventOrganizer());

        EventOrganizer eventOrganizer = eventOrganizerService.addMemberToEventOrganizer(EVENT_ORGANIZER_ID, List.of(USER_EMAIL));

        Assertions.assertNotNull(eventOrganizer);
        Mockito.verify(userRepository).findByEmailIn(userEmailArray);
        Mockito.verify(organizationMemberProjectionMapper).toEntityList(this.buildOrganizationMemberProjection());
        Mockito.verify(eventOrganizerRepository).findById(Mockito.anyString());
        Mockito.verify(eventOrganizerRepository).save(Mockito.any(EventOrganizer.class));
    }

    @Test
    void givenIdAndUserMailList_whenOrganizerMemberFound_ThenRemoveMemberToEventOrganizer(){
        List<String> userEmailList = List.of(USER_EMAIL);
        String[] userEmailArray = userEmailList.toArray(new String[0]);
        Mockito.when(userRepository.findByEmailIn(userEmailArray)).thenReturn(this.buildOrganizationMemberProjection());
        Mockito.when(organizationMemberProjectionMapper.toEntityList(this.buildOrganizationMemberProjection()))
                .thenReturn(this.buildOrganizationMemberList());
        Mockito.when(eventOrganizerRepository.findById(EVENT_ORGANIZER_ID)).thenReturn(Optional.ofNullable(this.buildEventOrganizer()));
        Mockito.when(eventOrganizerRepository.save(Mockito.any(EventOrganizer.class))).thenReturn(this.buildEventOrganizer());

        EventOrganizer eventOrganizer = eventOrganizerService.removeMemberFromEventOrganizer(EVENT_ORGANIZER_ID, List.of(USER_EMAIL));

        Assertions.assertNotNull(eventOrganizer);
        Mockito.verify(userRepository).findByEmailIn(userEmailArray);
        Mockito.verify(organizationMemberProjectionMapper).toEntityList(this.buildOrganizationMemberProjection());
        Mockito.verify(eventOrganizerRepository).findById(Mockito.anyString());
        Mockito.verify(eventOrganizerRepository).save(Mockito.any(EventOrganizer.class));
    }

    private List<OrganizationMemberProjection> buildOrganizationMemberProjection(){
        OrganizationMemberProjection projection = new OrganizationMemberProjection(USER_ID,
                                                "Lafleur", "johny", USER_EMAIL);
        return List.of(projection);
    }

    private List<OrganizationMember> buildOrganizationMemberList(){
        OrganizationMember member = OrganizationMember.builder().id(UUID.randomUUID().toString())
                .lastname("Lafleur").firstname("johny").email(USER_EMAIL).build();
        return List.of(member);
    }
    */

    private EventOrganizer buildEventOrganizer(){
        return EventOrganizer.builder()
                .id(EVENT_ORGANIZER_ID)
                .email("jp@gmail.com")
                .name("Johny's Production")
                .phoneNumbers(List.of("+2250707078548"))
                .socialMedia(List.of(SocialMedia.builder().name("Instagram").icon("pi-instagram").link("http://instagram.com").build()))
                .build();
    }

    
}