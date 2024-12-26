package org.example.library.client;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

public class IssueStorage {
    private static final List<Issue> issues = new ArrayList<>();
    private static final AtomicLong idGen = new AtomicLong(1);

    public static Issue issueBook(Long copyId, String userLogin) {
        Copy copy = CopyStorage.findById(copyId);
        if (copy == null || !"AVAILABLE".equals(copy.getStatus())) return null;
        User user = UserStorage.findByLogin(userLogin);
        if (user == null) return null;
        copy.setStatus("BORROWED");
        Issue issue = new Issue(idGen.getAndIncrement(), copy, user, LocalDate.now(), false);
        issues.add(issue);
        return issue;
    }

    public static Issue findById(Long id) {
        for (Issue i : issues) {
            if (i.getId().equals(id)) return i;
        }
        return null;
    }

    public static List<Issue> getAll() {
        return new ArrayList<>(issues);
    }

    public static boolean returnBook(Long issueId) {
        Issue issue = findById(issueId);
        if (issue == null || issue.getIsReturned()) return false;
        issue.returnBook();
        return true;
    }
}
