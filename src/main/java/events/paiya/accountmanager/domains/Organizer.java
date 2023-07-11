package events.paiya.accountmanager.domains;

import lombok.Getter;
import lombok.Setter;

import java.util.Objects;

@Getter @Setter
public class Organizer{
    private EventOrganizer eventOrganizer;
    private String poste;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Organizer organizer = (Organizer) o;
        return eventOrganizer.equals(organizer.eventOrganizer) && poste.equals(organizer.poste);
    }

    @Override
    public int hashCode() {
        return Objects.hash(eventOrganizer, poste);
    }
}
