package events.paiya.accountmanager.resources;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StatusChangeResource {
    private String email;
    private Boolean status;
}
