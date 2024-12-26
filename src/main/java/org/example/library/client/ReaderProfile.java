package org.example.library.client;

import java.util.ArrayList;
import java.util.List;

public class ReaderProfile {
    private String userLogin; // чей это профиль
    private List<Long> favoriteBookIds = new ArrayList<>(); // избранные книги
    private List<String> notes = new ArrayList<>(); // заметки читателя

    public ReaderProfile(String userLogin) {
        this.userLogin = userLogin;
    }

    public String getUserLogin() {
        return userLogin;
    }

    public List<Long> getFavoriteBookIds() {
        return favoriteBookIds;
    }

    public List<String> getNotes() {
        return notes;
    }

    public void addFavoriteBook(Long bookId) {
        if (!favoriteBookIds.contains(bookId)) {
            favoriteBookIds.add(bookId);
        }
    }

    public void addNote(String note) {
        notes.add(note);
    }
}
