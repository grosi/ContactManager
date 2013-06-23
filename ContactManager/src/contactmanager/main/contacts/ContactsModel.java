package contactmanager.main.contacts;

import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import contactmanager.main.groups.GroupDTO;
import java.util.ArrayList;


/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsModel extends AbstractModel implements ContactsEvent {
    
    /* Kontakt Kontroller */
    private ContactsController controller;
    
    /* DAO */
    private ContactsDAO contactsdao;

    
    /**
     * Konstruktor Kontakt Modell
     * @param controller Referenz auf Kontakt Kontroller
     */
    public ContactsModel(ContactsController controller) {
        super();
        
        this.controller = controller;
        
        this.contactsdao = (ContactsDAO)controller.getDAO();
    }
    
    
     /**
     * Alle vorhanden Kontakte der Datenbank einlesen
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
     * Eine spezifischer Kontakt der Datenbank einlesen
     * @param contact Kontakt Data Transfer Objekt
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
    
    
    /**
     * Neuer Kontakt zu Datenbank hinzufuegen
     * @param contact Kontakt Data Transfer Objekt
     */
    public void addContact(ContactDTO contact) {
        try {
            contact.user_id = contactsdao.insertContact(contact);
            if(contact.contact_adress.size() > 0)
                for(ContactDTO.ContactAdress address : contact.contact_adress)
                    contactsdao.insertContactAddress(contact.user_id,address);
            if(contact.contact_email.size() > 0)
                for(ContactDTO.ContactEmail email : contact.contact_email)
                    contactsdao.insertContactEmail(contact.user_id,email);
            if(contact.contact_phone.size() > 0)
                for(ContactDTO.ContactPhone phone : contact.contact_phone)
                    contactsdao.insertContactPhone(contact.user_id, phone);
        } catch (DAOException ex) {
            contact = null;
        }
  
        firePropertyChange(CONTACT_INSERT_EVENT, null, contact);
    }
    
    
    /**
     * Entfernt ein Kontakt
     * @param contact Kontakt Data Transfer Objekt
     */
    public void removeContact(ContactDTO contact) {
        try {
            contactsdao.deleteContact(contact.user_id);
        } catch (DAOException ex) {
            contact = null;
        }

        firePropertyChange(CONTACT_DELETE_EVENT, null, contact);    
    }
    
    
    /**
     * Bestehender Kontakt in der Datenbank speichern (Aktualisieren)
     * @param contact Kontakt Data Transfer Objekt
     */
    public void saveContact(ContactDTO contact) {
        try {
            contactsdao.updateContact(contact);
            if(contact.contact_adress.size() > 0) {
                for(ContactDTO.ContactAdress address : contact.contact_adress) {
                    if(address.adress_id != CONTACT_DEFAULT_ID)
                        contactsdao.updateContactAddress(address);
                    else
                        contactsdao.insertContactAddress(contact.user_id, address);
                }
            }
            if(contact.contact_email.size() > 0)
                for(ContactDTO.ContactEmail email : contact.contact_email) {
                    if(email.email_id != CONTACT_DEFAULT_ID)
                        contactsdao.updateContactEmail(email);
                    else
                        contactsdao.insertContactEmail(contact.user_id, email);
                }
            if(contact.contact_phone.size() > 0)
                for(ContactDTO.ContactPhone phone : contact.contact_phone) {
                    if(phone.phone_id != CONTACT_DEFAULT_ID)
                        contactsdao.updateContactPhone(phone);
                    else
                        contactsdao.insertContactPhone(contact.user_id, phone);
                }
        } catch (DAOException ex) {
            contact = null;
        }

        firePropertyChange(CONTACT_UPDATE_EVENT, null, contact);
    }
    
    
    /**
     * Nach bestehendem Kontakt suchen, die zu dem angegeben Text passen
     * @param text
     */
    public void searchContact(String text) {
        ArrayList<ContactDTO> contact_list;
        try {
            contact_list = contactsdao.searchContactList(text);
        } catch (DAOException ex) {
            contact_list = null;
        }

        firePropertyChange(CONTACT_SEARCH_EVENT, null, contact_list);
    } 



        /**
     * Alle Gruppen auslesen
     * @param text
     */
    public void allGroups() {
        ArrayList<GroupDTO> group_list;
        try {
            group_list = contactsdao.selectGroupsForContacts();
        } catch (DAOException ex) {
            group_list = null;
        }
        
        firePropertyChange(CONTACT_ALL_GROUP_EVENT, null, group_list);
    } 
}
