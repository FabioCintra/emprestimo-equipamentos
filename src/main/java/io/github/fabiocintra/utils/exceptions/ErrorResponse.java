package io.github.fabiocintra.utils.exceptions;

public record ErrorResponse(
        String code,
        String field,
        String error
) {
}
