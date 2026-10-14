package bci.exceptions;

public class ReturnFailedException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    public ReturnFailedException(int idUser, int idWork) {
        super("Erro ao devolver a obra " + idWork + " pelo utente " + idUser + ".");
    }
}
