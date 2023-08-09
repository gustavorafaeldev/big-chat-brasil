package group.irrah.big.chat.brasil.api.exception.handler;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.Instant;

@Data
@AllArgsConstructor
public class StandardError {

    private Instant date;
    private Integer status;
    private String error;
    private String message;
    private String path;
}
