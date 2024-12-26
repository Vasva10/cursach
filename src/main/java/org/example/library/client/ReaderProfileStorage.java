package org.example.library.client;

import java.util.HashMap;
import java.util.Map;

public class ReaderProfileStorage {
    private static final Map<String, ReaderProfile> profiles = new HashMap<>();

    public static void createProfileFor(String userLogin) {
        profiles.put(userLogin, new ReaderProfile(userLogin));
    }

    public static ReaderProfile getProfile(String userLogin) {
        return profiles.get(userLogin);
    }

    public static Map<String, ReaderProfile> getAllProfiles() {
        return new HashMap<>(profiles);
    }
}
