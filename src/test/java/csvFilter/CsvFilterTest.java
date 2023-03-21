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
    *   2.
    *
    *
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
    public void shouldThrowErrorIfListLengthIs1(){
        assertThatThrownBy(() -> filter.apply(List.of(HEADER_LINE))).hasMessage("Invalid file");
    }


}
