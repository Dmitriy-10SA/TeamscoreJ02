package passwordvalidator;

import java.util.ArrayList;
import java.util.List;

/**
 * Проверка пароля на сложность.
 * Пароль должен отвечать следующим требованиям:
 * - не менее 8 символов в длину
 * - содержит строчные, заглавные буквы и цифры
 * - не должен совпадать с именем пользователя
 * - не должен содержать пробельных символов, табуляции и кавычек
 */
public class PasswordValidator {
    /**
     * Проверка валидности пароля
     *
     * @param password пароль
     * @param userName имя пользователя
     * @return возвращает true, если пароль отвечает всем требованиям
     */
    public static boolean isValidPassword(String password, String userName) {
        return validatePassword(password, userName).isValid();
    }

    /**
     * Валидирование пароля и возврат списка ошибок
     *
     * @param password пароль
     * @param userName имя пользователя
     * @return {@link ValidationResult}
     * @see ValidationResult
     */
    public static ValidationResult validatePassword(String password, String userName) {
        List<String> errors = new ArrayList<>();
        if (password.length() < 8) {
            errors.add("Пароль должен содержать не менее 8 символов");
        }
        if (!hasLowercase(password)) {
            errors.add("Пароль должен содержать хотя бы одну строчную букву");
        }
        if (!hasUppercase(password)) {
            errors.add("Пароль должен содержать хотя бы одну заглавную букву");
        }
        if (!hasDigits(password)) {
            errors.add("Пароль должен содержать хотя бы одну цифру");
        }
        if (password.equals(userName)) {
            errors.add("Пароль не должен совпадать с именем пользователя");
        }
        if (hasSpacesOrQuotes(password)) {
            errors.add("Пароль не должен содержать пробелы, табуляцию и кавычки");
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    private static boolean hasDigits(String text) {
        for (char symbol : text.toCharArray()) {
            if (Character.isDigit(symbol)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasLowercase(String text) {
        for (char symbol : text.toCharArray()) {
            if (Character.isLowerCase(symbol)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasUppercase(String text) {
        for (char symbol : text.toCharArray()) {
            if (Character.isUpperCase(symbol)) {
                return true;
            }
        }
        return false;
    }

    private static boolean hasSpacesOrQuotes(String text) {
        for (char symbol : text.toCharArray()) {
            if (Character.isSpaceChar(symbol) || symbol == '\t' || symbol == '"') {
                return true;
            }
        }
        return false;
    }
}
