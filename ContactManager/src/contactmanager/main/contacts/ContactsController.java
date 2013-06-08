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
    
    private MainController main_controller;

    /* Modelle */
    private ContactsModel contactsModel;
    
    /* Views */
    private AbstractView contactsView;
    
    public ContactsController(MainController mainController) {
        super();
        
        this.main_controller = mainController;
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        contactsModel = new ContactsModel(this);
        addModel(contactsModel);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        contactsView = new ContactsView(this);
        addView(contactsView);
    }
 
    
    /***************************************************************************
     * MainController-> SubController Methoden
     **************************************************************************/
    /**
     * Aktuelle Datensaetze von der Datenbank holen
     */
    @Override
    public void updateData() {
        System.out.println("UPDATE CONTACTS");
    }
    
    /***************************************************************************
     * Model/View -> MainController Methoden
     **************************************************************************/
    
    /**
     * View zu Frame hinzufuegen
     * @param title
     * @param view 
     * @todo evtl in AbstractController einbauen!
     */
    public void addViewToFrame(String title, AbstractView view) {
        this.main_controller.addTabToMainFrame(title, view);
    }
    
    /**
     * Spezifische DAO erstellen
     * @return GroupsDAO
     * @todo evtl in AbstractController einbauen
     */
    @Override
    public Object getDAO() {
        return this.main_controller.getDAOFactory().getContactsDAO();
    }
    
    
    /***************************************************************************
     * GUI -> Model Methoden
     **************************************************************************/
 
}
