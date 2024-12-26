package org.example.library.client;

import java.util.List;
import java.util.Map;

public class LibraryService {
    private static final LibraryService INSTANCE = new LibraryService();

    private LibraryService() {}

    public static LibraryService getInstance() {
        return INSTANCE;
    }

    // Книги
    public List<Book> getAllBooks() {
        return BookStorage.getAll();
    }

    public void addBook(String title, String author, int year, String isbn, String genre) {
        BookStorage.addBook(title, author, year, isbn, genre);
    }

    // Копии
    public List<Copy> getAllCopies() {
        return CopyStorage.getAll();
    }

    public void addCopy(Long bookId, String invNumber) {
        CopyStorage.addCopy(bookId, invNumber);
    }

    // Выдачи
    public List<Issue> getAllIssues() {
        return IssueStorage.getAll();
    }

    public void issueBook(Long copyId, String userLogin) {
        IssueStorage.issueBook(copyId, userLogin);
    }

    public void returnBook(Long issueId) {
        IssueStorage.returnBook(issueId);
    }
    public ReaderProfile getReaderProfile(String userLogin) {
        return ReaderProfileStorage.getProfile(userLogin);
    }

    public void addBookToFavorites(String userLogin, Long bookId) {
        User u = UserStorage.findByLogin(userLogin);
        if (u == null || u.getRole() != UserRole.READER) {
            return; // Ничего не делаем, либо можно бросить исключение
        }
        ReaderProfile p = ReaderProfileStorage.getProfile(userLogin);
        if (p != null && BookStorage.findById(bookId) != null) {
            p.addFavoriteBook(bookId);
        }
    }

    public void addNoteToProfile(String userLogin, String note) {
        User u = UserStorage.findByLogin(userLogin);
        if (u == null || u.getRole() != UserRole.READER) {
            return;
        }
        ReaderProfile p = ReaderProfileStorage.getProfile(userLogin);
        if (p != null) {
            p.addNote(note);
        }
    }

    // Метод для LIBRARIAN: получить список всех профилей
    public Map<String, ReaderProfile> getAllReaderProfiles() {
        return ReaderProfileStorage.getAllProfiles();
    }

}
