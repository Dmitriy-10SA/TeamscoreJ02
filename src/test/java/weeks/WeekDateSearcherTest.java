package weeks;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.time.DayOfWeek;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class WeekDateSearcherTest {
    @Test
    @DisplayName("getMonday возвращает понедельник для первой недели 2024 года")
    void getMondayReturnsMondayForFirstWeek2024() {
        LocalDate monday = WeekDateSearcher.getMonday(2024, 1);
        assertEquals(DayOfWeek.MONDAY, monday.getDayOfWeek());
        assertEquals(2024, monday.getYear());
    }

    @Test
    @DisplayName("getMonday возвращает понедельник для первой недели 2025 года")
    void getMondayReturnsMondayForFirstWeek2025() {
        LocalDate monday = WeekDateSearcher.getMonday(2025, 1);
        assertEquals(DayOfWeek.MONDAY, monday.getDayOfWeek());
    }

    @Test
    @DisplayName("getMonday возвращает понедельник для последней недели 2024 года")
    void getMondayReturnsMondayForLastWeek2024() {
        LocalDate monday = WeekDateSearcher.getMonday(2024, 52);
        assertEquals(DayOfWeek.MONDAY, monday.getDayOfWeek());
    }

    @ParameterizedTest
    @DisplayName("getMonday возвращает понедельник для различных недель")
    @CsvSource({
            "2024, 1, 2024-01-01",
            "2024, 10, 2024-03-04",
            "2024, 20, 2024-05-13",
            "2024, 30, 2024-07-22",
            "2024, 40, 2024-09-30",
            "2024, 52, 2024-12-23",
            "2025, 1, 2024-12-30",
            "2025, 10, 2025-03-03",
            "2025, 20, 2025-05-12"
    })
    void getMondayReturnsCorrectMonday(int year, int weekNumber, String expectedDate) {
        LocalDate monday = WeekDateSearcher.getMonday(year, weekNumber);
        LocalDate expected = LocalDate.parse(expectedDate);
        assertEquals(expected, monday);
        assertEquals(DayOfWeek.MONDAY, monday.getDayOfWeek());
    }

    @Test
    @DisplayName("getSunday возвращает воскресенье для первой недели 2024 года")
    void getSundayReturnsSundayForFirstWeek2024() {
        LocalDate sunday = WeekDateSearcher.getSunday(2024, 1);
        assertEquals(DayOfWeek.SUNDAY, sunday.getDayOfWeek());
        assertEquals(2024, sunday.getYear());
    }

    @Test
    @DisplayName("getSunday возвращает воскресенье для первой недели 2025 года")
    void getSundayReturnsSundayForFirstWeek2025() {
        LocalDate sunday = WeekDateSearcher.getSunday(2025, 1);
        assertEquals(DayOfWeek.SUNDAY, sunday.getDayOfWeek());
    }

    @ParameterizedTest
    @DisplayName("getSunday возвращает воскресенье для различных недель")
    @CsvSource({
            "2024, 1, 2024-01-07",
            "2024, 10, 2024-03-10",
            "2024, 20, 2024-05-19",
            "2024, 30, 2024-07-28",
            "2024, 40, 2024-10-06",
            "2024, 52, 2024-12-29",
            "2025, 1, 2025-01-05",
            "2025, 10, 2025-03-09",
            "2025, 20, 2025-05-18"
    })
    void getSundayReturnsCorrectSunday(int year, int weekNumber, String expectedDate) {
        LocalDate sunday = WeekDateSearcher.getSunday(year, weekNumber);
        LocalDate expected = LocalDate.parse(expectedDate);
        assertEquals(expected, sunday);
        assertEquals(DayOfWeek.SUNDAY, sunday.getDayOfWeek());
    }

    @ParameterizedTest
    @DisplayName("getSunday всегда на 6 дней позже getMonday для различных недель")
    @CsvSource({
            "2024, 1",
            "2024, 10",
            "2024, 20",
            "2024, 30",
            "2024, 52",
            "2025, 1",
            "2025, 10",
            "2025, 20"
    })
    void getSundayIsAlwaysSixDaysAfterMonday(int year, int weekNumber) {
        LocalDate monday = WeekDateSearcher.getMonday(year, weekNumber);
        LocalDate sunday = WeekDateSearcher.getSunday(year, weekNumber);
        assertEquals(DayOfWeek.MONDAY, monday.getDayOfWeek());
        assertEquals(DayOfWeek.SUNDAY, sunday.getDayOfWeek());
        assertEquals(monday.plusDays(6), sunday);
    }

    @Test
    @DisplayName("Неделя 1 2025 года начинается в 2024 году")
    void firstWeekOf2025StartsIn2024() {
        LocalDate monday = WeekDateSearcher.getMonday(2025, 1);
        LocalDate sunday = WeekDateSearcher.getSunday(2025, 1);
        assertEquals(2024, monday.getYear());
        assertEquals(2025, sunday.getYear());
    }
}