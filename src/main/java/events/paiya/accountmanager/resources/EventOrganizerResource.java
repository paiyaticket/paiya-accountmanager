package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.OrganizationMember;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;
import java.util.Map;

@EqualsAndHashCode(callSuper = true)
@Data
@SuperBuilder
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class EventOrganizerResource extends BaseResource{
    private String id;
    private String name;
    private String email;
    private List<String> phoneNumbers;
    private Map<String, String> socialLinks;
    private List<OrganizationMember> organizationMembers;
    private String createdBy;
}
