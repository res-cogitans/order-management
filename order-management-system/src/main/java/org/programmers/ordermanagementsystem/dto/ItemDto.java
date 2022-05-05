package org.programmers.ordermanagementsystem.dto;

import org.programmers.ordermanagementsystem.domain.Item;

public record ItemDto (Long id, String name, int price, int stock, String type){

    public static ItemDto from(Item item) {
        return new ItemDto(item.getId(), item.getName(), item.getPrice(), item.getStock(), item.getType().toString());
    }
}
