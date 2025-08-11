package utch.edu.mx.tiendadetloz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class InventoryDAO {
    private static final String DATABASE_URL = "jdbc:sqlite:TiendaDeTLOZ.db";

    // Agregar item al inventario del jugador
    public boolean addItemToInventory(int itemId, int quantity) {
        // Primero verificar si ya tiene ese item
        String checkQuery = "SELECT quantity FROM player_inventory WHERE item_id = ?";
        String updateQuery = "UPDATE player_inventory SET quantity = quantity + ? WHERE item_id = ?";
        String insertQuery = "INSERT INTO player_inventory (item_id, quantity) VALUES (?, ?)";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Verificar si ya existe
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, itemId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                // Ya existe, solo actualizar cantidad
                PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                updateStmt.setInt(1, quantity);
                updateStmt.setInt(2, itemId);
                return updateStmt.executeUpdate() > 0;
            } else {
                // No existe, insertar nuevo
                PreparedStatement insertStmt = conn.prepareStatement(insertQuery);
                insertStmt.setInt(1, itemId);
                insertStmt.setInt(2, quantity);
                return insertStmt.executeUpdate() > 0;
            }

        } catch (SQLException e) {
            System.out.println("Error al agregar item al inventario: " + e.getMessage());
            return false;
        }
    }

    // Quitar item del inventario del jugador
    public boolean removeItemFromInventory(int itemId, int quantity) {
        String checkQuery = "SELECT quantity FROM player_inventory WHERE item_id = ?";
        String updateQuery = "UPDATE player_inventory SET quantity = quantity - ? WHERE item_id = ?";
        String deleteQuery = "DELETE FROM player_inventory WHERE item_id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL)) {
            // Verificar cuántos tiene
            PreparedStatement checkStmt = conn.prepareStatement(checkQuery);
            checkStmt.setInt(1, itemId);
            ResultSet rs = checkStmt.executeQuery();

            if (rs.next()) {
                int currentQuantity = rs.getInt("quantity");
                if (currentQuantity < quantity) {
                    return false; // No tiene suficientes
                }

                if (currentQuantity == quantity) {
                    // Borrar completamente
                    PreparedStatement deleteStmt = conn.prepareStatement(deleteQuery);
                    deleteStmt.setInt(1, itemId);
                    return deleteStmt.executeUpdate() > 0;
                } else {
                    // Solo reducir cantidad
                    PreparedStatement updateStmt = conn.prepareStatement(updateQuery);
                    updateStmt.setInt(1, quantity);
                    updateStmt.setInt(2, itemId);
                    return updateStmt.executeUpdate() > 0;
                }
            } else {
                return false; // No tiene ese item
            }

        } catch (SQLException e) {
            System.out.println("Error al quitar item del inventario: " + e.getMessage());
            return false;
        }
    }

    // Obtener cantidad de un item en el inventario
    public int getItemQuantity(int itemId) {
        String query = "SELECT quantity FROM player_inventory WHERE item_id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, itemId);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return rs.getInt("quantity");
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener cantidad del item: " + e.getMessage());
        }

        return 0; // No tiene el item
    }
}