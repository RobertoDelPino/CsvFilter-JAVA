package csvFilter;

import java.util.List;

public class CsvFilter {
    public List<String> apply(List<String> lines) throws Exception {
        if(lines.size() <= 1){
            throw new Exception("Invalid file");
        }

        return List.of();
    }
}
