package org.example.library.client;

import java.util.ArrayList;
import java.util.List;

public class UserStorage {
    private static final List<User> users = new ArrayList<>();

    static {
        // Начальный LIBRARIAN, как и раньше
        users.add(new User("admin", "secret", "Admin User", UserRole.LIBRARIAN));
    }

    public static User findByLogin(String login) {
        for (User u : users) {
            if (u.getLogin().equalsIgnoreCase(login)) {
                return u;
            }
        }
        return null;
    }

    public static void addUser(User user) {
        users.add(user);
        if (user.getRole() == UserRole.READER) {
            ReaderProfileStorage.createProfileFor(user.getLogin());
        }
    }


    public static boolean hasLibrarian() {
        for (User u : users) {
            if (u.getRole() == UserRole.LIBRARIAN) {
                return true;
            }
        }
        return false;
    }
}
