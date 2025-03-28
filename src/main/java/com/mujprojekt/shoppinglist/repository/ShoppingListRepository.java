package com.mujprojekt.shoppinglist.repository;

import com.mujprojekt.shoppinglist.model.ShoppingList;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ShoppingListRepository  extends JpaRepository<ShoppingList, Long> {
}
