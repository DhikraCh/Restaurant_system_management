package com.restaurant.model.notification;

import com.restaurant.model.order.Order;
import java.util.ArrayList;
import java.util.List;

/**
 * PATRON OBSERVATEUR - Classe Subject (Observable)
 * Gère la liste des observateurs et les notifie des changements
 */
public class OrderSubject {
    private List<OrderObserver> observers = new ArrayList<>();

    public void attach(OrderObserver observer) {
        if (!observers.contains(observer)) {
            observers.add(observer);
        }
    }

    public void detach(OrderObserver observer) {
        observers.remove(observer);
    }

    public void notifyObservers(Order order, String event) {
        for (OrderObserver observer : observers) {
            observer.update(order, event);
        }
    }
}