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
    private ContactsModel contacts_model;
    
    /* Views */
    private ContactsView contacts_view;
    
    public ContactsController(MainController mainController) {
        super();
        
        this.main_controller = mainController;
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        contacts_model = new ContactsModel(this);
        addModel(contacts_model);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        contacts_view = new ContactsView(this);
        addView(contacts_view);
    }
 
    
    /***************************************************************************
     * MainController-> SubController Methoden
     **************************************************************************/
    /**
     * Aktuelle Datensaetze von der Datenbank holen
     */
    @Override
    public void updateData() {
        contacts_model.getContactList();
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
     * View -> Controller Methoden
     **************************************************************************/
    
    public void getContact() {
        int index;
        int list_size;
        ContactDTO contact;
        int contact_id;
        String contact_listname;
        
        index = contacts_view.getSelectedContactIndex(); //Selektierte Gruppe in der Ubersichtsliste
        list_size = contacts_view.getContactListSize(); //Groesse der Uerbsichtsliste
        
        /* Kontrolle ob die Selektion noch moeglich ist */
        if(index > 0 && index != list_size) {
            contact_id = contacts_view.getContactIdOfIndex(index);
            contact_listname = contacts_view.getContactNameOfIndex(index);
            
            /* Nur gespeicherte Gruppen abfragen */
            if(contact_id != CONTACT_DEFAULT_ID) {
                contact = getContactDTO();
                contact.user_id = contact_id;
                contact.user_lastname = contact_listname;
                
                /* Ungespeicherte Gruppen loeschen */
                if(contacts_view.getContactState(CONTACT_DEFAULT_ID) == true)
                    contacts_view.setContactList(CONTACT_DEFAULT_ID, contact_listname, ContactsView.CONTACT_REMOVE_GROUP_WITH_ID);
                
                contacts_view.setMouseWaitCursor(true);
                contacts_model.getContact(contact);
                contacts_view.setMouseWaitCursor(false);
            } else {
                contacts_view.setMessageButtonState(false);
            }
        }
    }
    
    
    /**
     * Erste Gruppe in Ubersichtsliste selektieren
     */
    public void selectGroup() {
        int group_quantity;
 
        group_quantity = contacts_view.getContactQuantity();
        
        /* Falls die Liste nicht leer ist, erster Eintrag selektieren */
        if(group_quantity > 0)
            contacts_view.setSelectedContactIndex(1);
    }
    
    
    /***************************************************************************
     * Controller Methoden
     **************************************************************************/
    /**
     * Erzeugt ein Transfer Objekt
     * @return Transfer Objekt
     */
    private ContactDTO getContactDTO() {
        return new ContactDTO();
    }
}
