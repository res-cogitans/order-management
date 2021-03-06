package org.programmers.ordermanagementsystem.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.programmers.ordermanagementsystem.domain.ItemType;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ItemCreateForm {
    String name;
    int price;
    int stock;
    ItemType type;
}
