package bci.app.user;

import bci.LibraryManager;
import bci.app.exceptions.UserRegistrationFailedException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.2.1. Register new user.
 */
class DoRegisterUser extends Command<LibraryManager> {

    DoRegisterUser(LibraryManager receiver) {
        super(Label.REGISTER_USER, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        String name = Form.requestString(Prompt.userName());
        String email = Form.requestString(Prompt.userEMail());
        try{
            int id = _receiver.registerUser(name, email);
            _display.popup(Message.registrationSuccessful(id));
        } catch (bci.exceptions.UserRegistrationFailedException e) {throw new UserRegistrationFailedException(name, email);}   
    }

}
