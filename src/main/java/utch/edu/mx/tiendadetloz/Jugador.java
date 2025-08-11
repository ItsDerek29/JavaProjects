package utch.edu.mx.tiendadetloz;


public class Jugador {

    private int id;
    private String name;
    private int rupees;

    public Jugador(int id, String name, int rupees) {
        this.id = id;
        this.name = name;
        this.rupees = rupees;
    }
    //test de github

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRupees() {
        return rupees;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setRupees(int rupees) {
        this.rupees = rupees;
    }
}