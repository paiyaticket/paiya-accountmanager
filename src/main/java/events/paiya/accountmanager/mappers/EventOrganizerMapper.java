package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.EventOrganizer;
import events.paiya.accountmanager.resources.EventOrganizerResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface EventOrganizerMapper {
    @Mapping(target = "version", ignore = true)
    @Mapping(target = "lastModifiedDate", ignore = true)
    @Mapping(target = "createdDate", ignore = true)
    EventOrganizer toEntity(EventOrganizerResource eventOrganizerResource);
    EventOrganizerResource toResource(EventOrganizer eventOrganizer);

    List<EventOrganizer> toEntityList(List<EventOrganizerResource> eventOrganizerResourceList);
    List<EventOrganizerResource> toResourceList(List<EventOrganizer> eventOrganizerList);
}
