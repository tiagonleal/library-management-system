package bci.app.request;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.WorkNotBorrowedByUserException;
import bci.app.exceptions.UserIsActiveException;
import bci.exceptions.NoSuchEntityException;
import bci.exceptions.ReturnFailedException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.4.2. Return a work.
 */
class DoReturnWork extends Command<LibraryManager> {

    DoReturnWork(LibraryManager receiver) {
        super(Label.RETURN_WORK, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = Form.requestInteger(bci.app.user.Prompt.userId());
        int workId = Form.requestInteger(bci.app.work.Prompt.workId());
        try {
            _receiver.returnWork(userId, workId);
        } catch (ReturnFailedException e) {
            throw new WorkNotBorrowedByUserException(workId, userId);
        }catch (NoSuchEntityException e) {
            if (e.getEntityType().equals("utente")) {
                throw new NoSuchUserException(Integer.parseInt(e.getEntityId()));
            } else {
                throw new NoSuchWorkException(Integer.parseInt(e.getEntityId()));
            }
        }

        try {
            int fines = _receiver.getUserFines(userId);
            if (fines > 0) {
                _display.popup(Message.showFine(userId, fines));
                boolean payFines = Form.confirm(Prompt.finePaymentChoice());
                if (payFines) {
                    _receiver.payFine(userId);
                }
            }
        } catch (NoSuchEntityException e) {
            throw new NoSuchUserException(userId);
        } catch (bci.exceptions.UserIsActiveException e) {
            throw new UserIsActiveException(userId);
        }
    }

}
