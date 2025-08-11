package utch.edu.mx.tiendadetloz;

public class Item {
    private int id;
    private String name;
    private int price;
    private int sellPrice;
    private String description;
    private int stock;
    private String category;

    public Item(int id, String name, int price, int sellPrice, String description, int stock, String category) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.sellPrice = sellPrice;
        this.description = description;
        this.stock = stock;
        this.category = category;
        }


        public int getId() {
            return id;
        }
        public String getName() {
        return name;
        }
        public int getPrice() {
        return price;
        }
        public int getSellPrice() {
        return sellPrice;
        }
        public String getDescription() {
        return description;
        }
        public int getStock() {
        return stock;
        }
        public String getCategory() {
        return category;
        }


        public void setId(int id) {
        this.id=id;
        }
        public void setName(String name) {
        this.name = name;
        }
        public void setPrice(int price) {
        this.price = price;
        }
        public void setSellPrice(int sellPrice) {
        this.sellPrice = sellPrice;
        }
        public void setDescription(String description) {
        this.description = description;
        }
        public void setStock(int stock) {
        this.stock = stock;
        }
        public void setCategory(String category) {
        this.category = category;
        }


}
