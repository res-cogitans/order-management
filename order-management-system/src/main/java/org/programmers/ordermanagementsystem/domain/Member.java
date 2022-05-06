package org.programmers.ordermanagementsystem.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
public class Member {

    private Long id;
    private String name;
    private Age age;
    //TODO Email 검증
    private Email email;
    private Address address;
}
