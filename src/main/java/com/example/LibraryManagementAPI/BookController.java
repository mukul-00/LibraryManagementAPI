package com.example.LibraryManagementAPI;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/books")
public class BookController {

    // In-memory storage
    private Map<Integer, Book> books = new HashMap<>();

    // 1. Add a new book
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {

        if (books.containsKey(book.getId())) {
            return ResponseEntity.status(HttpStatus.CONFLICT).build();
        }

        books.put(book.getId(), book);

        return ResponseEntity.status(HttpStatus.CREATED).body(book);
    }

    // 2. Retrieve all books
    @GetMapping
    public List<Book> getAllBooks() {
        return new ArrayList<>(books.values());
    }

    // 3. Retrieve a book by ID
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Integer id) {

        Book book = books.get(id);

        if (book == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok(book);
    }

    // 4. Update book details
    @PutMapping("/{id}")
    public ResponseEntity<Book> updateBook(
            @PathVariable Integer id,
            @RequestBody Book updatedBook) {

        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        updatedBook.setId(id);

        books.put(id, updatedBook);

        return ResponseEntity.ok(updatedBook);
    }

    // 5. Delete a book by ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Integer id) {

        if (!books.containsKey(id)) {
            return ResponseEntity.notFound().build();
        }

        books.remove(id);

        return ResponseEntity.noContent().build();
    }
}
