package passwordvalidator;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PasswordValidatorTest {
    private void testPassword(String password, String username, boolean result) {
        // Act
        boolean validationResult = PasswordValidator.isValidPassword(password, username);

        // Assert
        assertEquals(result, validationResult);
    }

    @ParameterizedTest
    @DisplayName("Valid password is accepted")
    @ValueSource(strings =
            {"Test6789", "1R2g3T4k5Y", "0123456789aA", "_TestUser74"})
    public void validPassword(String password) {
        testPassword(password, "TestUser", true);
    }

    @ParameterizedTest
    @DisplayName("Password is less 8 symbols")
    @ValueSource(strings =
            {"abcdefg", "_ _ _ ", "1234", "1", ""})
    public void tooShortPassword(String password) {
        testPassword(password, "TestUser", false);
    }

    @ParameterizedTest
    @DisplayName("Password has no digits")
    @ValueSource(strings =
            {"asdfghjkl", "__TgTg__", "AbCdEfGhIjKlMn"})
    public void noDigitsPassword(String password) {
        testPassword(password, "TestUser", false);
    }

    @ParameterizedTest
    @DisplayName("Password has no lower case letters")
    @ValueSource(strings =
            {"QWERTYU2", "01234ABCD", "P@$$W0RD"})
    public void noLowerCasePassword(String password) {
        testPassword(password, "TestUser", false);
    }

    @ParameterizedTest
    @DisplayName("Password has no upper case letters")
    @ValueSource(strings =
            {"qwerty1234", ")(*&56nm", "password5"})
    public void noUpperCasePassword(String password) {
        testPassword(password, "TestUser", false);
    }

    @Test
    @DisplayName("Password equals username")
    public void equalsUsernamePassword() {
        String username = "TestUser1";
        testPassword(username, username, false);
    }

    @ParameterizedTest
    @DisplayName("Password has spaces or quotes")
    @ValueSource(strings =
            {"qwerty 1234", "_&*^\tBg79", "\"34jdfgER", " \t\r\n\"Rg6*"})
    public void hasSpacesQuotesPassword(String password) {
        testPassword(password, "TestUser", false);
    }
}
