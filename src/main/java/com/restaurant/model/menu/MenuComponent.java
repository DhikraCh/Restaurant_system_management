package com.restaurant.model.menu;

/**
 * PATRON COMPOSITION - Composant de base pour la hiérarchie du menu
 * Permet de traiter uniformément les éléments individuels et les catégories
 */
public abstract class MenuComponent {

    public String getName() {
        throw new UnsupportedOperationException();
    }

    public String getDescription() {
        throw new UnsupportedOperationException();
    }

    public double getPrice() {
        throw new UnsupportedOperationException();
    }

    public void add(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public void remove(MenuComponent component) {
        throw new UnsupportedOperationException();
    }

    public MenuComponent getChild(int i) {
        throw new UnsupportedOperationException();
    }

    public int getChildCount() {
        return 0;
    }

    public abstract void display(int indent);

    protected String getIndent(int indent) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < indent; i++) {
            sb.append("  ");
        }
        return sb.toString();
    }
}