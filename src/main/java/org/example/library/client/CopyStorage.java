package org.example.library.client;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class CopyStorage {
    private static final List<Copy> copies = new ArrayList<>();
    private static final AtomicLong idGen = new AtomicLong(1);

    public static Copy addCopy(Long bookId, String inventoryNumber) {
        Book book = BookStorage.findById(bookId);
        if (book == null) return null;
        Copy copy = new Copy(idGen.getAndIncrement(), book, inventoryNumber, "AVAILABLE");
        copies.add(copy);
        return copy;
    }

    public static List<Copy> getAll() {
        return new ArrayList<>(copies);
    }

    public static Copy findById(Long id) {
        for (Copy c : copies) {
            if (c.getId().equals(id)) return c;
        }
        return null;
    }
}
