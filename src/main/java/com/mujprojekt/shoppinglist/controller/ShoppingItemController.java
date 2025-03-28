package com.mujprojekt.shoppinglist.controller;

import com.mujprojekt.shoppinglist.model.ShoppingItem;
import com.mujprojekt.shoppinglist.service.ShoppingItemService;
import com.mujprojekt.shoppinglist.service.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/lists/{listId}/items")
public class ShoppingItemController {

    private final ShoppingItemService shoppingItemService;
    private final ShoppingListService shoppingListService;

    @Autowired
    public ShoppingItemController(ShoppingItemService shoppingItemService, ShoppingListService shoppingListService) {
        this.shoppingItemService = shoppingItemService;
        this.shoppingListService = shoppingListService;
    }

    @GetMapping("/create")
    public String showCreateItemForm(@PathVariable Long listId, Model model) {
        ShoppingItem newItem = new ShoppingItem();
        newItem.setQuantity(1);
        newItem.setUnit("ks");

        model.addAttribute("listId", listId);
        model.addAttribute("item", newItem);

        return "item-form";
    }

    @PostMapping
    public String createItem(@PathVariable Long listId,
                             @ModelAttribute ShoppingItem item,
                             @RequestParam(name = "filter", required = false) String filter) {
        shoppingItemService.createItem(listId, item);
        if (filter != null && !filter.isEmpty()) {
            return "redirect:/lists/" + listId + "?filter=" + filter;
        }
        return "redirect:/lists/" + listId;
    }

    @GetMapping("/{itemId}/edit")
    public String showEditItemForm(@PathVariable Long listId,
                                   @PathVariable Long itemId,
                                   @RequestParam(name = "filter", required = false) String filter,
                                   Model model) {
        shoppingItemService.getItemById(itemId).ifPresent(item -> {
            model.addAttribute("item", item);
            model.addAttribute("listId", listId);
        });
        if (filter != null && !filter.isEmpty()) {
            model.addAttribute("filter", filter);
        }
        return "item-form";
    }

    @PostMapping("/{itemId}")
    public String updateItem(@PathVariable Long listId,
                             @PathVariable Long itemId,
                             @ModelAttribute ShoppingItem item,
                             @RequestParam(name = "filter", required = false) String filter) {
        item.setId(itemId);
        shoppingItemService.updateItem(item);
        if (filter != null && !filter.isEmpty()) {
            return "redirect:/lists/" + listId + "?filter=" + filter;
        }
        return "redirect:/lists/" + listId;
    }

    @PostMapping("/{itemId}/toggle")
    public String toggleItemCompletion(@PathVariable Long listId,
                                       @PathVariable Long itemId,
                                       @RequestParam(name = "filter", required = false) String filter) {
        shoppingItemService.toggleItemCompletion(itemId);
        if (filter != null && !filter.isEmpty()) {
            return "redirect:/lists/" + listId + "?filter=" + filter;
        }
        return "redirect:/lists/" + listId;
    }

    @PostMapping("/{itemId}/delete")
    public String deleteItem(@PathVariable Long listId,
                             @PathVariable Long itemId,
                             @RequestParam(name = "filter", required = false) String filter) {
        shoppingItemService.deleteItem(itemId);
        if (filter != null && !filter.isEmpty()) {
            return "redirect:/lists/" + listId + "?filter=" + filter;
        }
        return "redirect:/lists/" + listId;
    }
}