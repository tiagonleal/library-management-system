package bci.app.work;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchCreatorException;
import bci.exceptions.NoSuchEntityException;
import pt.tecnico.uilib.forms.Form;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;

/**
 * 4.3.3. Display all works by a specific creator.
 */
class DoDisplayWorksByCreator extends Command<LibraryManager> {

    DoDisplayWorksByCreator(LibraryManager receiver) {
        super(Label.SHOW_WORKS_BY_CREATOR, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        String creatorId = Form.requestString(Prompt.creatorId());
        try{
            _display.popup(_receiver.showCreatorWorks(creatorId));
        } catch (NoSuchEntityException e) {
            throw new NoSuchCreatorException(creatorId);
        }
    }

}
