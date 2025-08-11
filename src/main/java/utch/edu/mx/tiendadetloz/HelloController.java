package utch.edu.mx.tiendadetloz;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.util.List;

public class HelloController {

    @FXML
    private Label rupeesLabel;
    @FXML
    private ListView<String> itemsList;
    @FXML
    private Button buyButton;
    @FXML
    private Button sellButton;

    private ItemDAO itemDAO;
    private JugadorDAO jugadorDAO;
    private InventoryDAO inventoryDAO;
    private List<Item> items;
    private Item selectedItem;

    // Este método se ejecuta automáticamente
    @FXML
    private void initialize() {
        itemDAO = new ItemDAO();
        jugadorDAO = new JugadorDAO();
        inventoryDAO = new InventoryDAO();

        loadData();
    }

    private void loadData() {
        // Cargar items
        items = itemDAO.getAllItems();
        ObservableList<String> itemNames = FXCollections.observableArrayList();

        for (Item item : items) {
            int playerHas = inventoryDAO.getItemQuantity(item.getId());
            String itemText = item.getName() + " - Comprar: " + item.getPrice() + " rupias (Stock: " + item.getStock() + ") | Tienes: " + playerHas;
            itemNames.add(itemText);
        }

        itemsList.setItems(itemNames);

        // Cargar rupias del jugador
        Jugador jugador = jugadorDAO.getJugador();
        rupeesLabel.setText("Rupias: " + jugador.getRupees());
    }

    @FXML
    private void onItemSelected() {
        int index = itemsList.getSelectionModel().getSelectedIndex();
        if (index >= 0) {
            selectedItem = items.get(index);

            // Configurar botón comprar
            if (selectedItem.getStock() == 0) {
                buyButton.setDisable(true);
            } else {
                buyButton.setDisable(false);
            }

            // Configurar botón vender
            int playerHas = inventoryDAO.getItemQuantity(selectedItem.getId());
            if (playerHas == 0) {
                sellButton.setDisable(true);
            } else {
                sellButton.setDisable(false);
            }
        }
    }

    @FXML
    private void onBuyClick() {
        if (selectedItem == null) {
            showAlert("Selecciona un item primero");
            return;
        }

        if (selectedItem.getStock() == 0) {
            showAlert("Este item está agotado");
            return;
        }

        Jugador jugador = jugadorDAO.getJugador();
        if (jugador.getRupees() < selectedItem.getPrice()) {
            showAlert("No tienes suficientes rupias");
            return;
        }

        // Realizar compra
        boolean stockUpdated = itemDAO.updateStock(selectedItem.getId(), selectedItem.getStock() - 1);
        boolean rupeesUpdated = jugadorDAO.spendRupees(selectedItem.getPrice());
        boolean inventoryUpdated = inventoryDAO.addItemToInventory(selectedItem.getId(), 1);

        if (stockUpdated && rupeesUpdated && inventoryUpdated) {
            showAlert("¡Compra exitosa! Ahora tienes " + selectedItem.getName());
            loadData();
        } else {
            showAlert("Error en la compra");
        }
    }

    @FXML
    private void onSellClick() {
        if (selectedItem == null) {
            showAlert("Selecciona un item primero");
            return;
        }

        int playerHas = inventoryDAO.getItemQuantity(selectedItem.getId());
        if (playerHas == 0) {
            showAlert("No tienes este item para vender");
            return;
        }

        // Realizar venta
        int sellPrice = selectedItem.getSellPrice();
        boolean stockUpdated = itemDAO.updateStock(selectedItem.getId(), selectedItem.getStock() + 1);
        boolean rupeesUpdated = jugadorDAO.addRupees(sellPrice);
        boolean inventoryUpdated = inventoryDAO.removeItemFromInventory(selectedItem.getId(), 1);

        if (stockUpdated && rupeesUpdated && inventoryUpdated) {
            showAlert("¡Vendiste " + selectedItem.getName() + " por " + sellPrice + " rupias!");
            loadData();
        } else {
            showAlert("Error en la venta");
        }
    }

    private void showAlert(String message) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setContentText(message);
        alert.showAndWait();
    }
}