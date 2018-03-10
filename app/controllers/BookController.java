package controllers;

import models.Book;
import play.data.Form;
import play.data.FormFactory;
import play.mvc.*;

import java.util.List;
import java.util.Set;

import static play.mvc.Results.ok;

import views.html.books.*;
import views.html.books.index;

import javax.inject.Inject;


public class BookController extends Controller{

    @Inject
    FormFactory formFactory;

    public Result index(){

        List<Book> allBooks=Book.finder.all();


        return ok(index.render(allBooks));
    }

    public Result create(){

        Form<Book> bookForm=formFactory.form(Book.class);

        return ok(createBook.render(bookForm));
    }

    public Result save(){
        Form<Book> bookForm= formFactory.form(Book.class).bindFromRequest();
        Book book=bookForm.get();

         book.save();
        return redirect(routes.BookController.index());
    }

    public Result edit(Integer id){
        Book book=Book.finder.byId(id);

        if (book==null)
            return notFound("Book not found!");

        Form<Book> bookForm=formFactory.form(Book.class).fill(book);

        return ok(editBook.render(bookForm));
    }

    public Result update(){
        Book book=formFactory.form(Book.class).bindFromRequest().get();
        Book oldBook=Book.finder.byId(book.id);

        if (oldBook==null)
            notFound("Book not found");

        oldBook.title=book.title;
        oldBook.author=book.author;
        oldBook.price=book.price;
        oldBook.update();

        return redirect(routes.BookController.index());
    }

    public Result destroy(Integer id){
        Book book=Book.finder.byId(id);
        if (book==null)
            notFound("Book Not Found");

        book.delete();


        return redirect(routes.BookController.index());
    }

    public Result show(Integer id){
        Book book=Book.finder.byId(id) ;
        if (book==null)
            notFound("Book Not Found");

        return ok(show.render(book));
    }



}
