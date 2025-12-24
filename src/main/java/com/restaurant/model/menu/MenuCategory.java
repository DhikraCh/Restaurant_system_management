package com.restaurant.model.menu;

import java.util.ArrayList;
import java.util.List;

/**
 * PATRON COMPOSITION - Composite de la hiérarchie
 * Représente une catégorie pouvant contenir des plats ou d'autres catégories
 */
public class MenuCategory extends MenuComponent {
    private String name;
    private String description;
    private List<MenuComponent> components = new ArrayList<>();

    public MenuCategory(String name, String description) {
        this.name = name;
        this.description = description;
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
    public void add(MenuComponent component) {
        components.add(component);
    }

    @Override
    public void remove(MenuComponent component) {
        components.remove(component);
    }

    @Override
    public MenuComponent getChild(int i) {
        return components.get(i);
    }

    @Override
    public int getChildCount() {
        return components.size();
    }

    public List<MenuComponent> getComponents() {
        return new ArrayList<>(components);
    }

    @Override
    public void display(int indent) {
        System.out.println(getIndent(indent) + "📁 " + name + " - " + description);
        for (MenuComponent component : components) {
            component.display(indent + 1);
        }
    }

    @Override
    public String toString() {
        return name;
    }
}