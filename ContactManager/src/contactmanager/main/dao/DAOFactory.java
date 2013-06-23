
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactsDAO;
import contactmanager.main.groups.GroupsDAO;

/**
 * DAO Fabrik
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 28.03.2013
 */
public abstract class DAOFactory {
    
    /* Liste mit allen verfuegbaren Daten */
    public static final int MySQL = 1;
    
    /* Spezifische DAOs */
    public abstract ContactsDAO getContactsDAO();
    public abstract GroupsDAO getGroupsDAO();
    
    /**
     * Konstruktor
     */
    protected DAOFactory() {}
    
   
    /**
     * Neue spezifische Fabrik erstellen
     * @param database Art der Datenbank waehlen
     * @return Datenbank Fabrik
     * @throws DAOException
     */
    public static DAOFactory getDAOFactory(int database) throws DAOException {
        switch (database) {
            case MySQL:
                return MySQLDAOFactory.getInstance();
            default:
                return null;
        }
    }
}
