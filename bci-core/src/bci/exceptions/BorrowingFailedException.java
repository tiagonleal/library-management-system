package bci.exceptions;

public class BorrowingFailedException extends Exception {
    @java.io.Serial
    private static final long serialVersionUID = 202507171003L;

    private int _userId;
    private int _workId;
    private int _ruleId;

    public BorrowingFailedException(int idUser, int idWork, int idRule) {
        super("Erro ao requisitar a obra " + idWork + " pelo utente " + idUser + ". Violação da regra " + idRule + ".");
        _userId = idUser;
        _workId = idWork;
        _ruleId = idRule;
    }
    
    public int getRuleId() {
        return _ruleId;
    }

    public int getUserId() {
        return _userId;
    }

    public int getWorkId() {
        return _workId;
    }
}
