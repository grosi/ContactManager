
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactsDAO;
import contactmanager.main.groups.GroupsDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * @author grosi
 * @version 0.1
 * @since 28.03.2013
 */
public class MySQLDAOFactory extends DAOFactory {

    private static final String DRIVER= "org.gjt.mm.mysql.Driver";
    private static final String DBURL=  "jdbc:mysql://localhost:3306/blabla" ;
    private static final String USER = "root";
    private static final String PW = "";
    
    public static Connection connection = null;
    
    private MySQLDAOFactory instance = null; 
    
    protected MySQLDAOFactory() throws DAOException {
        try {
            Class.forName(DRIVER);
            connection = DriverManager.getConnection(DBURL, USER, PW);
            
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new DAOException("ClassNotFoundException");
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("SQLException");
        }
    }
    
    public MySQLDAOFactory getInstance() throws DAOException {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        
        return instance;
    }
    
    
    
    /**
     * DAO fuer Kontakt-Modul
     * @return neue DAO
     */
    @Override
    public ContactsDAO getContactsDAO() {
        return new MySQLContactsDAO();
    }

    
    
    /**
     * DAO fuer Gruppen-Modul
     * @return neue DAO
     */
    @Override
    public GroupsDAO getGroupsDAO() {
        return new MySQLGroupsDAO();
    }

   
    

}
