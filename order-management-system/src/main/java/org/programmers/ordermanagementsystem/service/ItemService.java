package org.programmers.ordermanagementsystem.service;

import org.programmers.ordermanagementsystem.domain.Item;
import org.programmers.ordermanagementsystem.dto.ItemCreateForm;
import org.programmers.ordermanagementsystem.dto.ItemDto;

import java.util.List;

public interface ItemService {
    List<Item> getAllItems();

    Item registerItem(ItemCreateForm form);

    Item getItemById(Long id);

    Item updateItem(ItemDto form);

    void deleteItem(Long id);
}
