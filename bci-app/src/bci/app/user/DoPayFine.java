package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.UserIsActiveException;
import bci.exceptions.NoSuchEntityException;


/**
 * 4.2.5. Settle a fine.
 */
class DoPayFine extends Command<LibraryManager> {

    DoPayFine(LibraryManager receiver) {
        super(Label.PAY_FINE, receiver);

    }

    @Override
        protected final void execute() throws CommandException {
            int userId = Form.requestInteger(bci.app.user.Prompt.userId());
            try {
                _receiver.payFine(userId);
            } catch (NoSuchEntityException e) {
                throw new NoSuchUserException(userId);
            }
            catch (bci.exceptions.UserIsActiveException e) {
                throw new UserIsActiveException(userId);
            }
    }

}
