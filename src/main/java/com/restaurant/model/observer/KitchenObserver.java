package com.restaurant.model.notification;

import com.restaurant.model.order.Order;

/**
 * PATRON OBSERVATEUR - Observateur concret pour la cuisine
 * Reçoit les notifications des nouvelles commandes
 */
public class KitchenObserver implements OrderObserver {

    @Override
    public void update(Order order, String event) {
        if ("ORDER_VALIDATED".equals(event)) {
            System.out.println("\n🔔 [CUISINE] Nouvelle commande reçue !");
            System.out.println("   Commande #" + order.getOrderId());
            System.out.println("   Items: " + order.getItems().size());
            System.out.println("   Total: " + order.getTotal() + " DA");
            System.out.println("   → Préparation en cours...\n");
        }
    }
}