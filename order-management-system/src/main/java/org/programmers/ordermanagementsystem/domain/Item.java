package org.programmers.ordermanagementsystem.domain;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;

@Getter
@EqualsAndHashCode
@AllArgsConstructor
public class Item {
    private Long id;
    private String name;
    private int price;
    private int stock;
    private ItemType type;
}