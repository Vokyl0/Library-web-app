package com.myname;

import com.myname.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/search-page"})
public class SearchServlet  extends HttpServlet {
    public SearchServlet(){
        super();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String title = req.getParameter("title");
        String author = req.getParameter("author");
        String minPrice = req.getParameter("min-price");
        String maxPrice = req.getParameter("max-price");
        String releaseYear = req.getParameter("release-year");
        String genre = req.getParameter("genre");
        List<Book> books = search(title, author, minPrice, maxPrice, releaseYear, genre);
        System.out.println(books);
        req.setAttribute("books", books);
        getServletContext().getRequestDispatcher("/books-list.jsp").forward(req, resp);

    }
    private List<Book> search(String title, String author, String minPrice, String maxPrice,
                                     String releaseYear, String genre) {
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            CriteriaBuilder criteriaBuilder = session.getCriteriaBuilder();
            CriteriaQuery<Book> criteriaQuery = criteriaBuilder.createQuery(Book.class);
            Root<Book> root = criteriaQuery.from(Book.class);
            List<Predicate> predicates = new ArrayList<>();
            if (title != null && !title.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("title"), title));
            }
            System.out.println(author + " " + root.get("author"));
            if (author != null && !author.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("author"), author));
            }
            System.out.println(minPrice + " " + root.get("price"));
            if (minPrice != null && !minPrice.isEmpty()) {
                predicates.add(criteriaBuilder.greaterThanOrEqualTo(root.get("price"), Double.parseDouble(minPrice)));
            }
            System.out.println(maxPrice + " " + root.get("price"));
            if (maxPrice != null && !maxPrice.isEmpty()) {
                predicates.add(criteriaBuilder.lessThanOrEqualTo(root.get("price"), Double.parseDouble(maxPrice)));
            }
            System.out.println(releaseYear + " " + root.get("releaseYear"));
            if (releaseYear != null && !releaseYear.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("releaseYear"), releaseYear));
            }
            System.out.println(genre + " " + root.get("genre"));
            if (genre != null && !genre.equals("Any") && !genre.isEmpty()) {
                predicates.add(criteriaBuilder.equal(root.get("genre"), genre));
            }
            criteriaQuery.select(root).where(predicates.toArray(new Predicate[0]));
            Query<Book> query = session.createQuery(criteriaQuery);
            return query.getResultList();
        }
    }
}
