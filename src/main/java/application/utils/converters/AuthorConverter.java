/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.utils.converters;

import application.database.models.Author;
import application.modelfx.AuthorFx;

public class AuthorConverter {
    public static Author convertToAuthor(AuthorFx authorFx) {
        Author author = new Author();
        author.setId(authorFx.getId());
        author.setName(authorFx.getName());
        author.setSurname(authorFx.getSurname());
        return author;
    }

    public static AuthorFx convertToAuthorFx(Author author){
        AuthorFx authorFx = new AuthorFx();
        authorFx.setId(author.getId());
        authorFx.setName(author.getName());
        authorFx.setSurname(author.getSurname());
        return authorFx;
    }
}
