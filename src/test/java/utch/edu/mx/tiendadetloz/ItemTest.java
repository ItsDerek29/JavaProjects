package utch.edu.mx.tiendadetloz;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class ItemTest {

    @Test
    public void testConstructorAndGetters() {
        Item item = new Item(1, "Espada", 100, 50, "Espada legendaria", 10, "Armas");

        assertEquals(1, item.getId());
        assertEquals("Espada", item.getName());
        assertEquals(100, item.getPrice());
        assertEquals(50, item.getSellPrice());
        assertEquals("Espada legendaria", item.getDescription());
        assertEquals(10, item.getStock());
        assertEquals("Armas", item.getCategory());
    }

    @Test
    public void testSetters() {
        Item item = new Item(0, "", 0, 0, "", 0, "");

        item.setId(2);
        item.setName("Escudo");
        item.setPrice(150);
        item.setSellPrice(75);
        item.setDescription("Escudo protector");
        item.setStock(5);
        item.setCategory("Defensa");

        assertEquals(2, item.getId());
        assertEquals("Escudo", item.getName());
        assertEquals(150, item.getPrice());
        assertEquals(75, item.getSellPrice());
        assertEquals("Escudo protector", item.getDescription());
        assertEquals(5, item.getStock());
        assertEquals("Defensa", item.getCategory());
    }
}