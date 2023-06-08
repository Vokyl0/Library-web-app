package com.myname;

import com.myname.entity.Book;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.query.Query;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Map;

@WebServlet(urlPatterns = {"/add-page"})
@MultipartConfig
public class AddServlet extends HttpServlet {
    public AddServlet() {
        super();
    }
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.getWriter().write("Method doget\n");
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Map<String, String[]> params = req.getParameterMap();
        try (SessionFactory sessionFactory = new Configuration().configure().buildSessionFactory();
             Session session = sessionFactory.openSession()) {
            session.beginTransaction();
            InputStream inputStream = req.getPart("content").getInputStream();
            StringBuilder contentBuilder = new StringBuilder();
            try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    contentBuilder.append(line).append("\n");
                }
            }
            int maxId;
            Query<Integer> query = session.createQuery("SELECT MAX(b.id) FROM Book b", Integer.class);
            if (query == null){
                maxId = 0;
            }else {
                maxId = query.getSingleResult();
            }
            Book book = Book.builder()
                    .id(++maxId)
                    .title(params.get("title")[0])
                    .author(params.get("author")[0])
                    .price(Double.parseDouble(params.get("price")[0]))
                    .genre(params.get("genre")[0])
                    .content(contentBuilder.toString())
                    .releaseYear(Integer.parseInt(params.get("release-year")[0]))
                    .build();

            session.save(book);
            session.getTransaction().commit();
            resp.sendRedirect("/my-app/static/successfully-added.html");
        } catch (Exception e) {
            resp.sendRedirect("/my-app/static/successfully-added.html");
            e.printStackTrace();
            System.out.println(params);
        }
    }

}
