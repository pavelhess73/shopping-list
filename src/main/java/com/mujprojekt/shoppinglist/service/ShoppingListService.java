package com.mujprojekt.shoppinglist.service;

import com.mujprojekt.shoppinglist.model.ShoppingList;
import com.mujprojekt.shoppinglist.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ShoppingListService {

    private ShoppingListRepository shoppingListRepository;

    @Autowired
    public ShoppingListService(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }
    public List<ShoppingList> getAllShoppingLists() {
        return shoppingListRepository.findAll();
    }
    public Optional<ShoppingList> getShoppingListById(Long id) {
        return shoppingListRepository.findById(id);
    }
    public ShoppingList createShoppingList(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }
    public ShoppingList updateShoppingList(ShoppingList shoppingList) {
        return shoppingListRepository.save(shoppingList);
    }
    public void deleteShoppingList(Long id) {
        shoppingListRepository.deleteById(id);
    }
}
