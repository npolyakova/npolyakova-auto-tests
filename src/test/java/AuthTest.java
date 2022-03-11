import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.params.provider.Arguments.arguments;

public class AuthTest {

    @BeforeEach
    public void setup(){
        //1. Открыть страницу https://github.com/
        open("https://github.com/");
        //2. Кликнуть на кнопку sign in
        TestPages.mainPage.mainSignInButton()
                .click();
    }

    @Test
    @DisplayName("Успешная авторизация")
    public void shouldAuthorizeTest() {
        //3. Заполнить инпуты логина и пароля
        $("[id='login_field']")
                .sendKeys("23rwer");
        $("[id='password']")
                .sendKeys("123");
        //4. Нажать кнопку sign in
        $(".js-sign-in-button")
                .click();
        //5. Проверить авторизацию
        $(".Header")
                .shouldBe(visible);
        //$(byText("Your profile"));
    }

    @MethodSource("incorrectCredentials")
    @ParameterizedTest(name = "{displayName} {0}")
    @DisplayName("Авторизация с некорректными данными:")
    public void shouldNotAuthorizeTest(String type, String phone, String password){
        $("[id='login_field']")
                .sendKeys(phone);
        $("[id='password']")
                .sendKeys(password);
        $(".js-sign-in-button")
                .click();
        $(".flash.flash-full.flash-error")
                .shouldBe(visible);
    }

    static Stream<Arguments> incorrectCredentials() {
        return Stream.of(
                arguments(
                        "зарегистрированный номер телефона и пароль от чужого аккаунта",
                        "9999999999",
                        "123456789Qq"

                ),
                arguments(
                        "незарегистрированный номер телефона и пароль от чужого аккаунта",
                        "9100000000",
                        "123456789Qq"
                )
        );
    }

}
