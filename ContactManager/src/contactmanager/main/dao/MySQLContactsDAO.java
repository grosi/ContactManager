
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactsDAO;
import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.groups.GroupDTO;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Kevin Gerber
 * @version 0.1
 * @since 28.03.2013
 */
public class MySQLContactsDAO implements ContactsDAO {
    
    public MySQLContactsDAO() {}
   
    @Override
    public ArrayList<ContactDTO> selectContactList() throws DAOException {
            ArrayList<ContactDTO> list = new ArrayList<>();
            ResultSet result;

            result = executeQuery("SELECT * FROM user WHERE ORDER BY prename ASC, lastname ASC");

            if (result == null)
                    throw new DAOException("No User in the Database.");

            while (nextLine(result)) {
                    /* Benutzer anhaengen */
                    list.add(fillInContact(result));
            }

            return list;
    }

    @Override
    public ArrayList<ContactDTO> searchContactList(String search_pattern) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContactDTO selectContact(int id) throws DAOException {
            ResultSet result;

            result = executeQuery("SELECT * FROM user WHERE user_id="+id);

            return fillInContact(result);
    }

    @Override
    public boolean insertContact(ContactDTO insertContact) throws DAOException {
            return executeUpdate("INSERT INTO user(prename, lastname, state)" +
                            "VALUE('"+insertContact.prename+"', '"+insertContact.lastname+"'," +
                                            ""+insertContact.state+")");
    }

    @Override
    public boolean updateContact(ContactDTO updateContact) throws DAOException {
            if (updateContact.user_id <= 0)
                    return false;

            return executeUpdate("UPDATE user SET prename='"+updateContact.prename+"'," +
                            "lastname='"+updateContact.lastname+"'," +
                            "state="+updateContact.state+" WHERE user_id="+updateContact.user_id);
    }


    @Override
    public boolean addContactToGroup(int contact_id, int group_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteContactFromGroup(int contact_id, int group_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteContact(int id) throws DAOException {
            /* Alle Anderene Felder auch loeschen */
            return executeUpdate("DELETE user WHERE user_id="+id);
    }

    @Override
    public ArrayList<GroupDTO> selectGroupsFromContact(int contact_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
   
    
    
    
    /***************************************************************************
     * Private Methoden fuer Datenbankzugriff
     **************************************************************************/
    private ResultSet executeQuery(String query) throws DAOException {
		Statement statement;
		ResultSet result;
		
		try {
			statement = MySQLDAOFactory.connection.createStatement();
			result = statement.executeQuery(query);
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Error in your SQL Query");
		}
		
		return result;
    }
	
    private boolean executeUpdate(String query) throws DAOException {
            Statement statement;

            try {
                    statement = MySQLDAOFactory.connection.createStatement();
                    statement.executeUpdate(query);
            }
            catch (SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Cannot change Database Values");
            }

            return true;
    }
	
    private boolean nextLine(ResultSet result) throws DAOException {
            try {
                    return result.next();
            }
            catch (SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Not a ResultSet Object");
            }
    }
	
    private ContactDTO fillInContact(ResultSet result) throws DAOException {
            ContactDTO contact = new ContactDTO();

            if (result == null)
                    throw new DAOException("User does not exists!");

            try {
                    contact.user_id = result.getInt("user_id");
                    contact.prename = result.getString("prename");
                    contact.lastname = result.getString("lastname");
                    contact.state = result.getInt("state");
            }
            catch (SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Colum does not Exists!");
            }

            return contact;
    }

}
