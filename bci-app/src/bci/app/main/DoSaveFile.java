package bci.app.main;

import bci.LibraryManager;
import bci.exceptions.MissingFileAssociationException;
import pt.tecnico.uilib.menus.Command;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;
import java.io.IOException;
import bci.app.exceptions.FileOpenFailedException;

/**
 * §4.1.1 Open and load files.
 */
class DoSaveFile extends Command<LibraryManager> {

    DoSaveFile(LibraryManager receiver) {
        super(Label.SAVE_FILE, receiver);
    }

    @Override
    protected final void execute() throws CommandException {
        try {
            _receiver.save();
        } catch (MissingFileAssociationException e) {

            String fileName = Form.requestString(Prompt.newSaveAs());

            try {
                _receiver.saveAs(fileName);
            } catch (IOException e2) {
                throw new FileOpenFailedException(e2);
            }
        }
        catch (IOException e) {
            throw new FileOpenFailedException(e);
        }
    }

}
