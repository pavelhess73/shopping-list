package com.mujprojekt.shoppinglist.service;

import com.mujprojekt.shoppinglist.model.ShoppingItem;
import com.mujprojekt.shoppinglist.model.ShoppingList;
import com.mujprojekt.shoppinglist.repository.ShoppingItemRepository;
import com.mujprojekt.shoppinglist.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingItemService {
    private ShoppingItemRepository shoppingItemRepository;
    private ShoppingListRepository shoppingListRepository;

    @Autowired
    public  ShoppingItemService(ShoppingItemRepository shoppingItemRepository, ShoppingListRepository shoppingListRepository) {
        this.shoppingItemRepository = shoppingItemRepository;
        this.shoppingListRepository = shoppingListRepository;
    }

    public List<ShoppingItem> getItemByShoppingListId(Long shoppingListId) {
        return shoppingItemRepository.findByShoppingListId(shoppingListId);
    }
    public Optional<ShoppingItem> getItemById(Long Id) {
        return shoppingItemRepository.findById(Id);
    }

    public ShoppingItem createItem(Long shoppingListId, ShoppingItem item) {
        Optional<ShoppingList> shoppingListOpt = shoppingListRepository.findById(shoppingListId);
        if (shoppingListOpt.isPresent()) {
            ShoppingList shoppingList = shoppingListOpt.get();
            item.setShoppingList(shoppingList);
            return shoppingItemRepository.save(item);
        }
        throw new IllegalArgumentException("Shopping list not found with id " + shoppingListId);
    }
    public ShoppingItem updateItem( ShoppingItem item) {
        return shoppingItemRepository.save(item);
    }

    public void toggleItemCompletion(Long id) {
        Optional<ShoppingItem> itemOpt = shoppingItemRepository.findById(id);
        if (itemOpt.isPresent()) {
            ShoppingItem item = itemOpt.get();
            item.setCompleted(!item.getCompleted());
            shoppingItemRepository.save(item);
        }
    }

    public void deleteItem(Long id) {
        shoppingItemRepository.deleteById(id);
    }
}

