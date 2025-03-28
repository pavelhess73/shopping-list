package com.mujprojekt.shoppinglist.repository;

import com.mujprojekt.shoppinglist.model.ShoppingItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ShoppingItemRepository extends JpaRepository<ShoppingItem, Long> {
    List<ShoppingItem> findByShoppingListId(Long shoppingListId);
    List<ShoppingItem> findByShoppingListIdAndCompleted(Long shoppingListId, Boolean completed);
}
