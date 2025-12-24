package com.restaurant.model.payment;

/**
 * PATRON STRATÉGIE - Stratégie concrète pour paiement par carte bancaire
 */
public class CardPaymentStrategy implements PaymentStrategy {
    private String cardNumber;

    public CardPaymentStrategy(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    @Override
    public boolean pay(double amount) {
        System.out.println("💳 Paiement de " + amount + " DA par carte ****" +
                cardNumber.substring(cardNumber.length() - 4) + " effectué avec succès");
        return true;
    }

    @Override
    public String getPaymentMethod() {
        return "Carte Bancaire";
    }
}