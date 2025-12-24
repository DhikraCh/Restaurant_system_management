package com.restaurant.model.payment;

/**
 * PATRON STRATÉGIE - Stratégie concrète pour paiement en espèces
 */
public class CashPaymentStrategy implements PaymentStrategy {

    @Override
    public boolean pay(double amount) {
        System.out.println("💵 Paiement de " + amount + " DA en espèces effectué avec succès");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Espèces";
    }
}