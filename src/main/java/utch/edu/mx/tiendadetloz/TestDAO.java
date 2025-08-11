package utch.edu.mx.tiendadetloz;

import java.util.List;

public class TestDAO {
    public static void main(String[] args) {
        // Primero ejecuta Database.main() para crear la DB
        Database.main(new String[]{});

        // Luego prueba los DAOs
        ItemDAO itemDAO = new ItemDAO();
        List<Item> items = itemDAO.getAllItems();
        System.out.println("Items encontrados: " + items.size());

        JugadorDAO jugadorDAO = new JugadorDAO();
        Jugador jugador = jugadorDAO.getJugador();
        System.out.println("Jugador: " + jugador.getName() + " tiene " + jugador.getRupees() + " rupias");
    }
}
