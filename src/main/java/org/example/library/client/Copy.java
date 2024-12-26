package org.example.library.client;

public class Copy {
    private Long id;
    private Book book;
    private String inventoryNumber;
    private String status; // "AVAILABLE", "BORROWED"

    public Copy(Long id, Book book, String inventoryNumber, String status) {
        this.id = id;
        this.book = book;
        this.inventoryNumber = inventoryNumber;
        this.status = status;
    }

    public Long getId() { return id; }
    public Book getBook() { return book; }
    public String getInventoryNumber() { return inventoryNumber; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }
}
