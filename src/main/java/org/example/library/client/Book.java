package org.example.library.client;

public class Book {
    private Long id;
    private String title;
    private String author;
    private int publicationYear;
    private String isbn;
    private String genre;

    public Book(Long id, String title, String author, int publicationYear, String isbn, String genre) {
        this.id = id;
        this.title = title;
        this.author = author;
        this.publicationYear = publicationYear;
        this.isbn = isbn;
        this.genre = genre;
    }

    public Long getId() { return id; }
    public String getTitle() { return title; }
    public String getAuthor() { return author; }
    public int getPublicationYear() { return publicationYear; }
    public String getIsbn() { return isbn; }
    public String getGenre() { return genre; }
}
