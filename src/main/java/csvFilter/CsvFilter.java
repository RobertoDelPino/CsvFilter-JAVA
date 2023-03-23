package csvFilter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class CsvFilter {

    private final String HEADER_LINE = "Num_factura, Fecha, Bruto, Neto, IVA, IGIC, Concepto, CIF_cliente, NIF_cliente";
    private final int INDEX_IVA = 4;
    private final int INDEX_IGIC = 5;
    private final int INDEX_CIF = 7;
    private final int INDEX_NIF = 8;

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
            String[] invoiceLineSplit = invoiceLine.split(",");
            // Hacer una comprobación a la vez, no todas
            if(checkIdAndTaxFields(invoiceLineSplit)){
                if(!checkEqualInvoiceNumber(invoiceLinesList, invoiceLineSplit[0], index - 1)){
                    if(checkIncorrectNetValue(Arrays.stream(invoiceLineSplit).toList())){
                        result.add(invoiceLine);
                    }
                }
            }
        }

        return result;
    }

    private boolean checkIdAndTaxFields(String[] invoiceLineSplit){
        if(checkForEmptiesIdFields(invoiceLineSplit)){
            if(checkForSomeEmptyIdFields(invoiceLineSplit))
                if(checkForTaxFieldsAreEmpties(invoiceLineSplit)){
                    if(checkForSomeEmptyTaxFields(invoiceLineSplit)){
                        return true;
                    }
                }
            }
        return false;
    }


    private boolean checkForSomeEmptyTaxFields(String[] invoiceLineSplit){
        return invoiceLineSplit[INDEX_IVA].equals("") || invoiceLineSplit[INDEX_IGIC].equals("");
    }

    private boolean checkForTaxFieldsAreEmpties(String[] invoiceLineSplit){
        return !(invoiceLineSplit[INDEX_IVA].equals("") && invoiceLineSplit[INDEX_IGIC].equals(""));
    }

    private boolean checkForEmptiesIdFields(String[] invoiceLineSplit){
        return !(invoiceLineSplit[INDEX_CIF].isBlank() && invoiceLineSplit[INDEX_NIF].isBlank());
    }
    private boolean checkForSomeEmptyIdFields(String[] invoiceLineSplit){
        return invoiceLineSplit[INDEX_CIF].isBlank() || invoiceLineSplit[INDEX_NIF].isBlank();
    }

    private boolean checkEqualInvoiceNumber(List<String> invoicelinesList, String invoiceNumber, int indexInvoiceLinesList){
        for (int index = 0; index < invoicelinesList.size(); index++) {
            if(invoicelinesList.get(index).split(",")[0].equals(invoiceNumber) && indexInvoiceLinesList != index){
                return true;
            }
        }
        return false;
    }

    private boolean checkIncorrectNetValue(List<String> invoiceLineSplit){
        int indexGrossValue = 2;
        int indexNetValue = 3;

        float grossValue = Float.parseFloat(invoiceLineSplit.get(indexGrossValue));
        float netValue = Float.parseFloat(invoiceLineSplit.get(indexNetValue));
        float taxField;
        if(!invoiceLineSplit.get(INDEX_IVA).equals("")){
            taxField = Float.parseFloat(invoiceLineSplit.get(INDEX_IVA));
        }
        else{
            taxField = Float.parseFloat(invoiceLineSplit.get(INDEX_IGIC));
        }
        return (grossValue - (grossValue * (taxField / 100))) == netValue;
    }
}
