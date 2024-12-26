package org.example.library.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class BookStorage {
    private static final List<Book> books = new ArrayList<>();
    private static final AtomicLong idGenerator = new AtomicLong(1);

    public static Book addBook(String title, String author, int year, String isbn, String genre) {
        Book book = new Book(idGenerator.getAndIncrement(), title, author, year, isbn, genre);
        books.add(book);
        return book;
    }

    public static List<Book> getAll() {
        return new ArrayList<>(books);
    }

    public static Book findById(Long id) {
        for (Book b : books) {
            if (b.getId().equals(id)) return b;
        }
        return null;
    }
}
