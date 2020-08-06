/*
 * Copyright (c) 2020. Michał Parzych
 * All rights reserved
 */

package application.utils.converters;

import application.database.models.Author;
import application.modelfx.AuthorFx;

public class AuthorConverter {
    public static Author convertAuthorFxTOAuthor(AuthorFx authorFx) {
        Author author = new Author();
        author.setName(authorFx.getName());
        author.setSurname(authorFx.getSurname());
        return author;
    }
}
