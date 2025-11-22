package common;

import java.util.List;

public record ValidationResult(boolean isValid, List<String> errors) {
}