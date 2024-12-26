package org.example.library.repository;

import org.example.library.domain.Issue;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface IssueRepository extends JpaRepository<Issue, Long> {

    // JPQL-запрос: добавляем @Param для именованных параметров
    @Query("SELECT i FROM Issue i WHERE (:userId IS NULL OR i.user.id = :userId) AND (:bookId IS NULL OR i.copy.book.id = :bookId)")
    List<Issue> findByUserAndBook(@Param("userId") Long userId, @Param("bookId") Long bookId);

    // Native Query: используем один литерал строки без тройных кавычек
    @Query(value = "SELECT b.title, COUNT(i.id) as issue_count " +
            "FROM issues i " +
            "JOIN copies c ON i.copy_id = c.id " +
            "JOIN books b ON c.book_id = b.id " +
            "GROUP BY b.title " +
            "ORDER BY issue_count DESC",
            nativeQuery = true)
    List<Object[]> findPopularBooks();
}
