package group.irrah.big.chat.brasil.api.exception.handler;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.Instant;
import java.util.Arrays;
import java.util.List;

@Data
public class ApiErrorMessage {

    private HttpStatus status;
    private Instant date;
    private String path;
    private List<String> errors;


    public ApiErrorMessage(HttpStatus status, List<String> errors, String path) {
        super();
        this.date = Instant.now();
        this.status = status;
        this.path = path;
        this.errors = errors;
    }

    public ApiErrorMessage(HttpStatus status, String error) {
        super();
        this.date = Instant.now();
        this.status = status;
        errors = Arrays.asList(error);
    }
}

