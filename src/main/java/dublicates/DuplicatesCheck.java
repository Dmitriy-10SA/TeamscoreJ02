package dublicates;

import java.util.HashMap;
import java.util.Map;

public class DuplicatesCheck {
    private final Map<String, Integer> inputs;
    private int cnt;

    public DuplicatesCheck() {
        this.inputs = new HashMap<>();
        this.cnt = 0;
    }

    public void addOrThrow(String input) throws AlreadyExistsException {
        if (inputs.containsKey(input)) {
            throw new AlreadyExistsException(input, inputs.get(input));
        }
        inputs.put(input, cnt++);
    }
}
