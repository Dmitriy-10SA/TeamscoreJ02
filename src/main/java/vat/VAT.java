package vat;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.math.RoundingMode;

/**
 * Класс НДС, передав стоимость товара с НДС (или без), можно получить:
 * - полную стоимость с НДС
 * - стоимость товара без НДС
 * - сумму НДС в чеке
 * - сумму НДС для налоговой декларации
 *
 * @see VATAmounts
 */
public class VAT {
    private static final BigDecimal DIVIDER = new BigDecimal("1.2");

    /**
     * Получение суммы НДС в чеке
     *
     * @param priceWithVAT    полная стоимость с НДС
     * @param priceWithoutVAT стоимость товара без НДС
     * @return сумма НДС в чеке
     */
    private static BigDecimal getVatInReceipt(BigDecimal priceWithVAT, BigDecimal priceWithoutVAT) {
        return priceWithVAT.subtract(priceWithoutVAT).setScale(2, RoundingMode.HALF_UP);
    }

    /**
     * Получение НДС для налоговой декларации
     *
     * @param vatInReceipt НДС в чеке
     * @return сумму НДС для налоговой декларации
     */
    private static BigInteger getVatForDeclaration(BigDecimal vatInReceipt) {
        return vatInReceipt.setScale(0, RoundingMode.HALF_UP).toBigInteger();
    }

    /**
     * Получение всех сумм по полной стоимости с НДС
     *
     * @param priceWithVAT полная стоимость с НДС
     * @return все НДС суммы
     * @see VATAmounts
     */
    public static VATAmounts getVATAmountsByPriceWithVAT(BigDecimal priceWithVAT) {
        if (priceWithVAT == null || priceWithVAT.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }
        priceWithVAT = priceWithVAT.setScale(2, RoundingMode.HALF_UP);
        BigDecimal priceWithoutVAT = priceWithVAT.divide(DIVIDER, 2, RoundingMode.HALF_DOWN);
        BigDecimal vatInReceipt = getVatInReceipt(priceWithVAT, priceWithoutVAT);
        return new VATAmounts(
                priceWithVAT,
                priceWithoutVAT,
                vatInReceipt,
                getVatForDeclaration(vatInReceipt)
        );
    }

    /**
     * Получение всех сумм по стоимости без НДС
     *
     * @param priceWithoutVAT сумма без НДС
     * @return все НДС суммы
     * @see VATAmounts
     */
    public static VATAmounts getVATAmountsByPriceWithoutVAT(BigDecimal priceWithoutVAT) {
        if (priceWithoutVAT == null || priceWithoutVAT.compareTo(BigDecimal.ZERO) < 0) {
            throw new IllegalArgumentException("Сумма должна быть положительной");
        }
        priceWithoutVAT = priceWithoutVAT.setScale(2, RoundingMode.HALF_UP);
        BigDecimal priceWithVAT = priceWithoutVAT.multiply(DIVIDER).setScale(2, RoundingMode.HALF_UP);
        BigDecimal vatInReceipt = getVatInReceipt(priceWithVAT, priceWithoutVAT);
        return new VATAmounts(
                priceWithVAT,
                priceWithoutVAT,
                vatInReceipt,
                getVatForDeclaration(vatInReceipt)
        );
    }

    /**
     * Все НДС суммы
     *
     * @param priceWithVat      сумма с НДС
     * @param priceWithoutVat   сумма без НДС
     * @param vatInReceipt      сумма НДС в чеке
     * @param vatForDeclaration сумма НДС для налоговой декларации
     */
    public record VATAmounts(
            BigDecimal priceWithVat,
            BigDecimal priceWithoutVat,
            BigDecimal vatInReceipt,
            BigInteger vatForDeclaration
    ) {
    }
}
