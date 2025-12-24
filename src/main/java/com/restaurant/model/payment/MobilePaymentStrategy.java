package com.restaurant.model.payment;

/**
 * PATRON STRATÉGIE - Stratégie concrète pour paiement mobile (BaridiMob, CIB, etc.)
 */
public class MobilePaymentStrategy implements PaymentStrategy {
    private String phoneNumber;

    public MobilePaymentStrategy(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("📱 Paiement de " + amount + " DA via mobile " + phoneNumber + " effectué avec succès");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Paiement Mobile";
    }
}