package contactmanager.main.contacts;

import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import java.util.ArrayList;


/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsModel extends AbstractModel implements ContactsInterface {
    
    private ContactsController controller;
    private ContactsDAO contactsdao;

    public ContactsModel(ContactsController controller) {
        super();
        
        this.controller = controller;
        
        this.contactsdao = (ContactsDAO)controller.getDAO();
    }
    
     /**
     * Alle vorhanden Gruppen der Datenbank einlesen
     */
    public void getContactList() { 
        ArrayList<ContactDTO> group_list;
        
        try {
            group_list = contactsdao.selectContactList();
        } catch (DAOException ex) {
            group_list = null;
        }
        
        firePropertyChange(CONTACT_LIST_SELECT_EVENT, null, group_list);
    }
    
    
    /**
     * Eine spezifischer KOntakt der Datenbank einlesen
     * @param group Gruppen Data Transfer Objekt
     */
    public void getContact(ContactDTO contact) {
        ContactDTO contact_db;
        try {
            contact_db = contactsdao.selectContact(contact.user_id);
        } catch (DAOException ex) {
            contact_db = null;
        }
   
        firePropertyChange(CONTACT_SELECT_EVENT, contact, contact_db);
    }
    
}
