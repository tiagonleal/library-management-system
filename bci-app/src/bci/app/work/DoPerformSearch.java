package bci.app.work;

import bci.LibraryManager;
import pt.tecnico.uilib.menus.Command;
import bci.app.exceptions.NoSuchWorkException;
import pt.tecnico.uilib.forms.Form;

/**
 * 4.3.5. Perform search according to miscellaneous criteria.
 */
class DoPerformSearch extends Command<LibraryManager> {

    DoPerformSearch(LibraryManager receiver) {
        super(Label.PERFORM_SEARCH, receiver);
    }

    @Override
    protected final void execute() {
        String term = Form.requestString(Prompt.searchTerm());
        String result = _receiver.searchWorks(term);
        if (!result.isBlank() && result != null)
            _display.popup(result);
    }

}
