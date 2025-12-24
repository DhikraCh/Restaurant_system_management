package com.restaurant.model.menu;

/**
 * PATRON COMPOSITION - Feuille de la hiérarchie
 * Représente un plat individuel dans le menu
 */
public class MenuIt extends MenuComponent {
    private String name;
    private String description;
    private double price;

    public MenuIt(String name, String description, double price) {
        this.name = name;
        this.description = description;
        this.price = price;
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getDescription() {
        return description;
    }

    @Override
    public double getPrice() {
        return price;
    }

    @Override
    public void display(int indent) {
        System.out.println(getIndent(indent) + "→ " + name + " - " + price + " DA - " + description);
    }

    @Override
    public String toString() {
        return name + " (" + price + " DA)";
    }
}