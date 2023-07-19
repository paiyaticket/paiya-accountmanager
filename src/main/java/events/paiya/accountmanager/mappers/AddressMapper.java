package events.paiya.accountmanager.mappers;

import events.paiya.accountmanager.domains.Address;
import events.paiya.accountmanager.resources.AddressResource;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    AddressResource addressToAddressResource(Address address);

    Address addressResourceToAddress(AddressResource addressResource);
}
