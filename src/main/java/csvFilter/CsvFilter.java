package csvFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvFilter {

    private final String HEADER_LINE = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";

    public List<String> apply(List<String> lines) throws Exception {
        if(lines == null || lines.isEmpty()) return List.of();
        if(lines.size() == 1) throw new Exception("Invalid file");
        if(!lines.get(0).equals(HEADER_LINE))throw new Exception("ERROR: must contain header line");

        List<String> result = new ArrayList<>();
        result.add(lines.get(0));

        List<String> invoiceLinesList = new ArrayList<>(lines) ;
        invoiceLinesList.remove(0);

        for (int index = 1; index < lines.size(); index++) {
            String invoiceLine = lines.get(index);
            int indexIVA = 4;
            int indexIGIC = 5;
            int indexCIF = 7;
            int indexNIF = 8;
            String[] invoiceLineSplit = invoiceLine.split(",");

            if((invoiceLineSplit[indexIVA].equals("") || invoiceLineSplit[indexIGIC].equals(""))
                    && !(invoiceLineSplit[indexIVA].equals("") && invoiceLineSplit[indexIGIC].equals(""))
                    && (invoiceLineSplit[indexCIF].isBlank() || invoiceLineSplit[indexNIF].isBlank())
                    && !(invoiceLineSplit[indexCIF].isBlank() && invoiceLineSplit[indexNIF].isBlank())
            ){
                if(!checkEqualInvoiceNumber(invoiceLinesList, invoiceLineSplit[0], index - 1)){
                    result.add(invoiceLine);
                }
            }
        }

        return result;
    }

    private boolean checkEqualInvoiceNumber(List<String> invoicelinesList, String invoiceNumber, int indexInvoiceLinesList){
        for (int index = 0; index < invoicelinesList.size(); index++) {
            if(invoicelinesList.get(index).split(",")[0].equals(invoiceNumber) && indexInvoiceLinesList != index){
                return true;
            }
        }
        return false;
    }
}
