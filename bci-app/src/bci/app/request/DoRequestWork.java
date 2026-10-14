package bci.app.request;

import bci.LibraryManager;
import bci.app.exceptions.NoSuchUserException;
import bci.app.exceptions.NoSuchWorkException;
import bci.app.exceptions.BorrowingRuleFailedException;
import pt.tecnico.uilib.menus.Command;
import bci.exceptions.BorrowingFailedException;
import bci.exceptions.NoSuchEntityException;
import pt.tecnico.uilib.menus.CommandException;
import pt.tecnico.uilib.forms.Form;


/**
 * 4.4.1. Request work.
 */
class DoRequestWork extends Command<LibraryManager> {

    DoRequestWork(LibraryManager receiver) {
        super(Label.REQUEST_WORK, receiver);

    }

    @Override
    protected final void execute() throws CommandException {
        int userId = Form.requestInteger(bci.app.user.Prompt.userId());
        int workId = Form.requestInteger(bci.app.work.Prompt.workId());
        
        try {
            // Tentar requisitar
            int dueDate = _receiver.requestWork(userId, workId);
            
            // Sucesso - mostrar prazo
            _display.popup(Message.workReturnDay(workId, dueDate));
            
        } catch (NoSuchEntityException e) {
            // Converter para exceção específica
            if (e.getEntityType().equals("utente")) {
                throw new NoSuchUserException(Integer.parseInt(e.getEntityId()));
            } else {
                throw new NoSuchWorkException(Integer.parseInt(e.getEntityId()));
            }
            
        } catch (BorrowingFailedException e) {
            // Verificar se é regra 3 (sem exemplares)
            if (e.getRuleId() == 3) {
                // Perguntar se quer ser notificado
                boolean wantsNotification = Form.confirm(Prompt.returnNotificationPreference());
                
                if (wantsNotification) {
                    try {
                        _receiver.registerAvailabilityInterest(userId, workId);
                    } catch (NoSuchEntityException ex) {
                        // Não deve acontecer (já validámos antes)
                    }
                }
            }
            
            // Lançar exceção da app
            if (e.getRuleId() != 3)
                throw new BorrowingRuleFailedException(e.getUserId(), e.getWorkId(), e.getRuleId());
        }
    
    }

}
