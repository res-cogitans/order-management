package org.programmers.ordermanagementsystem.domain;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.List;

@RequiredArgsConstructor
public enum ItemType {
    COFFEE("커피"), ELECTRONICS("전자제품"), CIGARETTE("담배");

    @Getter
    private final String name;

    private static List<ItemType> prohibitedForMinor = List.of(CIGARETTE);

    public boolean isProhibitedForMinor() {
        return prohibitedForMinor.contains(this);
    }
}
