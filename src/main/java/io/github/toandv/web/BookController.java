package io.github.toandv.web;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import io.github.toandv.domain.Book;
import io.github.toandv.domain.BookRepository;

@RestController
@RequestMapping("/books")
public class BookController {

	private BookRepository bookRepository;

	@Autowired
	public BookController(BookRepository bookRepository) {
		this.bookRepository = bookRepository;
	}

	@PreAuthorize("hasRole('ROLE_ADMIN')")
	@RequestMapping(method = RequestMethod.GET)
	public List<Book> readersBooks(@Param("reader") String reader) {
		return bookRepository.findByReader(reader);
	}
}
