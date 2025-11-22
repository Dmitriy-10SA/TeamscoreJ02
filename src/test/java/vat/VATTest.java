package vat;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

import static org.junit.jupiter.api.Assertions.*;

class VATTest {
    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 1200")
    void getVATAmountsByPriceWithVAT_1200() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("1200"));
        assertEquals(new BigDecimal("1200.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("1000.00"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("200.00"), amounts.vatInReceipt());
        assertEquals(new BigInteger("200"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 500_000.00")
    void getVATAmountsByPriceWithVAT_500000() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("500000.00"));
        assertEquals(new BigDecimal("500000.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("416666.67"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("83333.33"), amounts.vatInReceipt());
        assertEquals(new BigInteger("83333"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 119.99")
    void getVATAmountsByPriceWithVAT_119_99() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("119.99"));
        assertEquals(new BigDecimal("119.99"), amounts.priceWithVat());
        assertEquals(new BigDecimal("99.99"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("20.00"), amounts.vatInReceipt());
        assertEquals(new BigInteger("20"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 99.99")
    void getVATAmountsByPriceWithVAT_99_99() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("99.99"));
        assertEquals(new BigDecimal("99.99"), amounts.priceWithVat());
        assertEquals(new BigDecimal("83.32"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("16.67"), amounts.vatInReceipt());
        assertEquals(new BigInteger("17"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 92.4")
    void getVATAmountsByPriceWithVAT_92_4() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("92.4"));
        assertEquals(new BigDecimal("92.40"), amounts.priceWithVat());
        assertEquals(new BigDecimal("77.00"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("15.40"), amounts.vatInReceipt());
        assertEquals(new BigInteger("15"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 183")
    void getVATAmountsByPriceWithVAT_183() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("183"));
        assertEquals(new BigDecimal("183.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("152.50"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("30.50"), amounts.vatInReceipt());
        assertEquals(new BigInteger("31"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 5.94")
    void getVATAmountsByPriceWithVAT_5_94() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("5.94"));
        assertEquals(new BigDecimal("5.94"), amounts.priceWithVat());
        assertEquals(new BigDecimal("4.95"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.99"), amounts.vatInReceipt());
        assertEquals(new BigInteger("1"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 0.06")
    void getVATAmountsByPriceWithVAT_0_06() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("0.06"));
        assertEquals(new BigDecimal("0.06"), amounts.priceWithVat());
        assertEquals(new BigDecimal("0.05"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.01"), amounts.vatInReceipt());
        assertEquals(new BigInteger("0"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 0.01")
    void getVATAmountsByPriceWithVAT_0_01() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("0.01"));
        assertEquals(new BigDecimal("0.01"), amounts.priceWithVat());
        assertEquals(new BigDecimal("0.01"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.00"), amounts.vatInReceipt());
        assertEquals(new BigInteger("0"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT вычисляет правильные суммы для 0")
    void getVATAmountsByPriceWithVAT_0() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("0"));
        assertEquals(new BigDecimal("0.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("0.00"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.00"), amounts.vatInReceipt());
        assertEquals(new BigInteger("0"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT выбрасывает исключение для отрицательной суммы")
    void getVATAmountsByPriceWithVAT_negative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VAT.getVATAmountsByPriceWithVAT(new BigDecimal("-0.01"))
        );
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithVAT выбрасывает исключение для null")
    void getVATAmountsByPriceWithVAT_null() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VAT.getVATAmountsByPriceWithVAT(null)
        );
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 1000")
    void getVATAmountsByPriceWithoutVAT_1000() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("1000"));
        assertEquals(new BigDecimal("1200.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("1000.00"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("200.00"), amounts.vatInReceipt());
        assertEquals(new BigInteger("200"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 416666.67")
    void getVATAmountsByPriceWithoutVAT_416666_67() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("416666.67"));
        assertEquals(new BigDecimal("500000.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("416666.67"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("83333.33"), amounts.vatInReceipt());
        assertEquals(new BigInteger("83333"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 99.99")
    void getVATAmountsByPriceWithoutVAT_99_99() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("99.99"));
        assertEquals(new BigDecimal("119.99"), amounts.priceWithVat());
        assertEquals(new BigDecimal("99.99"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("20.00"), amounts.vatInReceipt());
        assertEquals(new BigInteger("20"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 83.32")
    void getVATAmountsByPriceWithoutVAT_83_32() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("83.32"));
        assertEquals(new BigDecimal("99.98"), amounts.priceWithVat());
        assertEquals(new BigDecimal("83.32"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("16.66"), amounts.vatInReceipt());
        assertEquals(new BigInteger("17"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 77")
    void getVATAmountsByPriceWithoutVAT_77() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("77"));
        assertEquals(new BigDecimal("92.40"), amounts.priceWithVat());
        assertEquals(new BigDecimal("77.00"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("15.40"), amounts.vatInReceipt());
        assertEquals(new BigInteger("15"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 152.5")
    void getVATAmountsByPriceWithoutVAT_152_5() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("152.5"));
        assertEquals(new BigDecimal("183.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("152.50"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("30.50"), amounts.vatInReceipt());
        assertEquals(new BigInteger("31"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 4.95")
    void getVATAmountsByPriceWithoutVAT_4_95() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("4.95"));
        assertEquals(new BigDecimal("5.94"), amounts.priceWithVat());
        assertEquals(new BigDecimal("4.95"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.99"), amounts.vatInReceipt());
        assertEquals(new BigInteger("1"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 0.05")
    void getVATAmountsByPriceWithoutVAT_0_05() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("0.05"));
        assertEquals(new BigDecimal("0.06"), amounts.priceWithVat());
        assertEquals(new BigDecimal("0.05"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.01"), amounts.vatInReceipt());
        assertEquals(new BigInteger("0"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT вычисляет правильные суммы для 0")
    void getVATAmountsByPriceWithoutVAT_0() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("0"));
        assertEquals(new BigDecimal("0.00"), amounts.priceWithVat());
        assertEquals(new BigDecimal("0.00"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.00"), amounts.vatInReceipt());
        assertEquals(new BigInteger("0"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT выбрасывает исключение для отрицательной суммы")
    void getVATAmountsByPriceWithoutVAT_negative() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("-1"))
        );
    }

    @Test
    @DisplayName("getVATAmountsByPriceWithoutVAT выбрасывает исключение для -0.01")
    void getVATAmountsByPriceWithoutVAT_negativeSmall() {
        assertThrows(
                IllegalArgumentException.class,
                () -> VAT.getVATAmountsByPriceWithoutVAT(new BigDecimal("-0.01"))
        );
    }

    @Test
    @DisplayName("НДС для декларации округляется: менее 0.5 отбрасывается")
    void vatForDeclaration_roundsDown() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("0.59"));
        assertEquals(new BigInteger("0"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("НДС для декларации округляется: 0.5 и более округляется вверх")
    void vatForDeclaration_roundsUp() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("99.99"));
        assertEquals(new BigInteger("17"), amounts.vatForDeclaration());
        VAT.VATAmounts amounts2 = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("183"));
        assertEquals(new BigInteger("31"), amounts2.vatForDeclaration());
    }

    @Test
    @DisplayName("НДС в чеке округляется до копеек")
    void vatInReceipt_roundsToCents() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("100.01"));
        assertEquals(2, amounts.vatInReceipt().scale());
    }

    @Test
    @DisplayName("Оба метода дают одинаковый результат для эквивалентных сумм")
    void bothMethodsGiveSameResult() {
        BigDecimal priceWithVAT = new BigDecimal("1200.00");
        VAT.VATAmounts amounts1 = VAT.getVATAmountsByPriceWithVAT(priceWithVAT);
        VAT.VATAmounts amounts2 = VAT.getVATAmountsByPriceWithoutVAT(amounts1.priceWithoutVat());
        assertEquals(amounts1.priceWithVat(), amounts2.priceWithVat());
        assertEquals(amounts1.priceWithoutVat(), amounts2.priceWithoutVat());
        assertEquals(amounts1.vatInReceipt(), amounts2.vatInReceipt());
        assertEquals(amounts1.vatForDeclaration(), amounts2.vatForDeclaration());
    }

    @ParameterizedTest
    @DisplayName("НДС для декларации: суммы менее 0.5 округляются вниз")
    @CsvSource({
            "0.06, 0",
            "0.59, 0",
            "5.94, 1",
            "92.4, 15"
    })
    void vatForDeclarationRounding(String priceWithVAT, String expectedVAT) {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal(priceWithVAT));
        assertEquals(new BigInteger(expectedVAT), amounts.vatForDeclaration());
    }

    @ParameterizedTest
    @DisplayName("НДС для декларации: суммы 0.5 и более округляются вверх")
    @CsvSource({
            "99.99, 17",
            "183, 31"
    })
    void vatForDeclarationRoundingUp(String priceWithVAT, String expectedVAT) {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal(priceWithVAT));
        assertEquals(new BigInteger(expectedVAT), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("Проверка всех полей VATAmounts для больших сумм")
    void largeAmounts() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("1000000000.00"));
        assertNotNull(amounts.priceWithVat());
        assertNotNull(amounts.priceWithoutVat());
        assertNotNull(amounts.vatInReceipt());
        assertNotNull(amounts.vatForDeclaration());
        assertTrue(amounts.priceWithVat().compareTo(amounts.priceWithoutVat()) > 0);
        assertTrue(amounts.vatInReceipt().compareTo(BigDecimal.ZERO) >= 0);
        assertTrue(amounts.vatForDeclaration().compareTo(BigInteger.ZERO) >= 0);
    }

    @Test
    @DisplayName("Проверка точности вычислений для малых сумм")
    void smallAmounts() {
        VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(new BigDecimal("0.12"));
        assertEquals(new BigDecimal("0.12"), amounts.priceWithVat());
        assertEquals(new BigDecimal("0.10"), amounts.priceWithoutVat());
        assertEquals(new BigDecimal("0.02"), amounts.vatInReceipt());
        assertEquals(new BigInteger("0"), amounts.vatForDeclaration());
    }

    @Test
    @DisplayName("Проверка что priceWithVat всегда больше priceWithoutVat для положительных сумм")
    void priceWithVatGreaterThanPriceWithoutVat() {
        BigDecimal[] testValues = {
                new BigDecimal("1"),
                new BigDecimal("10"),
                new BigDecimal("100"),
                new BigDecimal("1000"),
                new BigDecimal("0.01"),
                new BigDecimal("0.12")
        };

        for (BigDecimal value : testValues) {
            VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(value);
            assertTrue(amounts.priceWithVat().compareTo(amounts.priceWithoutVat()) >= 0);
        }
    }

    @Test
    @DisplayName("Проверка что vatInReceipt = priceWithVat - priceWithoutVat")
    void vatInReceiptEqualsDifference() {
        BigDecimal[] testValues = {
                new BigDecimal("1200"),
                new BigDecimal("500000.00"),
                new BigDecimal("119.99"),
                new BigDecimal("99.99"),
                new BigDecimal("92.4"),
                new BigDecimal("183")
        };
        for (BigDecimal value : testValues) {
            VAT.VATAmounts amounts = VAT.getVATAmountsByPriceWithVAT(value);
            BigDecimal expectedVAT = amounts.priceWithVat().subtract(amounts.priceWithoutVat());
            assertEquals(
                    expectedVAT.setScale(2, RoundingMode.HALF_UP),
                    amounts.vatInReceipt()
            );
        }
    }
}

