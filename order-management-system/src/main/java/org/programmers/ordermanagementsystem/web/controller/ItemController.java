package org.programmers.ordermanagementsystem.web.controller;

import lombok.RequiredArgsConstructor;
import org.programmers.ordermanagementsystem.domain.ItemType;
import org.programmers.ordermanagementsystem.dto.ItemCreateForm;
import org.programmers.ordermanagementsystem.dto.ItemDto;
import org.programmers.ordermanagementsystem.service.ItemService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class ItemController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String itemList(Model model) {
        List<ItemDto> items = itemService.getAllItems().stream().map(ItemDto::from).toList();
        model.addAttribute("items", items);

        return "/item/itemList";
    }

    @GetMapping("/items/new")
    public String newItem(Model model) {
        model.addAttribute("item", new ItemCreateForm());
        model.addAttribute("typeOptions", ItemType.values());

        return "/item/newItem";
    }

    @PostMapping("/items/new")
    public String registerItem(@ModelAttribute("item") ItemCreateForm form, RedirectAttributes redirectAttributes) {

        var item = itemService.registerItem(form);
        redirectAttributes.addAttribute("id", item.getId());

        return "redirect:/items/{id}";
    }

    @GetMapping("/items/{id}")
    public String itemDetail(@PathVariable Long id, Model model) {
        var item = ItemDto.from(itemService.getItemById(id));
        model.addAttribute("item", item);
        model.addAttribute("typeOptions", ItemType.values());
        return "/item/itemDetail";
    }

    @PostMapping("/items/{id}")
    public String updateItem(@PathVariable Long id, @ModelAttribute("item") ItemDto form, Model model) {
        var item = itemService.updateItem(form);
        model.addAttribute("item", item);
        model.addAttribute("typeOptions", ItemType.values());

        return "redirect:/items";
    }

    @PostMapping("/items/{id}/delete")
    public String deleteItem(@PathVariable Long id) {
        itemService.deleteItem(id);

        return "redirect:/items";
    }
}
