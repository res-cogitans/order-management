package org.programmers.ordermanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@NoArgsConstructor
@Data
@AllArgsConstructor
public class MemberCreateForm {

    private String name;
    private LocalDate birthDate;
    private String email;
    private String postcode;
    private String roadAddress;
    private String lotNumberAddress;
    private String detailAddress;
    private String extraAddress;
}
