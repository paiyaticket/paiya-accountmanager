package events.paiya.accountmanager.mappers.projection;

import events.paiya.accountmanager.domains.OrganizationMember;
import events.paiya.accountmanager.domains.projections.OrganizationMemberProjection;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface OrganizationMemberProjectionMapper {
    @Mapping(target = "isOrganizationOwner", ignore = true)
    @Mapping(target = "isAdmin", ignore = true)
    OrganizationMember toEntity(OrganizationMemberProjection organizationMemberProjection);
    OrganizationMemberProjection toProjection(OrganizationMember organizationMember);

    List<OrganizationMember> toEntityList(List<OrganizationMemberProjection> organizationMemberProjectionList);
}
