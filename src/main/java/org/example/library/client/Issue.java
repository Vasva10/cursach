package org.example.library.client;

import java.time.LocalDate;

public class Issue {
    private Long id;
    private Copy copy;
    private User user;
    private LocalDate issueDate;
    private LocalDate returnDate;
    private Boolean isReturned;

    public Issue(Long id, Copy copy, User user, LocalDate issueDate, Boolean isReturned) {
        this.id = id;
        this.copy = copy;
        this.user = user;
        this.issueDate = issueDate;
        this.isReturned = isReturned;
    }

    public Long getId() { return id; }
    public Copy getCopy() { return copy; }
    public User getUser() { return user; }
    public LocalDate getIssueDate() { return issueDate; }
    public LocalDate getReturnDate() { return returnDate; }
    public Boolean getIsReturned() { return isReturned; }

    public void returnBook() {
        this.isReturned = true;
        this.returnDate = LocalDate.now();
        this.copy.setStatus("AVAILABLE");
    }
}
