package group.irrah.big.chat.brasil.api.exception;

/**
 * Classe de exception utilizada para quando não forem encontrados registros no banco.
 */
public class NotFoundException extends RuntimeException {

    public NotFoundException(String message) {
        super(message);
    }
}
