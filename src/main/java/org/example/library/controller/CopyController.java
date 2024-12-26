package org.example.library.controller;

import org.example.library.domain.Copy;
import org.example.library.dto.CopyDTO;
import org.example.library.service.CopyService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/copies")
public class CopyController {

    private final CopyService copyService;

    public CopyController(CopyService copyService) {
        this.copyService = copyService;
    }

    @GetMapping
    public List<Copy> getAllCopies() {
        return copyService.getAll();
    }

    @GetMapping("/{id}")
    public Copy getCopyById(@PathVariable Long id) {
        return copyService.getById(id).orElseThrow(() -> new RuntimeException("Copy not found"));
    }

    @PostMapping
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Copy createCopy(@RequestBody CopyDTO dto) {
        return copyService.createCopy(dto);
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public Copy updateCopy(@PathVariable Long id, @RequestBody CopyDTO dto) {
        return copyService.updateCopy(id, dto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('LIBRARIAN')")
    public void deleteCopy(@PathVariable Long id) {
        copyService.deleteCopy(id);
    }
}
