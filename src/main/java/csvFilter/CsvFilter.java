package csvFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class CsvFilter {

    public List<String> apply(List<String> lines) throws Exception {
        if(lines == null || lines.isEmpty()){
            return List.of();
        }

        if(lines.size() == 1){
            throw new Exception("Invalid file");
        }

        List<String> result = new ArrayList<>();
        result.add(lines.get(0));

        int indexIVA = 4;
        int indexIGIC = 5;

        String[] lineSplit = lines.get(1).split(",");

        if(lineSplit[indexIVA].equals("") || lineSplit[indexIGIC].equals("")){
            result.add(lines.get(1));
        }

        return result;
    }
}
