package org.example.library.service;

import org.example.library.domain.Book;
import org.example.library.domain.Copy;
import org.example.library.dto.CopyDTO;
import org.example.library.repository.BookRepository;
import org.example.library.repository.CopyRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CopyService {
    private final CopyRepository copyRepository;
    private final BookRepository bookRepository;

    public CopyService(CopyRepository copyRepository, BookRepository bookRepository) {
        this.copyRepository = copyRepository;
        this.bookRepository = bookRepository;
    }

    public List<Copy> getAll() {
        return copyRepository.findAll();
    }

    public Optional<Copy> getById(Long id) {
        return copyRepository.findById(id);
    }

    public Copy createCopy(CopyDTO dto) {
        Book book = bookRepository.findById(dto.getBookId())
                .orElseThrow(() -> new RuntimeException("Book not found"));
        Copy copy = Copy.builder()
                .book(book)
                .inventoryNumber(dto.getInventoryNumber())
                .status(dto.getStatus())
                .build();
        return copyRepository.save(copy);
    }

    public Copy updateCopy(Long id, CopyDTO dto) {
        Copy copy = copyRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Copy not found"));
        if (dto.getBookId() != null) {
            Book book = bookRepository.findById(dto.getBookId())
                    .orElseThrow(() -> new RuntimeException("Book not found"));
            copy.setBook(book);
        }
        if (dto.getInventoryNumber() != null) {
            copy.setInventoryNumber(dto.getInventoryNumber());
        }
        if (dto.getStatus() != null) {
            copy.setStatus(dto.getStatus());
        }
        return copyRepository.save(copy);
    }

    public void deleteCopy(Long id) {
        copyRepository.deleteById(id);
    }
}
