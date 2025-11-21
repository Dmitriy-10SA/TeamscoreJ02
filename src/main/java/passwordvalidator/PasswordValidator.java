package passwordvalidator;

/** Проверка пароля на сложность.
 * Пароль должен отвечать следующим требованиям:
 * - не менее 8 символов в длину
 * - содержит строчные, заглавные буквы и цифры
 * - не должен совпадать с именем пользователя
 * - не должен содержать пробельных символов, табуляции и кавычек
 */
public class PasswordValidator {
    /**
     * Проверка валидности пароля
     * @param password пароль
     * @param userName имя пользователя
     * @return возвращает true, если пароль отвечает всем требованиям
     */
    public static boolean isValidPassword(String password, String userName) {
        if (password.length() < 8) {
            return false;
        }
        if (!hasDigits(password)) {
            return false;
        }
        if (!hasLowercase(password)) {
            return false;
        }
        if (!hasUppercase(password)) {
            return false;
        }
        if (password.equals(userName)) {
            return false;
        }
        return !hasSpacesOrQuotes(password);
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
            if (Character.isSpaceChar(symbol)
                    || symbol == '\t' || symbol == '"') {
                return true;
            }
        }
        return false;
    }
}
