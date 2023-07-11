package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.resources.UserResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResource userToUserResource(User user);


    User userResourceToUser(UserResource userResource);
}
