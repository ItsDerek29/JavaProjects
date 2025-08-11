package utch.edu.mx.tiendadetloz;

import java.sql.*;

public class JugadorDAO {
    private static final String DATABASE_URL = "jdbc:sqlite:TiendaDeTLOZ.db";

    // Obtener los datos del jugador
    public Jugador getJugador() {
        String query = "SELECT * FROM player LIMIT 1";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(query)) {

            if (rs.next()) {
                Jugador jugador = new Jugador(
                        rs.getInt("id"),
                        rs.getString("name"),
                        rs.getInt("rupees")
                );
                return jugador;
            }

        } catch (SQLException e) {
            System.out.println("Error al obtener jugador: " + e.getMessage());
        }

        return null;
    }

    // Actualizar las rupias del jugador
    public boolean updateRupees(int newAmount) {
        String query = "UPDATE player SET rupees = ? WHERE id = 1";

        try (Connection conn = DriverManager.getConnection(DATABASE_URL);
             PreparedStatement stmt = conn.prepareStatement(query)) {

            stmt.setInt(1, newAmount);

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            System.out.println("Error al actualizar rupias: " + e.getMessage());
            return false;
        }
    }

    // Restar rupias del jugador (para compras)
    public boolean spendRupees(int amount) {
        Jugador jugador = getJugador();
        if (jugador != null && jugador.getRupees() >= amount) {
            return updateRupees(jugador.getRupees() - amount);
        }
        return false;
    }

    // Sumar rupias al jugador (para ventas)
    public boolean addRupees(int amount) {
        Jugador jugador = getJugador();
        if (jugador != null) {
            return updateRupees(jugador.getRupees() + amount);
        }
        return false;
    }
}