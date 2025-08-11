package utch.edu.mx.tiendadetloz;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class ItemDAO {
    private static final String DATABASE_URL = "jdbc:sqlite:TiendaDeTLOZ.db";

    // Obtener todos los items de la tienda
    public List<Item> getAllItems() {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("sell_price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("category")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener items: " + e.getMessage());
        }

        return items;
    }

    // Obtener un item por su ID
    public Item getItemById(int id) {
        String query = "SELECT * FROM items WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("sell_price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("category")
                );
                return item;
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener item: " + e.getMessage());
        }

        return null;
    }

    // Actualizar el stock de un item (para cuando se compre)
    public boolean updateStock(int itemId, int newStock) {
        String query = "UPDATE items SET stock = ? WHERE id = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newStock);
            stmt.setInt(2, itemId);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar stock: " + e.getMessage());
            return false;
        }
    }

    // Obtener items por categoría
    public List<Item> getItemsByCategory(String category) {
        List<Item> items = new ArrayList<>();
        String query = "SELECT * FROM items WHERE category = ?";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setString(1, category);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                Item item = new Item(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("price"),
                        rs.getInt("sell_price"),
                        rs.getString("description"),
                        rs.getInt("stock"),
                        rs.getString("category")
                );
                items.add(item);
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener items por categoría: " + e.getMessage());
        }

        return items;
    }
}