package org.example.library.service;

import org.example.library.domain.Copy;
import org.example.library.domain.Issue;
import org.example.library.domain.User;
import org.example.library.repository.CopyRepository;
import org.example.library.repository.IssueRepository;
import org.example.library.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
public class IssueService {
    private final IssueRepository issueRepository;
    private final CopyRepository copyRepository;
    private final UserRepository userRepository;

    public IssueService(IssueRepository issueRepository, CopyRepository copyRepository, UserRepository userRepository) {
        this.issueRepository = issueRepository;
        this.copyRepository = copyRepository;
        this.userRepository = userRepository;
    }

    public List<Issue> getAllIssues() {
        return issueRepository.findAll();
    }

    public Optional<Issue> getIssueById(Long id) {
        return issueRepository.findById(id);
    }

    public Issue issueBookToUser(Long copyId, Long userId) {
        Copy copy = copyRepository.findById(copyId)
                .orElseThrow(() -> new RuntimeException("Copy not found"));
        if (!"AVAILABLE".equalsIgnoreCase(copy.getStatus())) {
            throw new RuntimeException("Copy is not available");
        }

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Меняем статус экземпляра на BORROWED
        copy.setStatus("BORROWED");
        copyRepository.save(copy);

        Issue issue = Issue.builder()
                .copy(copy)
                .user(user)
                .issueDate(LocalDate.now())
                .isReturned(false)
                .build();

        return issueRepository.save(issue);
    }

    public Issue returnBook(Long issueId) {
        Issue issue = issueRepository.findById(issueId)
                .orElseThrow(() -> new RuntimeException("Issue not found"));

        if (issue.getIsReturned()) {
            throw new RuntimeException("Book already returned");
        }
        issue.setIsReturned(true);
        issue.setReturnDate(LocalDate.now());

        // Освобождаем экземпляр
        Copy copy = issue.getCopy();
        copy.setStatus("AVAILABLE");
        copyRepository.save(copy);

        return issueRepository.save(issue);
    }

    public List<Issue> searchIssues(Long userId, Long bookId) {
        return issueRepository.findByUserAndBook(userId, bookId);
    }

    public List<Object[]> getPopularBooks() {
        return issueRepository.findPopularBooks();
    }
}
