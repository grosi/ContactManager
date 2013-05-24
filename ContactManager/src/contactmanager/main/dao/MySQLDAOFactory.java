
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

    private static final String DRIVER= "com.mysql.jdbc.Driver";
    private static final String DBURL=  "jdbc:mysql://sql2.freesqldatabase.com:3306/sql27717" ;
    private static final String USER = "sql27717";
    private static final String PW = "wZ3!cX7!";

    private static Connection connection = null;
    private static MySQLDAOFactory instance = null; 
    
    private MySQLDAOFactory() throws DAOException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new DAOException("ClassNotFoundException");
        }
    }
    
    public static MySQLDAOFactory getInstance() throws DAOException {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        
        return instance;
    }
    
    
    public Connection getConnection() throws SQLException {
        if(connection == null) {
            connection = DriverManager.getConnection(DBURL, USER, PW);
        }
        
        return connection;
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
