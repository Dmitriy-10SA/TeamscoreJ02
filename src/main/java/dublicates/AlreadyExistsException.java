package dublicates;

public class AlreadyExistsException extends RuntimeException {
    private final String value;
    private final int position;

    public AlreadyExistsException(String value, int position) {
        super("Значение " + value + " уже есть. Его позиция: " + position);
        this.value = value;
        this.position = position;
    }

    public String getValue() {
        return value;
    }

    public int getPosition() {
        return position;
    }
}
