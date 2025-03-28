package com.mujprojekt.shoppinglist.controller;



import com.mujprojekt.shoppinglist.model.ShoppingItem;
import com.mujprojekt.shoppinglist.model.ShoppingList;
import com.mujprojekt.shoppinglist.service.ShoppingListService;
import com.mujprojekt.shoppinglist.service.ShoppingItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/lists")
public class ShoppingListController {

    private final ShoppingListService shoppingListService;
    private final ShoppingItemService shoppingItemService;

    @Autowired
    public ShoppingListController(ShoppingListService shoppingListService, ShoppingItemService shoppingItemService) {
        this.shoppingListService = shoppingListService;
        this.shoppingItemService = shoppingItemService;
    }

    @GetMapping
    public String getAllLists(Model model) {
        model.addAttribute("lists", shoppingListService.getAllShoppingLists());
        model.addAttribute("newList", new ShoppingList());
        return "lists";
    }

    @GetMapping("/{id}")
    public String getListById(@PathVariable Long id,
                              @RequestParam(name = "filter", defaultValue = "all") String filter,
                              Model model) {
        Optional<ShoppingList> listOptional = shoppingListService.getShoppingListById(id);
        if (listOptional.isPresent()) {
            ShoppingList originalList = listOptional.get();

            // Vyfiltrování položek podle parametru
            List<ShoppingItem> filteredItems;
            if ("completed".equals(filter)) {
                // Jen splněné položky
                filteredItems = originalList.getItems().stream()
                        .filter(ShoppingItem::getCompleted)
                        .collect(Collectors.toList());
            } else if ("active".equals(filter)) {
                // Jen nesplněné položky
                filteredItems = originalList.getItems().stream()
                        .filter(item -> !item.getCompleted())
                        .collect(Collectors.toList());
            } else {
                // Všechny položky (výchozí)
                filteredItems = originalList.getItems();
            }

            // Vytvoření kopie seznamu jen s vyfiltrovanými položkami
            ShoppingList filteredList = new ShoppingList();
            filteredList.setId(originalList.getId());
            filteredList.setName(originalList.getName());
            filteredList.setCreatedAt(originalList.getCreatedAt());
            filteredList.setItems(filteredItems);

            // Přidání atributů do modelu
            model.addAttribute("list", filteredList);
            model.addAttribute("filter", filter);
            model.addAttribute("allItemsCount", originalList.getItems().size());
            model.addAttribute("completedItemsCount", originalList.getItems().stream()
                    .filter(ShoppingItem::getCompleted)
                    .count());

            return "list-detail";
        }
        return "redirect:/lists";
    }

    @PostMapping
    public String createList(@ModelAttribute ShoppingList shoppingList) {
        shoppingListService.createShoppingList(shoppingList);
        return "redirect:/lists";
    }

    @PostMapping("/{id}/delete")
    public String deleteList(@PathVariable Long id) {
        shoppingListService.deleteShoppingList(id);
        return "redirect:/lists";
    }
}
