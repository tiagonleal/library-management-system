package bci.app.user;

import bci.app.exceptions.*;
import bci.exceptions.NoSuchEntityException;
import bci.LibraryManager;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.2. Show specific user.
 */
class DoShowUser extends Command<LibraryManager> {

    DoShowUser(LibraryManager receiver) {
        super(Label.SHOW_USER, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int id = Form.requestInteger(Prompt.userId());
        try {
            _display.popup(_receiver.showUser(id));
        } catch (NoSuchEntityException e) {throw new NoSuchUserException(id);}
    }

}
