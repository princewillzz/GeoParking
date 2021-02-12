package xyz.willz.geoparking.mapper;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import xyz.willz.geoparking.dto.CustomerDTO;
import xyz.willz.geoparking.model.Customer;

@Mapper(componentModel = "spring")
public interface CustomerMapper {
    
    CustomerMapper INSTANCE = Mappers.getMapper(CustomerMapper.class);

    
    CustomerDTO toCustomerDTO(Customer customer);

    Customer toCustomerEntity(CustomerDTO customerDTO);

}
