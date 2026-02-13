package question1.question1_library_api.Controller;

import question1.question1_library_api.Book;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/books")
public class BookController {
    
    private List<Book> books = new ArrayList<>();
    private Long nextId = 4L;

    public BookController() {
        // Initialize with 3 sample books
        books.add(new Book(1L, "Clean Code", "Robert Martin", "978-0132350884", 2008));
        books.add(new Book(2L, "The Pragmatic Programmer", "Andrew Hunt", "978-0135957059", 1999));
        books.add(new Book(3L, "Design Patterns", "Gang of Four", "978-0201633610", 1994));
    }

    /**
     * GET /api/books - Return a list of all books
     */
    @GetMapping
    public ResponseEntity<List<Book>> getAllBooks() {
        return ResponseEntity.ok(books);
    }

    /**
     * GET /api/books/search?title={title} - Search books by title
     * Note: This must come BEFORE /{id} to avoid conflicts
     */
    @GetMapping("/search")
    public ResponseEntity<List<Book>> searchByTitle(@RequestParam(required = true) String title) {
        List<Book> searchResults = new ArrayList<>();
        for (Book book : books) {
            if (book.getTitle().toLowerCase().contains(title.toLowerCase())) {
                searchResults.add(book);
            }
        }

        if (searchResults.isEmpty()) {
            return ResponseEntity.notFound().build(); // 404
        }
        return ResponseEntity.ok(searchResults); // 200
    }

    /**
     * GET /api/books/{id} - Return a specific book by ID
     */
    @GetMapping("/{id}")
    public ResponseEntity<Book> getBookById(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (book.isPresent()) {
            return ResponseEntity.ok(book.get());
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }

    /**
     * POST /api/books - Add a new book
     */
    @PostMapping
    public ResponseEntity<Book> addBook(@RequestBody Book book) {
        book.setId(nextId++);
        books.add(book);
        return ResponseEntity.status(HttpStatus.CREATED).body(book); // 201
    }

    /**
     * DELETE /api/books/{id} - Delete a book by ID
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteBook(@PathVariable Long id) {
        Optional<Book> book = books.stream()
                .filter(b -> b.getId().equals(id))
                .findFirst();

        if (book.isPresent()) {
            books.remove(book.get());
            return ResponseEntity.noContent().build(); // 204
        } else {
            return ResponseEntity.notFound().build(); // 404
        }
    }
}
