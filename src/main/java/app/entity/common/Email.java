package app.entity.common;

import com.fasterxml.jackson.annotation.JsonValue;

public record Email(@JsonValue String email) {
    @Override
    public String toString() {
        return email;
    }
}
