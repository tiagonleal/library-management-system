package bci.exceptions;

public class UserIsActiveException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _userId;

    public UserIsActiveException(int id) {
        super("O utente " + id + " está ativo e não pode pagar multa.");
        _userId = id;
    }

    public int getUserId() {
        return _userId;
    }
}
