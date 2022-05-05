package org.programmers.ordermanagementsystem.repository;

import org.programmers.ordermanagementsystem.domain.Item;
import org.programmers.ordermanagementsystem.dto.ItemCreateForm;

import java.util.List;
import java.util.Optional;

public interface ItemRepository {
    Item save(ItemCreateForm args);

    Item update(Item item);

    Optional<Item> findById(Long id);

    List<Item> findAll();

    void delete(Long id);
}
