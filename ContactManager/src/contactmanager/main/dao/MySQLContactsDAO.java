
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactsDAO;
import contactmanager.main.contacts.Contact;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author grosi
 * @version 0.1
 * @since 28.03.2013
 */
public class MySQLContactsDAO implements ContactsDAO {
    
    public MySQLContactsDAO() {}
    
    @Override
    public Contact getContact(int id) throws DAOException {
        PreparedStatement s;
        
        try {
            s = MySQLDAOFactory.connection.prepareStatement("SELECT name FROM ´contacts´ where id = ?;");
            s.setInt(1, id);
            ResultSet rs = s.executeQuery();
            return new Contact(rs.getString("name"));
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("SQLException");
        }
    }
    
    @Override
    public boolean deleteContact(int id) throws DAOException {
        PreparedStatement s;
        
        try {
            s = MySQLDAOFactory.connection.prepareStatement("Delete From ´contacts´ where id = ?;");
            s.setInt(1,id);
            ResultSet rs = s.executeQuery();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("SQLException");
        }
    }
    
    @Override
    public boolean updateContact(Contact updateContact) throws DAOException {
        PreparedStatement s;
        
        try {
            s = MySQLDAOFactory.connection.prepareStatement("Update ´contacts´ SET name = ? where id = ?;");
            s.setInt(1,updateContact.getID());
            s.setString(2, updateContact.getName());
            ResultSet rs = s.executeQuery();
            return true;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("SQLException");
        }
    }
   

}
