package events.paiya.accountmanager.domains;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.List;
import java.util.Map;

@Document
@Getter @Setter
public class EventOrganizer {
    @Id
    private String id;
    private String name;
    private List<String> phoneNumber;
    private Map<String, String> socialLinks;
}
