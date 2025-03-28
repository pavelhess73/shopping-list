package com.mujprojekt.shoppinglist.controller;



import com.mujprojekt.shoppinglist.model.ShoppingList;
import com.mujprojekt.shoppinglist.repository.ShoppingListRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class TestController {

    private final ShoppingListRepository shoppingListRepository;

    @Autowired
    public TestController(ShoppingListRepository shoppingListRepository) {
        this.shoppingListRepository = shoppingListRepository;
    }

    @GetMapping("/test")
    public String test() {
        // Vytvoření testovacího seznamu
        ShoppingList list = new ShoppingList("Testovací seznam");
        shoppingListRepository.save(list);

        // Načtení všech seznamů
        List<ShoppingList> lists = shoppingListRepository.findAll();

        return "Vytvořen testovací seznam. Celkem seznamů: " + lists.size();
    }
}