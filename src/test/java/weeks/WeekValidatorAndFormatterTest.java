package weeks;

import common.ValidationResult;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.WeekFields;

import static org.junit.jupiter.api.Assertions.*;

class WeekValidatorAndFormatterTest {
    @Test
    @DisplayName("validateYearAndNumberOfWeek возвращает успех для валидных данных")
    void validateYearAndNumberOfWeekReturnsSuccessForValidData() {
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(2024, 10);
        assertTrue(result.isValid());
        assertTrue(result.errors().isEmpty());
    }

    @Test
    @DisplayName("validateYearAndNumberOfWeek возвращает ошибку для года меньше 1")
    void validateYearAndNumberOfWeekReturnsErrorForYearLessThanOne() {
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(0, 1);
        assertFalse(result.isValid());
        assertEquals(1, result.errors().size());
        assertEquals("Год должен быть в диапазоне [1, 9999]", result.errors().get(0));
    }

    @Test
    @DisplayName("validateYearAndNumberOfWeek возвращает ошибку для года больше 9999")
    void validateYearAndNumberOfWeekReturnsErrorForYearGreaterThan9999() {
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(10000, 1);
        assertFalse(result.isValid());
        assertEquals(1, result.errors().size());
        assertEquals("Год должен быть в диапазоне [1, 9999]", result.errors().get(0));
    }

    @ParameterizedTest
    @DisplayName("validateYearAndNumberOfWeek принимает граничные значения года")
    @ValueSource(ints = {1, 9999})
    void validateYearAndNumberOfWeekAcceptsBoundaryYears(int year) {
        int maxWeeks = LocalDate.of(year, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(year, maxWeeks);
        assertTrue(result.isValid());
    }

    @Test
    @DisplayName("validateYearAndNumberOfWeek возвращает ошибку для номера недели меньше 1")
    void validateYearAndNumberOfWeekReturnsErrorForWeekNumberLessThanOne() {
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(2024, 0);
        assertFalse(result.isValid());
        assertEquals(1, result.errors().size());
        assertTrue(result.errors().get(0).contains("Номер недели должен быть в диапазоне"));
    }

    @Test
    @DisplayName("validateYearAndNumberOfWeek возвращает ошибку для номера недели больше максимального")
    void validateYearAndNumberOfWeekReturnsErrorForWeekNumberGreaterThanMax() {
        int maxWeeks = LocalDate.of(2024, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(
                2024,
                maxWeeks + 1
        );
        assertFalse(result.isValid());
        assertEquals(1, result.errors().size());
        assertTrue(result.errors().get(0).contains("Номер недели должен быть в диапазоне"));
    }

    @Test
    @DisplayName("validateYearAndNumberOfWeek принимает граничные значения номера недели")
    void validateYearAndNumberOfWeekAcceptsBoundaryWeekNumbers() {
        int maxWeeks = LocalDate.of(2024, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        ValidationResult result1 = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(2024, 1);
        ValidationResult result2 = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(2024, maxWeeks);
        assertTrue(result1.isValid());
        assertTrue(result2.isValid());
    }

    @Test
    @DisplayName("validateYearAndNumberOfWeek возвращает обе ошибки при невалидном годе и номере недели")
    void validateYearAndNumberOfWeekReturnsBothErrors() {
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(0, 0);
        assertFalse(result.isValid());
        assertEquals(2, result.errors().size());
        assertEquals("Год должен быть в диапазоне [1, 9999]", result.errors().get(0));
        assertTrue(result.errors().get(1).contains("Номер недели должен быть в диапазоне"));
    }

    @ParameterizedTest
    @DisplayName("validateYearAndNumberOfWeek валидирует разные годы")
    @CsvSource({
            "2024, 1",
            "2024, 52",
            "2025, 1",
            "2025, 53",
            "2000, 1",
            "1, 52",
            "9999, 52"
    })
    void validateYearAndNumberOfWeekValidatesDifferentYears(int year, int weekNumber) {
        int maxWeeks = LocalDate.of(year, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        ValidationResult result = WeekValidatorAndFormatter.validateYearAndNumberOfWeek(year, weekNumber);
        if (weekNumber <= maxWeeks) {
            assertTrue(result.isValid());
        } else {
            assertFalse(result.isValid());
        }
    }

    @Test
    @DisplayName("formatWeekRange форматирует диапазон дат для валидных данных")
    void formatWeekRangeFormatsDateRangeForValidData() {
        String result = WeekValidatorAndFormatter.formatWeekRange(2024, 10);
        assertNotNull(result);
        assertTrue(result.contains(" – "));
        String[] dates = result.split(" – ");
        assertEquals(2, dates.length);
        assertEquals(DayOfWeek.MONDAY, LocalDate.parse(dates[0]).getDayOfWeek());
        assertEquals(DayOfWeek.SUNDAY, LocalDate.parse(dates[1]).getDayOfWeek());
    }

    @Test
    @DisplayName("formatWeekRange возвращает правильный формат для первой недели")
    void formatWeekRangeReturnsCorrectFormatForFirstWeek() {
        String result = WeekValidatorAndFormatter.formatWeekRange(2024, 1);
        String[] dates = result.split(" – ");
        LocalDate monday = LocalDate.parse(dates[0]);
        LocalDate sunday = LocalDate.parse(dates[1]);
        assertEquals(monday.plusDays(6), sunday);
    }

    @Test
    @DisplayName("formatWeekRange возвращает правильный формат для последней недели")
    void formatWeekRangeReturnsCorrectFormatForLastWeek() {
        int maxWeeks = LocalDate.of(2024, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        String result = WeekValidatorAndFormatter.formatWeekRange(2024, maxWeeks);
        String[] dates = result.split(" – ");
        LocalDate monday = LocalDate.parse(dates[0]);
        LocalDate sunday = LocalDate.parse(dates[1]);
        assertEquals(monday.plusDays(6), sunday);
    }

    @ParameterizedTest
    @DisplayName("formatWeekRange форматирует диапазон для различных недель")
    @CsvSource({
            "2024, 1",
            "2024, 10",
            "2024, 20",
            "2024, 30",
            "2024, 52",
            "2025, 1",
            "2025, 10"
    })
    void formatWeekRangeFormatsRangeForVariousWeeks(int year, int weekNumber) {
        int maxWeeks = LocalDate.of(year, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        if (weekNumber <= maxWeeks) {
            String result = WeekValidatorAndFormatter.formatWeekRange(year, weekNumber);
            String[] dates = result.split(" – ");
            assertEquals(2, dates.length);
            LocalDate monday = LocalDate.parse(dates[0]);
            LocalDate sunday = LocalDate.parse(dates[1]);
            assertEquals(monday.plusDays(6), sunday);
        }
    }

    @Test
    @DisplayName("formatWeekRange выбрасывает исключение для невалидного года")
    void formatWeekRangeThrowsExceptionForInvalidYear() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> WeekValidatorAndFormatter.formatWeekRange(0, 1)
        );
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Год должен быть в диапазоне [1, 9999]"));
    }

    @Test
    @DisplayName("formatWeekRange выбрасывает исключение для невалидного номера недели")
    void formatWeekRangeThrowsExceptionForInvalidWeekNumber() {
        int maxWeeks = LocalDate.of(2024, 12, 28)
                .get(WeekFields.ISO.weekOfWeekBasedYear());
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> WeekValidatorAndFormatter.formatWeekRange(2024, maxWeeks + 1)
        );
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Номер недели должен быть в диапазоне"));
    }

    @Test
    @DisplayName("formatWeekRange выбрасывает исключение для номера недели меньше 1")
    void formatWeekRangeThrowsExceptionForWeekNumberLessThanOne() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> WeekValidatorAndFormatter.formatWeekRange(2024, 0)
        );
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Номер недели должен быть в диапазоне"));
    }

    @Test
    @DisplayName("formatWeekRange выбрасывает исключение для года больше 9999")
    void formatWeekRangeThrowsExceptionForYearGreaterThan9999() {
        IllegalArgumentException exception = assertThrows(
                IllegalArgumentException.class,
                () -> WeekValidatorAndFormatter.formatWeekRange(10000, 1)
        );
        assertNotNull(exception.getMessage());
        assertTrue(exception.getMessage().contains("Год должен быть в диапазоне [1, 9999]"));
    }

    @Test
    @DisplayName("formatWeekRange использует ISO формат дат")
    void formatWeekRangeUsesIsoDateFormat() {
        String result = WeekValidatorAndFormatter.formatWeekRange(2024, 10);
        String[] dates = result.split(" – ");
        DateTimeFormatter isoFormatter = DateTimeFormatter.ISO_LOCAL_DATE;
        assertEquals(dates[0], isoFormatter.format(LocalDate.parse(dates[0])));
        assertEquals(dates[1], isoFormatter.format(LocalDate.parse(dates[1])));
    }

    @Test
    @DisplayName("formatWeekRange для года с 53 неделями (2020)")
    void formatWeekRangeForYearWith53Weeks() {
        String result = WeekValidatorAndFormatter.formatWeekRange(2020, 53);
        assertNotNull(result);
        String[] dates = result.split(" – ");
        LocalDate monday = LocalDate.parse(dates[0]);
        LocalDate sunday = LocalDate.parse(dates[1]);
        assertEquals(monday.plusDays(6), sunday);
    }
}

