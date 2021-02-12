package xyz.willz.geoparking.dto;

import lombok.Data;

@Data
public class CustomerDTO {
    private Long id;

    private String firstName;
    private String middleName;
    private String lastName;

    private String mobile;

    private String email;

    private String password;

    private Boolean isActive;
}
