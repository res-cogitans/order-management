package org.programmers.ordermanagementsystem.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public enum OrderStatus {
    ACCEPTED("주문 접수"), COMPLETE("완료"), CANCELED("취소");

    @Getter
    private final String name;
}
