package model;

public class CartItem {
    private Book book;
    private int quantity;
    private long priceSnapshot; // giá “chụp cứng” để hiển thị trong giỏ

    public CartItem(Book book, int quantity, long priceSnapshot) {
        this.book = book;
        this.quantity = quantity;
        this.priceSnapshot = priceSnapshot;
    }

    public Book getBook() { return book; }
    public int getQuantity() { return quantity; }
    public long getPriceSnapshot() { return priceSnapshot; }

    public void setQuantity(int quantity) { this.quantity = quantity; }
}
