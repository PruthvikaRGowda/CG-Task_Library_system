package CGTask.library_system.service;

import CGTask.library_system.model.Book;
import CGTask.library_system.repositroy.BookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class BookService {
    @Autowired
    private BookRepository bookRepository;

    public List<Book> getAllBooks() {
        return bookRepository.findAll();
    }

    public Book getBookById(Long id) {
        Optional<Book> optionalBook = bookRepository.findById(id);
        return optionalBook.orElse(null); // If not found, return null
    }

    public Book createBook(Book book) {
        return bookRepository.save(book);
    }

    public Book updateBook(Long id, Book updatedBook) {
        Optional<Book> optionalExistingBook = bookRepository.findById(id);
        if (optionalExistingBook.isPresent()) {
            Book existingBook = optionalExistingBook.get();
            // Update book fields
            updateBookFields(existingBook, updatedBook);
            return bookRepository.save(existingBook);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }

    private void updateBookFields(Book existingBook, Book updatedBook) {
        if (updatedBook.getTitle() != null) {
            existingBook.setTitle(updatedBook.getTitle());
        }
        if (updatedBook.getAuthor() != null) {
            existingBook.setAuthor(updatedBook.getAuthor());
        }
        if (updatedBook.getIsbn() != null) {
            existingBook.setIsbn(updatedBook.getIsbn());
        }
        if (updatedBook.getGenre() != null) {
            existingBook.setGenre(updatedBook.getGenre());
        }
        if (updatedBook.getPublicationYear() != 0) {
            existingBook.setPublicationYear(updatedBook.getPublicationYear());
        }
    }

    public void deleteBook(Long id) {
        if (bookRepository.existsById(id)) {
            bookRepository.deleteById(id);
        } else {
            throw new RuntimeException("Book not found with id: " + id);
        }
    }
}
