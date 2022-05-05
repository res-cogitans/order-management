package org.programmers.ordermanagementsystem.service;

import lombok.RequiredArgsConstructor;
import org.programmers.ordermanagementsystem.domain.Item;
import org.programmers.ordermanagementsystem.domain.ItemType;
import org.programmers.ordermanagementsystem.dto.ItemCreateForm;
import org.programmers.ordermanagementsystem.repository.ItemRepository;
import org.programmers.ordermanagementsystem.dto.ItemDto;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ItemServiceImpl implements ItemService {

    private final ItemRepository itemRepository;

    @Override
    public List<Item> getAllItems() {
        return itemRepository.findAll();
    }

    @Override
    public Item registerItem(ItemCreateForm form) {
        System.out.println("form.getType() = " + form.getType());
        return itemRepository.save(form);
    }

    //TODO: Null 발생시
    @Override
    public Item getItemById(Long id) {
        return itemRepository.findById(id).get();
    }

    @Override
    public Item updateItem(ItemDto form) {
        return itemRepository.update(new Item(form.id(), form.name(), form.price(), form.stock(), ItemType.valueOf(form.type())));
    }

    @Override
    public void deleteItem(Long id) {
        itemRepository.delete(id);
    }
}
