package group.irrah.big.chat.brasil.api.exception;

/**
 * Classe de exception de bad request.
 */
public class IlegalException extends RuntimeException {

    public IlegalException(String message) {
        super(message);
    }
}
