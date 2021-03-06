
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactsDAO;
import contactmanager.main.groups.GroupsDAO;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * MySQL DAO Fabrik
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 28.03.2013
 */
public class MySQLDAOFactory extends DAOFactory {

    /* Zugangsdaten zur Datenbank */
    private static final String DRIVER= "com.mysql.jdbc.Driver";
    private static final String DBURL=  "jdbc:mysql://sql2.freesqldatabase.com:3306/sql27717" ;
    private static final String USER = "sql27717";
    private static final String PW = "wZ3!cX7!";

    /* Datenbankverbindung */
    private static Connection connection = null;
    private static MySQLDAOFactory instance = null; 
    
    /**
     * Fabrik Konstruktor -> Singleton
     * @throws DAOException 
     */
    private MySQLDAOFactory() throws DAOException {
        try {
            Class.forName(DRIVER);
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
            throw new DAOException("ClassNotFoundException");
        }
    }
    
    
    /**
     * Neu Fabrik Instantzieren -> Singleton
     * @return Referenz auf Fabrik
     * @throws DAOException 
     */
    public static MySQLDAOFactory getInstance() throws DAOException {
        if (instance == null) {
            instance = new MySQLDAOFactory();
        }
        
        return instance;
    }
    
    
    /**
     * Verbindung zu Datenbank aufbauen
     * @return Referenz auf Verbindung
     * @throws SQLException 
     */
    public Connection getConnection() throws SQLException {
        if(connection == null || connection.isClosed()) {
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
