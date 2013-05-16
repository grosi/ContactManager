package contactmanager.main.contacts;

import contactmanager.main.AbstractController;
import contactmanager.main.AbstractView;
import contactmanager.main.frame.MainController;
import contactmanager.main.SubController;


/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class ContactsController extends AbstractController implements ContactsInterface, SubController {
    
    private MainController mainController;

    /* Modelle */
    private ContactsModel contactsModel;
    
    /* Views */
    private AbstractView contactsView;
    
    public ContactsController(MainController mainController) {
        super();
        
        this.mainController = mainController;
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        contactsModel = new ContactsModel(this);
        addModel(contactsModel);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        contactsView = new ContactsView(this);
        addView(contactsView);
    }
 
    
    
    /***************************************************************************
     * Model/View -> MainController Methoden
     **************************************************************************/
    
    @Override
    public MainController getMainController() {
        return this.mainController;
    }
    
    
    /***************************************************************************
     * GUI -> Model Methoden
     **************************************************************************/
 
}
