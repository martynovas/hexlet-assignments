package exercise.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.ServletContext;

import java.util.Map;
import java.util.HashMap;
import java.util.List;
import java.util.ArrayList;

import org.apache.commons.lang3.ArrayUtils;

import exercise.TemplateEngineUtil;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.PreparedStatement;
import java.util.Objects;
import java.util.Optional;


public class ArticlesServlet extends HttpServlet {

    private String getId(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return null;
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 1, null);
    }

    private String getAction(HttpServletRequest request) {
        String pathInfo = request.getPathInfo();
        if (pathInfo == null) {
            return "list";
        }
        String[] pathParts = pathInfo.split("/");
        return ArrayUtils.get(pathParts, 2, getId(request));
    }

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        String action = getAction(request);

        switch (action) {
            case "list":
                showArticles(request, response);
                break;
            default:
                showArticle(request, response);
                break;
        }
    }

    private void showArticles(HttpServletRequest request,
                              HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN
        int page = Integer.parseInt(Optional.ofNullable(request.getParameter("page")).orElseGet(() -> "1"));
        int offset = (page - 1) * 10;
        int limit = 10;

        List<Map<String, String>> articles = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "select * from articles order by id limit ? offset ? ");
            statement.setInt(1,limit);
            statement.setInt(2,offset);
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                articles.add(
                        Map.of(
                                "id", rs.getString("id"),
                                "title", rs.getString("title"),
                                "body", rs.getString("body")));
            }
            rs.close();
        } catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }

        request.setAttribute("articles", articles);
        request.setAttribute("prevPage", page-1);
        request.setAttribute("nextPage", page+1);
        // END
        TemplateEngineUtil.render("articles/index.html", request, response);
    }

    private void showArticle(HttpServletRequest request,
                             HttpServletResponse response)
            throws IOException, ServletException {

        ServletContext context = request.getServletContext();
        Connection connection = (Connection) context.getAttribute("dbConnection");
        // BEGIN
        try {
            PreparedStatement statement = connection.prepareStatement("select * from articles where id = ?");
            statement.setString(1,getId(request));
            ResultSet rs = statement.executeQuery();
            while (rs.next()) {
                request.setAttribute("id",rs.getString("id"));
                request.setAttribute("title",rs.getString("title"));
                request.setAttribute("body",rs.getString("body"));
            }
            rs.close();
        }catch (Exception e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR);
            return;
        }
        if (Objects.isNull(request.getAttribute("id"))) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
            return;
        }
        // END
        TemplateEngineUtil.render("articles/show.html", request, response);
    }
}
