package com.mitocode.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.Column;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class PatientDTO {
    private Integer idPatient;

    @NotEmpty
    @Size(min = 3, message = "{firstname.size}")
    private String firstName;

    @NotNull
    @Size(min = 3, message = "{lastname.size}")
    private String lastName;

    @Size(min = 8)
    private String dni;

    @Size(min = 3, max = 150)
    private String address;

    @Size(min = 9, max = 9)
    private String phone;

    @Email
    private String email;
}
