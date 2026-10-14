package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchWorkException;
import bci.exceptions.NoSuchEntityException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.1. Display work.
 */
class DoDisplayWork extends Command<LibraryManager> {

    DoDisplayWork(LibraryManager receiver) {
        super(Label.SHOW_WORK, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        int id = Form.requestInteger(Prompt.workId());
        try{
        _display.popup(_receiver.showWork(id)); }
        catch (NoSuchEntityException e) {throw new NoSuchWorkException(id);}
    }

}
