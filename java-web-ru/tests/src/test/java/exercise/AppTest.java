package exercise;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

import kong.unirest.HttpResponse;
import kong.unirest.Unirest;
import io.javalin.Javalin;
import io.ebean.DB;

import exercise.domain.User;
import exercise.domain.query.QUser;
import io.ebean.Database;

class AppTest {

    private static Javalin app;
    private static String baseUrl;

    // BEGIN
    @BeforeAll
    static void beforeAll() {
        app = App.getApp();
        app.start(0);
        baseUrl = "http://localhost:" + app.port();
    }

    @AfterAll
    static void afterAll() {
        app.stop();
    }
    // END

    // Между тестами база данных очищается
    // Благодаря этому тесты не влияют друг на друга
    @BeforeEach
    void beforeEach() {
        Database db = DB.getDefault();
        db.truncate("users");
        User existingUser = new User("Wendell", "Legros", "a@a.com", "123456");
        existingUser.save();
    }

    @Test
    void testUsers() {

        // Выполняем GET запрос на адрес http://localhost:port/users
        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users")
            .asString();
        // Получаем тело ответа
        String content = response.getBody();

        // Проверяем код ответа
        assertThat(response.getStatus()).isEqualTo(200);
        // Проверяем, что страница содержит определенный текст
        assertThat(response.getBody()).contains("Wendell Legros");
    }

    @Test
    void testNewUser() {

        HttpResponse<String> response = Unirest
            .get(baseUrl + "/users/new")
            .asString();

        assertThat(response.getStatus()).isEqualTo(200);
    }

    // BEGIN
    @Test
    void testCreateUserSuccess() {
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName","Ivan")
                .field("lastName","Ivanov")
                .field("email","ivan@mail.ru")
                .field("password","12345")
                .asString();

        assertThat(response.getStatus()).isEqualTo(302);

        User user = new QUser()
                .firstName.equalTo("Ivan")
                .lastName.equalTo("Ivanov")
                .email.equalTo("ivan@mail.ru")
                .password.equalTo("12345")
                .findOne();
        assertThat(user).isNotNull();
    }

    @Test
    void testCreateUserValidFail() {
        HttpResponse<String> response = Unirest
                .post(baseUrl + "/users")
                .field("firstName","")
                .field("lastName","")
                .field("email","mail.ru")
                .field("password","123")
                .asString();

        assertThat(response.getStatus()).isEqualTo(422);

        assertThat(response.getBody()).contains("Имя не должно быть пустым");
        assertThat(response.getBody()).contains("Фамилия не должна быть пустой");
        assertThat(response.getBody()).contains("Должно быть валидным email");
        assertThat(response.getBody()).contains("Пароль должен содержать не менее 4 символов");
    }



    // END
}
