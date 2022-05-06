package org.programmers.ordermanagementsystem.domain;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@AllArgsConstructor
@EqualsAndHashCode
public class Address {
    private String postCode;
    private String roadAddress;
    private String lotNumberAddress;
    private String detailAddress;
    private String extraAddress;
}
