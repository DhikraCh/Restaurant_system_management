package com.restaurant.model.payment;

/**
 * PATRON STRATÉGIE - Interface définissant l'algorithme de paiement
 * Permet de changer dynamiquement la stratégie de paiement
 */
public interface PaymentStrategy {
    boolean pay(double amount);
    String getPaymentMethod();
}