package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.resources.EventOrganizerResource;
import org.mapstruct.*;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventOrganizerMapper {
    EventOrganizer toEntity(EventOrganizerResource eventOrganizerResource);
    EventOrganizerResource toResource(EventOrganizer eventOrganizer);
    List<EventOrganizerResource> toResourceList(List<EventOrganizer> eventOrganizerList);

    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateFromResource(EventOrganizerResource resource, @MappingTarget EventOrganizer eventOrganizer);


}
