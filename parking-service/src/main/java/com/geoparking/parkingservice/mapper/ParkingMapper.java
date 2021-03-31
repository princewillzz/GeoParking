package com.geoparking.parkingservice.mapper;

import com.geoparking.parkingservice.dto.ParkingDTO;
import com.geoparking.parkingservice.model.Parking;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface ParkingMapper {

    ParkingMapper INSTANCE = Mappers.getMapper(ParkingMapper.class);

    ParkingDTO toDTO(Parking parking);

    Parking toEntity(ParkingDTO parkingDTO);

}
