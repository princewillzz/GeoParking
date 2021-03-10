package xyz.willz.geoparking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import xyz.willz.geoparking.dto.BookingDTO;
import xyz.willz.geoparking.model.Booking;

@Mapper(componentModel = "spring")
public interface BookingMapper {

    BookingMapper INSTANCE = Mappers.getMapper(BookingMapper.class);

    Booking toBookingEntity(BookingDTO bookingDTO);

    BookingDTO toBookingDTO(Booking booking);

}
