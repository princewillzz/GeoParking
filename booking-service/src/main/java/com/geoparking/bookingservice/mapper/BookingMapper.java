package com.geoparking.bookingservice.mapper;

import com.geoparking.bookingservice.dto.BookingDTO;
import com.geoparking.bookingservice.model.Booking;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    Booking toBookingEntity(BookingDTO bookingDTO);

    BookingDTO toBookingDTO(Booking booking);

}
