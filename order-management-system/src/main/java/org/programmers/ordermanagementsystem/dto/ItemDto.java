package org.programmers.ordermanagementsystem.dto;

import org.programmers.ordermanagementsystem.domain.Item;
import org.programmers.ordermanagementsystem.domain.ItemType;

public record ItemDto (Long id, String name, int price, int stock, ItemType type){

    public static ItemDto from(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock(), item.getType());
    }
}
