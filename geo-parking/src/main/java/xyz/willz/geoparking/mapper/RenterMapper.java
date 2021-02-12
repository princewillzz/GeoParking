package xyz.willz.geoparking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import xyz.willz.geoparking.dto.RenterDTO;
import xyz.willz.geoparking.model.Renter;

@Mapper(componentModel = "spring")
public interface RenterMapper {
    
    RenterMapper INSTANCE = Mappers.getMapper( RenterMapper.class);

    RenterDTO toRenterDTO(Renter renter);

    Renter toRenterEntity(RenterDTO renterDTO);

}
