
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactsDAO;

/**
 * @author grosi
 * @version 0.1
 * @since 28.03.2013
 */
public abstract class DAOFactory {
    /* Liste mit allen verfuegbaren Daten */
    public static final int MySQL = 1;
    
    protected DAOFactory() {}
    
    public abstract ContactsDAO getContactsDAO();
    
    public static DAOFactory getDAOFactory(int database) throws DAOException {
        switch (database) {
            case MySQL:
                return new MySQLDAOFactory();
            default:
                return null;
        }
    }

}
