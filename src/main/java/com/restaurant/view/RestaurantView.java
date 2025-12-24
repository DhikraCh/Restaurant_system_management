package com.restaurant.view;

import com.restaurant.controller.RestaurantController;
import com.restaurant.model.menu.*;
import com.restaurant.model.order.*;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 * VUE MVC - Interface utilisateur JavaFX
 * Affiche les données et capture les actions utilisateur
 */
public class RestaurantView {
    private RestaurantController controller;
    private Stage stage;

    private ListView<MenuIt> menuListView; // <--- remplacé MenuItem par MenuIt
    private ListView<OrderItem> orderListView;
    private Label totalLabel;
    private TextArea outputArea;
    private ComboBox<String> paymentMethodCombo;
    private TextField paymentDetailsField;

    public RestaurantView(RestaurantController controller, Stage stage) {
        this.controller = controller;
        this.stage = stage;
    }

    public void show() {
        BorderPane root = new BorderPane();
        root.setPadding(new Insets(10));

        root.setTop(createHeader());
        root.setCenter(createMainContent());
        root.setBottom(createConsoleOutput());

        Scene scene = new Scene(root, 1200, 700);
        stage.setTitle("Système de Restaurant - MVC + Patrons de Conception");
        stage.setScene(scene);
        stage.show();

        loadMenu();
    }

    private VBox createHeader() {
        VBox header = new VBox(5);
        header.setAlignment(Pos.CENTER);
        header.setPadding(new Insets(10));

        Label title = new Label("🍽️ SYSTÈME DE RESTAURANT 🍽️");
        title.setFont(Font.font("Arial", FontWeight.BOLD, 24));

        Label subtitle = new Label("Architecture MVC | Singleton | Composition | Stratégie | Observateur");
        subtitle.setFont(Font.font("Arial", FontWeight.NORMAL, 12));

        header.getChildren().addAll(title, subtitle, new Separator());
        return header;
    }

    private HBox createMainContent() {
        HBox mainContent = new HBox(15);
        mainContent.setPadding(new Insets(10));

        mainContent.getChildren().addAll(
                createMenuPanel(),
                createOrderPanel(),
                createPaymentPanel()
        );

        return mainContent;
    }

    private VBox createMenuPanel() {
        VBox panel = new VBox(10);
        panel.setPrefWidth(400);
        panel.setPadding(new Insets(10));

        Label titleLabel = new Label("📋 MENU (Patron Composition)");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        ComboBox<MenuCategory> categoryCombo = new ComboBox<>();
        categoryCombo.setPromptText("Sélectionner une catégorie");
        categoryCombo.setPrefWidth(380);

        menuListView = new ListView<>();
        menuListView.setPrefHeight(300);

        categoryCombo.setOnAction(e -> {
            MenuCategory selected = categoryCombo.getValue();
            if (selected != null) {
                menuListView.getItems().clear();
                for (MenuComponent comp : selected.getComponents()) {
                    if (comp instanceof MenuIt) { // <--- remplacé MenuItem par MenuIt
                        menuListView.getItems().add((MenuIt) comp); // <--- remplacé MenuItem par MenuIt
                    }
                }
            }
        });

        categoryCombo.getItems().addAll(controller.getMenuCategories());

        Spinner<Integer> quantitySpinner = new Spinner<>(1, 20, 1);
        quantitySpinner.setPrefWidth(100);

        Button addButton = new Button("➕ Ajouter à la commande");
        addButton.setPrefWidth(380);
        addButton.setOnAction(e -> {
            MenuIt selected = menuListView.getSelectionModel().getSelectedItem(); // <--- remplacé MenuItem par MenuIt
            if (selected != null) {
                int quantity = quantitySpinner.getValue();
                controller.addItemToOrder(selected, quantity);
                updateOrderDisplay();
                logAction("Ajouté: " + quantity + "x " + selected.getName());
            } else {
                showAlert("Veuillez sélectionner un plat");
            }
        });

        HBox quantityBox = new HBox(10, new Label("Quantité:"), quantitySpinner, addButton);
        quantityBox.setAlignment(Pos.CENTER_LEFT);

        panel.getChildren().addAll(titleLabel, categoryCombo, menuListView, quantityBox);
        return panel;
    }

    private VBox createOrderPanel() {
        VBox panel = new VBox(10);
        panel.setPrefWidth(350);
        panel.setPadding(new Insets(10));

        Label titleLabel = new Label("🛒 COMMANDE EN COURS");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        orderListView = new ListView<>();
        orderListView.setPrefHeight(300);

        totalLabel = new Label("Total: 0.00 DA");
        totalLabel.setFont(Font.font("Arial", FontWeight.BOLD, 18));

        Button removeButton = new Button("🗑️ Retirer l'article");
        removeButton.setPrefWidth(330);
        removeButton.setOnAction(e -> {
            int selectedIndex = orderListView.getSelectionModel().getSelectedIndex();
            if (selectedIndex >= 0) {
                controller.removeItemFromOrder(selectedIndex);
                updateOrderDisplay();
                logAction("Article retiré de la commande");
            } else {
                showAlert("Veuillez sélectionner un article à retirer");
            }
        });

        Button newOrderButton = new Button("🆕 Nouvelle Commande");
        newOrderButton.setPrefWidth(330);
        newOrderButton.setOnAction(e -> {
            controller.createNewOrder();
            updateOrderDisplay();
            logAction("Nouvelle commande créée");
        });

        panel.getChildren().addAll(titleLabel, orderListView, totalLabel, removeButton, newOrderButton);
        return panel;
    }

    private VBox createPaymentPanel() {
        VBox panel = new VBox(10);
        panel.setPrefWidth(350);
        panel.setPadding(new Insets(10));

        Label titleLabel = new Label("💳 PAIEMENT (Patron Stratégie)");
        titleLabel.setFont(Font.font("Arial", FontWeight.BOLD, 16));

        paymentMethodCombo = new ComboBox<>();
        paymentMethodCombo.getItems().addAll("Espèces", "Carte Bancaire", "Paiement Mobile");
        paymentMethodCombo.setPromptText("Méthode de paiement");
        paymentMethodCombo.setPrefWidth(330);

        paymentDetailsField = new TextField();
        paymentDetailsField.setPromptText("Ex: N° carte ou téléphone");
        paymentDetailsField.setPrefWidth(330);
        paymentDetailsField.setDisable(true);

        paymentMethodCombo.setOnAction(e -> {
            String method = paymentMethodCombo.getValue();
            if ("Carte Bancaire".equals(method)) {
                paymentDetailsField.setDisable(false);
                paymentDetailsField.setPromptText("N° de carte (ex: 1234567890123456)");
            } else if ("Paiement Mobile".equals(method)) {
                paymentDetailsField.setDisable(false);
                paymentDetailsField.setPromptText("N° de téléphone (ex: 0555123456)");
            } else {
                paymentDetailsField.setDisable(true);
                paymentDetailsField.clear();
            }
        });

        Button payButton = new Button("✅ VALIDER ET PAYER");
        payButton.setPrefWidth(330);
        payButton.setFont(Font.font("Arial", FontWeight.BOLD, 14));
        payButton.setOnAction(e -> processPayment());

        Label infoLabel = new Label("ℹ️ Info: La validation notifiera\nautomatiquement la cuisine\n(Patron Observateur)");
        infoLabel.setFont(Font.font("Arial", FontWeight.NORMAL, 11));
        infoLabel.setWrapText(true);

        panel.getChildren().addAll(
                titleLabel,
                paymentMethodCombo,
                paymentDetailsField,
                payButton,
                new Separator(),
                infoLabel
        );

        return panel;
    }

    private VBox createConsoleOutput() {
        VBox panel = new VBox(5);
        panel.setPadding(new Insets(10));

        Label label = new Label("📊 Journal d'activité:");
        label.setFont(Font.font("Arial", FontWeight.BOLD, 12));

        outputArea = new TextArea();
        outputArea.setEditable(false);
        outputArea.setPrefHeight(120);
        outputArea.setFont(Font.font("Monospaced", 11));

        panel.getChildren().addAll(label, outputArea);
        return panel;
    }

    private void loadMenu() {
        logAction("Système initialisé - Patron Singleton activé");
        logAction("Menu chargé avec structure hiérarchique - Patron Composition");
    }

    private void updateOrderDisplay() {
        orderListView.getItems().clear();

        Order currentOrder = controller.getCurrentOrder();
        if (currentOrder != null) {
            orderListView.getItems().addAll(currentOrder.getItems());
            totalLabel.setText(String.format("Total: %.2f DA", currentOrder.getTotal()));
        } else {
            totalLabel.setText("Total: 0.00 DA");
        }
    }

    private void processPayment() {
        if (!controller.hasCurrentOrder()) {
            showAlert("Aucune commande en cours");
            return;
        }

        String method = paymentMethodCombo.getValue();
        if (method == null) {
            showAlert("Veuillez sélectionner une méthode de paiement");
            return;
        }

        String details = paymentDetailsField.getText();
        String methodCode = "";

        switch (method) {
            case "Espèces":
                methodCode = "CASH";
                details = "";
                break;
            case "Carte Bancaire":
                methodCode = "CARD";
                if (details.isEmpty()) {
                    showAlert("Veuillez entrer le numéro de carte");
                    return;
                }
                break;
            case "Paiement Mobile":
                methodCode = "MOBILE";
                if (details.isEmpty()) {
                    showAlert("Veuillez entrer le numéro de téléphone");
                    return;
                }
                break;
        }

        controller.setPaymentMethod(methodCode, details);

        if (controller.validateAndPayOrder()) {
            logAction("=== PAIEMENT RÉUSSI ===");
            logAction("Méthode: " + method);
            logAction("Notification envoyée à la cuisine (Observateur)");
            logAction("========================");

            updateOrderDisplay();
            paymentMethodCombo.setValue(null);
            paymentDetailsField.clear();
            paymentDetailsField.setDisable(true);

            showSuccessAlert("Commande validée et payée avec succès!\nLa cuisine a été notifiée.");
        } else {
            showAlert("Échec du paiement");
        }
    }

    private void logAction(String message) {
        outputArea.appendText(message + "\n");
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.WARNING);
        alert.setTitle("Attention");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    private void showSuccessAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Succès");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }
}
