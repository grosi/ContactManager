package contactmanager.main.DAO;

import contactmanager.main.model.Contact;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 28.03.2013
 */
public interface ContactsDAO {
    
    public Contact getContact(int id) throws DAOException;
    public boolean deleteContact(int id) throws DAOException;
    public boolean updateContact(Contact updateContact) throws DAOException;
}