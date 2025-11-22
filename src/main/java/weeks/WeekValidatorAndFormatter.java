package weeks;

import common.ValidationResult;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;
import java.util.ArrayList;
import java.util.List;

/**
 * Класс для проверки входных данных (год и номер недели в нумерации ISO) и форматирования диапазона дат
 * Данные валидны, если:
 * - год в диапазоне [1, 1999]
 * - номер недели в диапазоне [1, максимальный номер недели]
 */
public class WeekValidatorAndFormatter {
    private static final DateTimeFormatter ISO_FORMATTER = DateTimeFormatter.ISO_LOCAL_DATE;

    private static ValidationResult validateYearAndNumberOfWeek(int year, int numberOfWeek) {
        List<String> errors = new ArrayList<>();
        if (year < 1 || year > 9999) {
            errors.add("Год должен быть в диапазоне [1, 9999]");
        }
        int maxNumberOfWeeks = LocalDate.of(year, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        if (numberOfWeek < 1 || numberOfWeek > maxNumberOfWeeks) {
            errors.add("Номер недели должен быть в диапазоне [1, " + maxNumberOfWeeks + "]");
        }
        return new ValidationResult(errors.isEmpty(), errors);
    }

    public static String formatWeekRange(int year, int numberOfWeek) {
        ValidationResult validationResult = validateYearAndNumberOfWeek(year, numberOfWeek);
        if (!validationResult.isValid()) {
            return validationResult.errors().toString();
        }
        LocalDate monday = WeekDateSearcher.getMonday(year, numberOfWeek);
        LocalDate sunday = WeekDateSearcher.getSunday(year, numberOfWeek);
        return ISO_FORMATTER.format(monday) + " – " + ISO_FORMATTER.format(sunday);
    }

    /**
     * Класс для поиска дат
     */
    private static class WeekDateSearcher {
        /**
         * Получение даты понедельника
         *
         * @param year         год
         * @param numberOfWeek номер недели в нумерации ISO
         * @return дата понедельника
         */
        public static LocalDate getMonday(int year, int numberOfWeek) {
            return LocalDate.of(year, 1, 4)
                    .with(WeekFields.ISO.weekOfWeekBasedYear(), numberOfWeek)
                    .with(WeekFields.ISO.dayOfWeek(), 1);
        }

        /**
         * Получение даты воскресенья
         *
         * @param year         год
         * @param numberOfWeek номер недели в нумерации ISO
         * @return дата воскресенья
         */
        public static LocalDate getSunday(int year, int numberOfWeek) {
            return getMonday(year, numberOfWeek).plusDays(6);
        }
    }
}
