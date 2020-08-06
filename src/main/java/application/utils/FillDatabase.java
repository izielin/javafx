package application.utils;

import application.database.dao.BookDao;
import application.database.dao.CategoryDao;
import application.database.dbutils.DbManager;
import application.database.models.Author;
import application.database.models.Book;
import application.database.models.Category;
import application.utils.exceptions.ApplicationException;

import java.util.Date;

public class FillDatabase {
    public static  void fillDatabase(){
        Category category1 = new Category();
        category1.setName("Dramat");
        Author author1 = new Author();
        author1.setName("William");
        author1.setSurname("Szekspir");
        Book book1 = new Book();
        book1.setCategory(category1);
        book1.setAuthor(author1);
        book1.setTitle("Makbet");
        book1.setIsbn(8386740418l);
        book1.setRating(4);
        book1.setReleaseDate(new Date());
        book1.setAddedDate(new Date());


        Category category2 = new Category();
        category2.setName("Sensacja");
        CategoryDao categoryDao = new CategoryDao(DbManager.getConnectionSource());
        try {
            categoryDao.createOrUpdate(category2);
            DbManager.closeConnectionSource();
        } catch (ApplicationException e) {
            e.printStackTrace();
        }


        Category category3 = new Category();
        category3.setName("Reportaż");
        Author author2 = new Author();
        author2.setName("Mariusz");
        author2.setSurname("Szczygieł");
        Book book2 = new Book();
        book2.setCategory(category3);
        book2.setAuthor(author2);
        book2.setTitle("Gottland");
        book2.setIsbn(9788380493483l);
        book2.setRating(5);
        book2.setReleaseDate(new Date());
        book2.setAddedDate(new Date());

        Category category4 = new Category();
        category4.setName("Fantastyka");
        Author author3 = new Author();
        author3.setName("John Ronald Reuel");
        author3.setSurname("Tolkien");
        Book book3 = new Book();
        book3.setCategory(category4);
        book3.setAuthor(author3);
        book3.setTitle("Władca Pierścieni");
        book3.setIsbn(9788377582558l);
        book3.setRating(5);
        book3.setReleaseDate(new Date());
        book3.setAddedDate(new Date());

        Author author4 = new Author();
        author4.setName("Terry ");
        author4.setSurname("Pratchett");
        Book book4 = new Book();
        book4.setCategory(category4);
        book4.setAuthor(author3);
        book4.setTitle("Kolor magii");
        book4.setIsbn(9788380493483l);
        book4.setRating(3);
        book4.setReleaseDate(new Date());
        book4.setAddedDate(new Date());

        BookDao bookDao = new BookDao(DbManager.getConnectionSource());
        try {
            bookDao.createOrUpdate(book1);
            bookDao.createOrUpdate(book2);
            bookDao.createOrUpdate(book3);
            bookDao.createOrUpdate(book4);
        } catch (ApplicationException e) {
            e.printStackTrace();
        }
        DbManager.closeConnectionSource();
    }
}
