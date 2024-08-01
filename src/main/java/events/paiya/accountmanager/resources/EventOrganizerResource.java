package events.paiya.accountmanager.resources;

import events.paiya.accountmanager.domains.SocialMedia;
import lombok.*;
import lombok.experimental.SuperBuilder;

import java.util.List;

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
    private String details;
    private List<String> phoneNumbers;
    private List<SocialMedia> socialMedia;
    private List<String> staffMembers;
    private String createdBy;
}
