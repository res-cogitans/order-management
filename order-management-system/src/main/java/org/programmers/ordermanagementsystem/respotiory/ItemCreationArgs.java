package org.programmers.ordermanagementsystem.respotiory;

import org.programmers.ordermanagementsystem.domain.ItemType;

public record ItemCreationArgs(String name, int price, int stock, ItemType type) {
}
