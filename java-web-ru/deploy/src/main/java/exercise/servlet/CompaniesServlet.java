package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static exercise.Data.getCompanies;

public class CompaniesServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
            throws IOException, ServletException {

        // BEGIN
        PrintWriter writer = response.getWriter();
        String search = request.getParameter("search");
        Stream<String> stream = Objects.isNull(search) ? getCompanies().stream()
                : getCompanies().stream().filter(it -> it.contains(search));
        List<String> list = stream.collect(Collectors.toList());

        if (list.isEmpty()) {
            writer.println("Companies not found");
        } else {
            list.forEach(writer::println);
        }
        writer.close();
        // END
    }
}
