package com.enes.library.controller;

import com.enes.library.entity.BookBorrowing;
import com.enes.library.repository.BookBorrowingRepository;
import com.enes.library.repository.BookRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/borrowings")
@RequiredArgsConstructor
public class BookBorrowingController {

    private final BookBorrowingRepository borrowingRepository;
    private final BookRepository bookRepository;

    @GetMapping
    public List<BookBorrowing> getAllBorrowings() {
        return borrowingRepository.findAll();
    }

    @PostMapping
    public BookBorrowing borrowBook(@RequestBody BookBorrowing borrowing) {
        // borrowingDate boşsa bugünün tarihi ver
        if (borrowing.getBorrowingDate() == null) {
            borrowing.setBorrowingDate(LocalDate.now());
        }

        return borrowingRepository.save(borrowing);
    }

    @PutMapping("/{id}/return")
    public BookBorrowing returnBook(@PathVariable Long id) {
        BookBorrowing borrowing = borrowingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Borrowing not found"));

        borrowing.setReturnDate(LocalDate.now());

        return borrowingRepository.save(borrowing);
    }
}
