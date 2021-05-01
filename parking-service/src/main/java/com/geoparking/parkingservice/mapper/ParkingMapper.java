package com.geoparking.parkingservice.mapper;

import com.geoparking.parkingservice.dto.ParkingCoordinate;
import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.model.Parking;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", imports = ParkingCoordinate.class)
public interface ParkingMapper {

    ParkingMapper INSTANCE = Mappers.getMapper(ParkingMapper.class);

    @Mapping(target = "position", expression = "java(new ParkingCoordinate(parking.getLocation().getX(), parking.getLocation().getY()))")
    ParkingDTO toDTO(Parking parking);

    Parking toEntity(ParkingDTO parkingDTO);

}
