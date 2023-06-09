package exercise.servlet;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.List;
import java.util.Map;

import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.Files;
import java.util.Optional;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.core.JsonProcessingException;
import org.apache.commons.lang3.ArrayUtils;

public class UsersServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request,
                      HttpServletResponse response)
                throws IOException, ServletException {

        String pathInfo = request.getPathInfo();

        if (pathInfo == null) {
            showUsers(request, response);
            return;
        }

        String[] pathParts = pathInfo.split("/");
        String id = ArrayUtils.get(pathParts, 1, "");

        showUser(request, response, id);
    }

    private List getUsers() throws JsonProcessingException, IOException {
        // BEGIN
        ObjectMapper objectMapper = new ObjectMapper();
        List users = objectMapper.readValue(
                Files.newInputStream(Paths.get("src/main/resources/users.json")), List.class);
        return users;
        // END
    }

    private void showUsers(HttpServletRequest request,
                          HttpServletResponse response)
                throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        PrintWriter printWriter = response.getWriter();
        printWriter.append("<html>" +
                "<head>" +
                "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">" +
                "</head>" +
                "<table>");
        users.forEach(user -> {
            printWriter.append("<tr>" +
                    "<td>"+user.get("id")+"</td>" +
                    "<td>" +
                    "<a href=\"/users/"+user.get("id")+"\">"+user.get("firstName")+" "+user.get("lastName")+"</a>" +
                    "</td>" +
                    "</tr>");
        });
        printWriter.append("</table>");
        printWriter.append("</html>");
        printWriter.close();
        // END
    }

    private void showUser(HttpServletRequest request,
                         HttpServletResponse response,
                         String id)
                 throws IOException {

        // BEGIN
        List<Map<String, String>> users = getUsers();
        Optional<Map<String,String>> user = users.stream().filter(u -> u.get("id").equals(id)).findAny();
        if (user.isEmpty()) {
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            PrintWriter printWriter = response.getWriter();
            printWriter.append("<html>" +
                    "<head>" +
                    "<link href=\"https://cdn.jsdelivr.net/npm/bootstrap@5.1.3/dist/css/bootstrap.min.css\" rel=\"stylesheet\" integrity=\"sha384-1BmE4kWBq78iYhFldvKuhfTAU6auU8tT94WrHftjDbrCEXSU1oBoqyl2QvZ6jIW3\" crossorigin=\"anonymous\">" +
                    "</head>" +
                    "<table>");
            user.get().entrySet().forEach(field -> {
                printWriter.append("<tr>" +
                        "<td>"+field.getKey()+"</td>" +
                        "<td>"+field.getValue()+"</td>" +
                        "</tr>");
            });
            printWriter.append("</table>");
            printWriter.append("</html>");
            printWriter.close();

        }
        // END
    }
}
