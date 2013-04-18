package contactmanager.main.model;

import contactmanager.main.AbstractModel;
import contactmanager.main.DAO.ContactsDAO;
import contactmanager.main.DAO.MySQLContactsDAO;
import contactmanager.main.controller.ContactsController;


/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsModel extends AbstractModel {
    
    private String name;
    private ContactsDAO dataHandler;

    public ContactsModel() {
        super();
        
        this.dataHandler = new MySQLContactsDAO();
    }
    
    /**
     * @TODO nur Testmethode
     * @param name
     */
    public void setName(String name) {
        String oldName = this.name;
        this.name = name;
        System.out.println("Text geaendert");
        firePropertyChange(ContactsController.NAME_CHANGED_EVENT, oldName, name);
    }
    
    /**
     * @TODO nur Testmethode
     * @return
     */
    public String getName() {
        return name;
    }
    
    /**
     * @TODO nur Testmethode
     */
    public void setChange() {
        System.out.println("Button gedrueckt");
    }
    
}
