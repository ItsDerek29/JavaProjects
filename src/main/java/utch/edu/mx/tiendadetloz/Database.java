package utch.edu.mx.tiendadetloz;
import java.sql.*;

public class Database {
    private static final String DATABASE_URL = "jdbc:sqlite:TiendaDeTLOZ.db";

    public static void main(String[] args) {
        System.out.println("Creando la base de datos de la tiendita...");

        try {
            Connection connection = DriverManager.getConnection(DATABASE_URL);
            System.out.println("Database conectada correctamente.");

            createTables(connection);

            addInitialItems(connection);
            addInitialPlayer(connection);

            testReadingData(connection);

            connection.close();
            System.out.println("Proceso terminado con éxito.");

        } catch (SQLException e) {
            System.out.println("Error en la base de datos: " + e.getMessage());
            e.printStackTrace();
        }
    }

    // Aqui puse para que se creen las tablas
    private static void createTables(Connection connection) throws SQLException {
        Statement statement = connection.createStatement();


        String createItemsTable = """
            CREATE TABLE IF NOT EXISTS items (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                name TEXT NOT NULL,
                price INTEGER NOT NULL,
                sell_price INTEGER NOT NULL,
                description TEXT,
                stock INTEGER DEFAULT 0,
                category TEXT
            )
        """;


        String createPlayerTable = """
            CREATE TABLE IF NOT EXISTS player (
                id INTEGER PRIMARY KEY DEFAULT 1,
                name TEXT DEFAULT 'Link',
                rupees INTEGER DEFAULT 1000
            )
        """;


        String createInventoryTable = """
            CREATE TABLE IF NOT EXISTS player_inventory (
                id INTEGER PRIMARY KEY AUTOINCREMENT,
                item_id INTEGER,
                quantity INTEGER DEFAULT 0,
                FOREIGN KEY (item_id) REFERENCES items (id)
            )
        """;


        statement.execute(createItemsTable);
        statement.execute(createPlayerTable);
        statement.execute(createInventoryTable);

        System.out.println("Tablas creadas correctamente.");
        statement.close();
    }


    private static void addInitialItems(Connection connection) throws SQLException {
        // Vemos si no hay items ya creados
        Statement checkStatement = connection.createStatement();
        ResultSet rs = checkStatement.executeQuery("SELECT COUNT(*) FROM items");
        int itemCount = rs.getInt(1);
        rs.close();
        checkStatement.close();

        if (itemCount > 0) {
            System.out.println("Ya habían items, no se pondrán más.");
            return;
        }

        String insertItem = "INSERT INTO items (name, price, sell_price, description, stock, category) VALUES (?, ?, ?, ?, ?, ?)";
        PreparedStatement statement = connection.prepareStatement(insertItem);

        statement.setString(1, "Poción Roja");
        statement.setInt(2, 300);
        statement.setInt(3, 150);
        statement.setString(4, "Pocíón mágica que regenera el 100% de salud");
        statement.setInt(5, 3);
        statement.setString(6, "Pociones");
        statement.executeUpdate();

        statement.setString(1, "Poción Verde");
        statement.setInt(2, 200);
        statement.setInt(3, 100);
        statement.setString(4, "Regenera toda tu magia");
        statement.setInt(5, 3);
        statement.setString(6, "Pociones");
        statement.executeUpdate();

        statement.setString(1, "Poción Azul");
        statement.setInt(2, 400);
        statement.setInt(3, 200);
        statement.setString(4, "Regenera toda tu salud y magia");
        statement.setInt(5, 3);
        statement.setString(6, "Pociones");
        statement.executeUpdate();

        statement.setString(1, "Espada Maestra");
        statement.setInt(2, 750);
        statement.setInt(3, 400);
        statement.setString(4, "La espada que atrapa y destruye el mal");
        statement.setInt(5, 1);
        statement.setString(6, "Armas");
        statement.executeUpdate();

        statement.setString(1, "Arco");
        statement.setInt(2, 300);
        statement.setInt(3, 150);
        statement.setString(4, "Usado para atacar a distancia");
        statement.setInt(5, 1);
        statement.setString(6, "Armas");
        statement.executeUpdate();

        statement.setString(1, "Flechas (x10)");
        statement.setInt(2, 100);
        statement.setInt(3, 50);
        statement.setString(4, "Flechas para usar con el arco");
        statement.setInt(5, 5);
        statement.setString(6, "Armas");
        statement.executeUpdate();

        statement.setString(1, "Bombas (x5)");
        statement.setInt(2, 150);
        statement.setInt(3, 50);
        statement.setString(4, "BOOM! Explota después de 3 segundos");
        statement.setInt(5, 3);
        statement.setString(6, "Armas");
        statement.executeUpdate();

        System.out.println("Items iniciales agregados con éxito.");
        statement.close();
    }


    private static void addInitialPlayer(Connection connection) throws SQLException {
        // Checa si el jugador ya está creado o no
        Statement checkStatement = connection.createStatement();
        ResultSet rs = checkStatement.executeQuery("SELECT COUNT(*) FROM player");
        int playerCount = rs.getInt(1);
        rs.close();
        checkStatement.close();

        if (playerCount > 0) {
            System.out.println("El jugador ya existe, no hay que crearlo de nuevo.");
            return;
        }

        // Aquí se crea el jugador
        String insertPlayer = "INSERT INTO player (name, rupees) VALUES ('Link', 1000)";
        Statement statement = connection.createStatement();
        statement.execute(insertPlayer);

        System.out.println("Jugador 'Link' se creó con 1000 rupias!");
        statement.close();
    }


    private static void testReadingData(Connection connection) throws SQLException {
        System.out.println("Probando la database...");


        Statement statement = connection.createStatement();
        ResultSet rs = statement.executeQuery("SELECT * FROM items");

        // Lee los items de la tiendita
        System.out.println("Items en tiendita:");
        while (rs.next()) {
            System.out.println("- " + rs.getString("name") +
                    " (Price: " + rs.getInt("price") + " rupees, " +
                    "Stock: " + rs.getInt("stock") + ")");
        }
        rs.close();

        // Este lee los datos del jugador
        rs = statement.executeQuery("SELECT * FROM player");
        if (rs.next()) {
            System.out.println("\nJugador: " + rs.getString("name") +
                    " tiene " + rs.getInt("rupees") + " rupias.");
        }
        rs.close();
        statement.close();
    }
}
