package events.paiya.accountmanager.resources;

import lombok.Data;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
@Data
@SuperBuilder
public abstract class BaseResource {
    private LocalDateTime createdDate;
    private LocalDateTime lastModifiedDate;
    private Integer version;

    public BaseResource() {
    }
}
