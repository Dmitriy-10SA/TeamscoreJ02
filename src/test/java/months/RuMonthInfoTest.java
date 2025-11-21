package months;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RuMonthInfoTest {
    @Nested
    @DisplayName("Проверяем инициализацию и верность выдачи номера месяца")
    class InitializationAndNumberMonthTests {
        @Test
        @DisplayName("Текущий месяц, если конструктор без параметров")
        void nowMonthNumberWhenEmptyConstructor() {
            RuMonthInfo info = new RuMonthInfo();
            assertEquals(LocalDate.now().getMonthValue(), info.getNumber());
        }

        @ParameterizedTest
        @ValueSource(ints = {1, 2, 3, 4, 5, 6, 7, 8, 9, 10, 11, 12})
        @DisplayName("Верный номер месяца, если конструктор с датой")
        void correctMonthNumberWhenConstructorWithDate(int month) {
            LocalDate date = LocalDate.of(2025, month, 15);
            RuMonthInfo info = new RuMonthInfo(date);
            assertEquals(month, info.getNumber());
        }
    }

    @ParameterizedTest
    @CsvSource({
            "1, январь",
            "2, февраль",
            "3, март",
            "4, апрель",
            "5, май",
            "6, июнь",
            "7, июль",
            "8, август",
            "9, сентябрь",
            "10, октябрь",
            "11, ноябрь",
            "12, декабрь"
    })
    @DisplayName("Верное название месяца")
    void correctNameForMonth(int month, String expectedName) {
        RuMonthInfo info = new RuMonthInfo(LocalDate.of(2025, month, 15));
        assertEquals(expectedName, info.getName());
    }

    @ParameterizedTest
    @CsvSource({
            "1, ср",
            "2, сб",
            "3, сб",
            "4, вт",
            "5, чт",
            "6, вс",
            "7, вт",
            "8, пт",
            "9, пн",
            "10, ср",
            "11, сб",
            "12, пн"
    })
    @DisplayName("Верное короткое название первого дня месяца")
    void correctFirstDayShortNameForMonth(int month, String expectedShortName) {
        RuMonthInfo info = new RuMonthInfo(LocalDate.of(2025, month, 15));
        assertEquals(expectedShortName, info.getFirstDayShortName());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 31",
            "2, 28",
            "3, 31",
            "4, 30",
            "5, 31",
            "6, 30",
            "7, 31",
            "8, 31",
            "9, 30",
            "10, 31",
            "11, 30",
            "12, 31"
    })
    @DisplayName("Верная дата последнего дня месяца")
    void correctLastDayDateForMonth(int month, int expectedDay) {
        LocalDate date = LocalDate.of(2025, month, 15);
        RuMonthInfo info = new RuMonthInfo(date);
        assertEquals(LocalDate.of(2025, month, expectedDay), info.getLastDayDate());
    }

    @ParameterizedTest
    @CsvSource({
            "1, 31",
            "2, 28",
            "3, 31",
            "4, 30",
            "5, 31",
            "6, 30",
            "7, 31",
            "8, 31",
            "9, 30",
            "10, 31",
            "11, 30",
            "12, 31"
    })
    @DisplayName("Верное количество дней в месяце")
    void correctCountDays(int month, int expectedDays) {
        RuMonthInfo info = new RuMonthInfo(LocalDate.of(2025, month, 15));
        assertEquals(expectedDays, info.getCountDays());
    }

    @ParameterizedTest
    @CsvSource({
            "2025, 1, 2025 Q1",
            "2025, 2, 2025 Q1",
            "2025, 3, 2025 Q1",
            "2025, 4, 2025 Q2",
            "2025, 5, 2025 Q2",
            "2025, 6, 2025 Q2",
            "2025, 7, 2025 Q3",
            "2025, 8, 2025 Q3",
            "2025, 9, 2025 Q3",
            "2025, 10, 2025 Q4",
            "2025, 11, 2025 Q4",
            "2025, 12, 2025 Q4",
            "2026, 1, 2026 Q1",
    })
    @DisplayName("Верный квартал и год")
    void correctQuarterWithYear(int year, int month, String expectedQuarter) {
        RuMonthInfo info = new RuMonthInfo(LocalDate.of(year, month, 15));
        assertEquals(expectedQuarter, info.getQuarterWithYear());
    }

    @Nested
    @DisplayName("Високосный год")
    class LeapYearTests {
        @ParameterizedTest
        @CsvSource({
                "2024-02-01, 2024-02-29",
                "2024-02-28, 2024-02-29",
                "2024-02-29, 2024-02-29"
        })
        @DisplayName("Последний день февраля — 29 февраля")
        void lastDayOfFebruaryIs29th(LocalDate sourceDate, LocalDate expectedLastDay) {
            RuMonthInfo info = new RuMonthInfo(sourceDate);
            assertEquals(expectedLastDay, info.getLastDayDate());
        }

        @ParameterizedTest
        @CsvSource({
                "2024-02-01",
                "2024-02-15",
                "2024-02-29"
        })
        @DisplayName("В феврале високосного года 29 дней")
        void februaryHas29Days(LocalDate sourceDate) {
            RuMonthInfo info = new RuMonthInfo(sourceDate);
            assertEquals(29, info.getCountDays());
        }
    }
}