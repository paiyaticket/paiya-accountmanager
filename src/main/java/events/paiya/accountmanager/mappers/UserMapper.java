package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.resources.UserResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResource userToUserResource(User user);

    User userResourceToUser(UserResource userResource);
}
