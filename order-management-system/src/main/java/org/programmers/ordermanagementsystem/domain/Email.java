package org.programmers.ordermanagementsystem.domain;

import lombok.EqualsAndHashCode;
import lombok.Getter;

@EqualsAndHashCode
public class Email {

    public Email(String address) {
        this.address = address;
    }

    @Getter
    private String address;
}
