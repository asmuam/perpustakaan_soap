package com.polstat.perpustakaan.controller;

import com.polstat.soap.gen.GetBooksResponse;
import com.polstat.soap.gen.GetBooksRequest;
import com.polstat.soap.gen.BookDto;
import com.polstat.soap.gen.SearchBooksRequest;
import com.polstat.soap.gen.CreateBookRequest;
import com.polstat.soap.gen.SearchBooksResponse;
import com.polstat.soap.gen.CreateBookResponse;
import com.polstat.perpustakaan.service.BookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ws.server.endpoint.annotation.Endpoint;
import org.springframework.ws.server.endpoint.annotation.PayloadRoot;
import org.springframework.ws.server.endpoint.annotation.RequestPayload;
import org.springframework.ws.server.endpoint.annotation.ResponsePayload;

import java.util.List;

@Endpoint
public class BookEndpoint {

    private static final String NAMESPACE_URI = "http://www.polstat.com/soap/gen";

    @Autowired
    private BookService bookService;

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "CreateBookRequest")
    @ResponsePayload
    public CreateBookResponse createBook(@RequestPayload CreateBookRequest request) {
        // Create a book and return the response
        System.out.println(request.getAuthor());
        BookDto bookDto = BookDto.builder()
                .title(request.getTitle())
                .author(request.getAuthor())
                .description(request.getDescription())
                .build();
        bookService.createBook(bookDto);
        CreateBookResponse response = new CreateBookResponse();
        response.setMessage("Book created successfully");
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "GetBooksRequest")
    @ResponsePayload
    public GetBooksResponse getBooks(@RequestPayload GetBooksRequest request) {
        // Get a list of books and return the response
        GetBooksResponse response = new GetBooksResponse();
        List<BookDto> bookList = bookService.getBooks();
        response.getBook().addAll(bookList);
        return response;
    }

    @PayloadRoot(namespace = NAMESPACE_URI, localPart = "SearchBooksRequest")
    @ResponsePayload
    public SearchBooksResponse searchBooks(@RequestPayload SearchBooksRequest request) {
        // Search for books and return the response
        System.out.println(request.getKeyword());
        SearchBooksResponse response = new SearchBooksResponse();
        List<BookDto> searchResults = bookService.searchBooksByTitleOrAuthor(request.getKeyword());
        response.getBook().addAll(searchResults);
        return response;
    }
}
