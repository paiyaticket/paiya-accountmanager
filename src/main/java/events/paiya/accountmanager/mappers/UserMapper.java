package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.resources.UserResource;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "gender", source = "gender")
    UserResource userToUserResource(User user);

    @Mapping(target = "firstname", source = "firstname")
    @Mapping(target = "lastname", source = "lastname")
    @Mapping(target = "email", source = "email")
    @Mapping(target = "phoneNumber", source = "phoneNumber")
    @Mapping(target = "gender", source = "gender")
    User userResourceToUser(UserResource userResource);
}
