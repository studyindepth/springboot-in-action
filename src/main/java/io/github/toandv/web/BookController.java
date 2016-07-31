package io.github.toandv.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import io.github.toandv.domain.Book;
import io.github.toandv.domain.BookRepository;

@RestController
@RequestMapping("/v1/api")
public class BookController {

	private BookRepository bookRepository;

	@Autowired
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@GetMapping(path = "/books")
	public List<Book> readersBooks(@Param("reader") String reader) {
		return bookRepository.findByReader(reader);
	}
}
