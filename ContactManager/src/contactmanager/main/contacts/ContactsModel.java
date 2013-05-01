package contactmanager.main.contacts;

import contactmanager.main.AbstractModel;
import contactmanager.main.contacts.ContactsDAO;
import contactmanager.main.dao.MySQLContactsDAO;
import contactmanager.main.contacts.ContactsController;


/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsModel extends AbstractModel {
    
    private ContactsController controller;
    private String name;
    private ContactsDAO contactsdao;

    public ContactsModel(ContactsController controller) {
        super();
        
        this.controller = controller;
        
        this.contactsdao = controller.getMainController().getDAOFactory().getContactsDAO();
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
