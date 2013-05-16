package contactmanager.main.contacts;

import contactmanager.main.AbstractModel;


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
        
        //this.contactsdao = controller.getMainController().getDAOFactory().getContactsDAO();
    }
    
    
    
}
