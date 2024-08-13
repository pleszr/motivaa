package backendtesting;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class OutputVerification {
    String outputAttribute;
    String expectedValue;
}
