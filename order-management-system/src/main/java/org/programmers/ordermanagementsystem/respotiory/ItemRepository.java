package org.programmers.ordermanagementsystem.respotiory;

import org.programmers.ordermanagementsystem.domain.Item;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(ItemCreationArgs args);

    Item update(Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll();

    void delete(Long id);
}
