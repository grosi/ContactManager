package contactmanager.main.contacts;

import contactmanager.main.dao.DAOException;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 28.03.2013
 */
public interface ContactsDAO {
    /* @TODO noch Schnittstellen definieren */
    public Contact getContact(int id) throws DAOException;
    public boolean storeContact(Contact updateContact) throws DAOException;
    public boolean deleteContact(int id) throws DAOException;
}
