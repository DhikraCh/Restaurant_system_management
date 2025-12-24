package com.restaurant.model.notification;

import com.restaurant.model.order.Order;

/**
 * PATRON OBSERVATEUR - Interface Observer
 * Définit le contrat pour les objets observant les changements de commande
 */
public interface OrderObserver {
    void update(Order order, String event);
}