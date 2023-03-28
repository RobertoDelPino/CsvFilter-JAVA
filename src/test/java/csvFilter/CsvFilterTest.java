package csvFilter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

public class CsvFilterTest {

    /*
    *
    * Uses cases:
    *   1. Empty or null list give empty list
    *   2. List size is lower or equal to 1 throw error
    *   3. Given list with correct lines return same list
    *   4.
    *
    */

    private final String HEADER_LINE = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
    private CsvFilter filter = new CsvFilter();
    private final List<String> EMPTYDATAFILE = new ArrayList<>(List.of(HEADER_LINE));
    private final String EMPTYFIELD = "";

    @Test
    public void shouldGivenEmptyOrNullListGiveEmptyList() throws Exception {
        assertThat(filter.apply(null)).isEqualTo(List.of());
    }

    @Test
    public void shouldThrowErrorIfListDoesntContainInvoices(){
        assertThatThrownBy(() -> filter.apply(List.of(HEADER_LINE))).hasMessage("Invalid file");
    }

    @Test
    public void shouldThrowErrorIfListDoesntContainHeaderLine(){
        String invoiceLine = "1,02/05/2019,100,81,19,1,ACER Laptop,B76430134,";
        assertThatThrownBy(() -> filter.apply(List.of(invoiceLine, invoiceLine))).hasMessage("ERROR: must contain header line");
    }

    @Test
    public void shouldGiveSameLineIfListContainCorrectLines() throws Exception {
        String invoiceLine = "1,02/05/2019,100,81,19,,ACER Laptop,B76430134, ";
        List<String> result = filter.apply(List.of(HEADER_LINE, invoiceLine));

        assertThat(result).isEqualTo(List.of(HEADER_LINE, invoiceLine));
    }

    @Test
    public void shouldExcludeLinesWithBothTaxFieldNotEmpty() throws Exception {
        String invoiceLine = "1,02/05/2019,100,81,19,1,ACER Laptop,B76430134, ";
        List<String> result = filter.apply(List.of(HEADER_LINE, invoiceLine));

        assertThat(result).isEqualTo(List.of(HEADER_LINE));
    }

    @Test
    public void shouldExcludeLinesWithBothIDFieldsNotEmpty() throws Exception {
        String invoiceLine = "1,02/05/2019,100,81,19,,ACER Laptop,B76430134,111";
        List<String> result = filter.apply(List.of(HEADER_LINE, invoiceLine));

        assertThat(result).isEqualTo(List.of(HEADER_LINE));
    }

    @Test
    public void shouldExcludeLinesWithEqualInvoiceNumber() throws Exception {
        String invoiceLine = "1,02/05/2019,100,81,19,,ACER Laptop,B76430134, ";
        String invoiceLine2 = "2,02/05/2019,100,81,19,,ACER Laptop,B76430134, ";
        List<String> result = filter.apply(List.of(HEADER_LINE, invoiceLine, invoiceLine2, invoiceLine));

        assertThat(result).isEqualTo(List.of(HEADER_LINE, invoiceLine2));
    }

    @Test
    public void shouldExcludeInvoiceLinesWithIncorrectNetValue() throws Exception {
        String invoiceLine = "1,02/05/2019,100,810,19,,ACER Laptop,B76430134, ";
        List<String> result = filter.apply(List.of(HEADER_LINE, invoiceLine));

        assertThat(result).isEqualTo(List.of(HEADER_LINE));
    }
}
