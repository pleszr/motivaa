package backendtesting;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;
import java.util.Map;

@Data
@AllArgsConstructor
public class ProcessObject {
    String processUuid;
    List<Map<String, String>> lockVersions;

}
