package events.paiya.accountmanager.domains;

import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.annotation.Version;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Document
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class EventOrganizer {
    @Id
    private String id;
    private String name;
    private String email;
    private List<String> phoneNumbers;
    @Builder.Default
    private Map<String, String> socialLinks = new HashMap<>();
    @Builder.Default
    private List<OrganizationMember> organizationMembers = new ArrayList<>();


    // Auditing
    private String createdBy;
    @CreatedDate
    private LocalDateTime createdDate;
    @LastModifiedDate
    private LocalDateTime lastModifiedDate;
    @Version
    private Integer version;

    public void addOrganizationMember(List<OrganizationMember> membersToAddList){
        membersToAddList.forEach(member -> {
            if(!this.organizationMembers.contains(member)){
                this.organizationMembers.add(member);
            }}
        );
    }

    public void removeOrganizationMember(List<OrganizationMember> membersToRemoveList){
        membersToRemoveList.forEach(member -> this.organizationMembers.remove(member));
    }
}
