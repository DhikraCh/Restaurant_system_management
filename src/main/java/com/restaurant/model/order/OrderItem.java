package com.restaurant.model.order;

import com.restaurant.model.menu.MenuIt;

/**
 * Représente un item dans une commande avec sa quantité
 */
public class OrderItem {
    private MenuIt menuItem;
    private int quantity;

    public OrderItem(MenuIt menuItem, int quantity) {
        this.menuItem = menuItem;
        this.quantity = quantity;
    }

    public MenuIt getMenuItem() {
        return menuItem;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public double getSubtotal() {
        return menuItem.getPrice() * quantity;
    }

    @Override
    public String toString() {
        return quantity + "x " + menuItem.getName() + " - " + getSubtotal() + " DA";
    }
}
