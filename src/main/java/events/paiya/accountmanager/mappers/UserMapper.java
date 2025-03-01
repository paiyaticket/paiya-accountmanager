package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.User;
import events.paiya.accountmanager.resources.UserResource;
import org.mapstruct.*;

@Mapper(componentModel = "spring")
public interface UserMapper {
    UserResource userToUserResource(User user);
    @Mapping(target = "financialAccounts", ignore = true)
    User userResourceToUser(UserResource userResource);

    @Mapping(target = "financialAccounts", ignore = true)
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateUserFromResource(UserResource resource, @MappingTarget User user);
}
