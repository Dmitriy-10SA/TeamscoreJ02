package months;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.TextStyle;
import java.util.Locale;

/**
 * Класс для вывода информации о месяце заданной даты (русская локаль)
 * При пустом конструкторе используется текущая дата
 */
public class RuMonthInfo {
    private static final Locale LOCALE = new Locale("ru", "RU");

    private final LocalDate date;

    public RuMonthInfo() {
        this(LocalDate.now());
    }

    public RuMonthInfo(LocalDate date) {
        this.date = date;
    }

    public String getName() {
        return date.getMonth().getDisplayName(TextStyle.FULL_STANDALONE, LOCALE);
    }

    public int getNumber() {
        return date.getMonthValue();
    }

    public String getFirstDayShortName() {
        return date.withDayOfMonth(1).getDayOfWeek().getDisplayName(TextStyle.SHORT_STANDALONE, LOCALE);
    }

    public LocalDate getLastDayDate() {
        return YearMonth.from(date).atEndOfMonth();
    }

    public int getCountDays() {
        return YearMonth.from(date).lengthOfMonth();
    }

    public String getQuarterWithYear() {
        int quarter = (date.getMonthValue() - 1) / 3 + 1;
        return date.getYear() + " Q" + quarter;
    }
}
