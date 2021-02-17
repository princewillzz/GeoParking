package xyz.willz.geoparking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import xyz.willz.geoparking.dto.ParkingDTO;
import xyz.willz.geoparking.model.Parking;

@Mapper(componentModel = "spring")
public interface ParkingMapper {

    ParkingMapper INSTANCE = Mappers.getMapper(ParkingMapper.class);

    ParkingDTO toParkingDTO(Parking parking);

    Parking toParkingEntity(ParkingDTO parkingDTO);

}
