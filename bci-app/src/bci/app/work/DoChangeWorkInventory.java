package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.NoSuchEntityException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.3.4. Change the number of exemplars of a work.
 */
class DoChangeWorkInventory extends Command<LibraryManager> {

    DoChangeWorkInventory(LibraryManager receiver) {
        super(Label.CHANGE_WORK_INVENTORY, receiver);
    }

    @Override
    protected final void execute() throws CommandException, NoSuchWorkException {
        int workId = Form.requestInteger(Prompt.workId());
        int numberToChange = Form.requestInteger(Prompt.amountToUpdate());
        try {
            boolean success = _receiver.changeWorkInventory(workId, numberToChange);
            if (!success)
                _display.popup(Message.notEnoughInventory(workId, numberToChange));
        } catch (NoSuchEntityException e) {
            if (e.getEntityType().equals("obra")) {
                throw new NoSuchWorkException(Integer.parseInt(e.getEntityId()));
            }
        }
        
    }

}
