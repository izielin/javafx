/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.utils.converters;

import application.database.models.Book;
import application.modelfx.BookFx;
import application.utils.Utils;

public class BookConverter {
    public static Book convertToBook(BookFx bookFx) {
        Book book = new Book();
        book.setId(bookFx.getId());
        book.setTitle(bookFx.getTitle());
        book.setDescription(bookFx.getDescription());
        book.setRating(bookFx.getRating());
        book.setIsbn(bookFx.getIsbn());
        book.setReleaseDate(Utils.convertToDate(bookFx.getReleaseDate()));
        book.setAddedDate(Utils.convertToDate(bookFx.getAddedDate()));
        return book;
    }
}
