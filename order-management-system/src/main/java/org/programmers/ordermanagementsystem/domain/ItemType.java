package org.programmers.ordermanagementsystem.domain;

import java.util.List;

public enum ItemType {
    COFFEE, ELECTRONICS, CIGARETTE;

    private static List<ItemType> prohibitedForMinor = List.of(CIGARETTE);

    public boolean isProhibitedForMinor() {
        return prohibitedForMinor.contains(this);
    }
}
