package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.OrganizationMember;
import lombok.*;

import java.util.List;
import java.util.Map;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventOrganizerResource {
    private String id;
    private String name;
    private String email;
    private List<String> phoneNumber;
    private Map<String, String> socialLinks;
    private List<OrganizationMember> organizationMembers;
}
