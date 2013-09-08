package network;

/**
 * Excepcion no chequeada que se lanza cuando ocurre un error fatal en la base de datos.
 */
@SuppressWarnings("serial")
public class DatabaseException extends RuntimeException {

	public DatabaseException(String message, Throwable cause) {
		super(message, cause);
	}
}
