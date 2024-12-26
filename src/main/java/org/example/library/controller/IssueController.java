package org.example.library.controller;

import org.example.library.domain.Issue;
import org.example.library.service.IssueService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/issues")
public class IssueController {

    private final IssueService issueService;

    public IssueController(IssueService issueService) {
        this.issueService = issueService;
    }

    @GetMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<Issue> getAllIssues() {
        return issueService.getAllIssues();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Issue getIssueById(@PathVariable Long id) {
        return issueService.getIssueById(id).orElseThrow(() -> new RuntimeException("Issue not found"));
    }

    @PostMapping("/issue")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Issue issueBook(@RequestParam Long copyId, @RequestParam Long userId) {
        return issueService.issueBookToUser(copyId, userId);
    }

    @PostMapping("/return")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Issue returnBook(@RequestParam Long issueId) {
        return issueService.returnBook(issueId);
    }

    @GetMapping("/search")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<Issue> searchIssues(@RequestParam(required = false) Long userId,
                                    @RequestParam(required = false) Long bookId) {
        return issueService.searchIssues(userId, bookId);
    }

    @GetMapping("/popular")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public List<Object[]> getPopularBooks() {
        return issueService.getPopularBooks();
    }
}
