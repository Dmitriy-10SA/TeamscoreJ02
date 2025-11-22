package weeks;

import java.time.LocalDate;
import java.time.temporal.WeekFields;

/**
 * Класс для поиска дат
 */
public class WeekDateSearcher {
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
