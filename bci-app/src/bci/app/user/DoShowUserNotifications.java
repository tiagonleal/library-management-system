package bci.app.user;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.NoSuchUserException;
import bci.exceptions.NoSuchEntityException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.2.3. Show notifications of a specific user.
 */
class DoShowUserNotifications extends Command<LibraryManager> {

    DoShowUserNotifications(LibraryManager receiver) {
        super(Label.SHOW_USER_NOTIFICATIONS, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int userId = Form.requestInteger(Prompt.userId());
        
        try {
            String notifications = _receiver.showUserNotifications(userId);
            
            if (!notifications.isEmpty()) {
                _display.popup(notifications);
            }
            // Se vazio, não mostra nada
            
        } catch (NoSuchEntityException e) {
            throw new NoSuchUserException(userId);
        }
    }

}
