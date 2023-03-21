package csvFilter;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

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
    private List<String> emptyDataFile = new ArrayList<>(List.of(HEADER_LINE));
    private String emptyField = "";

    @Test
    public void should_Given_Empty_Or_Null_List_Give_Empty_List(){
        assertThat(filter.apply(List.of())).isEqualTo(List.of());
    }


}
