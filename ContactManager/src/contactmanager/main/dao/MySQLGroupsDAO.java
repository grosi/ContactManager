
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.groups.GroupDTO;
import contactmanager.main.groups.GroupsDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public class MySQLGroupsDAO implements GroupsDAO {
    
    private Connection connection = null;
    private PreparedStatement prepared_statement = null;
    private ResultSet result = null;

    
    public MySQLGroupsDAO() {}
    

    @Override
    public ArrayList<GroupDTO> selectGroupList() throws DAOException {
        ArrayList<GroupDTO> groups = new ArrayList<>();

        //result = executeQuery("SELECT `group_id`, `name` FROM `group` WHERE `name` LIKE '%e%' ORDER BY `name` ASC");
        result = executeQuery("SELECT * FROM `group` ORDER BY `name` ASC");

        while(nextDataSet(result)) {
            groups.add(getGroupDTO(result));
        }
        //closeConnection();
        closeResult(result);
        
        return groups;
    }

    @Override
    public ArrayList<GroupDTO> searchGroupList(String search_pattern) throws DAOException {
        ArrayList<GroupDTO> groups = new ArrayList<>();

        result = executeQuery("SELECT * FROM `group` WHERE `name` " + "LIKE '%"+search_pattern+"%' ORDER BY `name` ASC");

        while(nextDataSet(result)) {
            groups.add(getGroupDTO(result));
        }
        
        closeResult(result);

        return groups;
    }

    @Override
    public GroupDTO selectGroup(int group_id) throws DAOException {
        ArrayList<ContactDTO> contacts;
        GroupDTO group =null;

        //result = executeQuery("SELECT `group_id`, `name` FROM `group` WHERE `group_id`="+group_id);
        result = executeQuery("SELECT * FROM `group` WHERE `group_id`="+group_id);
        nextDataSet(result);
        group = getGroupDTO(result);
        
        closeResult(result);
        
        contacts = selectContactsFromGroup(group.group_id);
        group.group_contacts = contacts;
        
        //closeConnection();
        
        return group;
    }

    @Override
    public int insertGroup(GroupDTO insert_group) throws DAOException {
        int id;
    	
    	if (insert_group.group_id != 0)
            throw new DAOException("Group id is allready set");
    	
    	id = executeInsert("INSERT INTO `group`(name)" +
                "VALUE('"+insert_group.group_name+"')");
    	
    	//insert_group.group_id = id;
    	
    	return id;
    }

    @Override
    public boolean updateGroup(GroupDTO update_group) throws DAOException {
        if (update_group.group_id <= 0)
            throw new DAOException("Group id is missed");

        executeUpdate("UPDATE `group` SET name='"+update_group.group_name+"' " +"WHERE group_id="+update_group.group_id);
        
        return true;
    }

    @Override
    public boolean deleteGroup(int group_id) throws DAOException {
         executeUpdate("DELETE `group`.*, `user2group`.* FROM `group` " +
        		"LEFT JOIN `user2group` ON `group`.`group_id` = `user2group`.`group_id` " +
        		"WHERE `group`.`group_id`="+group_id);
        
        return true;
    }

    @Override
    public ArrayList<ContactDTO> selectContactsFromGroup(int group_id) throws DAOException {
        ArrayList<ContactDTO> contacts = new ArrayList<>();
        
        result = executeQuery("SELECT u.* FROM `user` AS u, `user2group` AS c WHERE c.group_id = "+group_id+" AND u.user_id = c.user_id ORDER BY u.prename ASC, u.lastname ASC");
        
        while(nextDataSet(result)) {
            contacts.add(getContactDTO(result));
        }
        //closeConnection();
        closeResult(result);
        
        return contacts;
    }

    @Override
    public boolean addGroupToContact(int group_id, int contact_id) throws DAOException {
        deleteGroupFromContact(group_id, contact_id);
        executeUpdate("INSERT INTO user2group(user_id, group_id)" +
        			"VALUES("+Integer.toString(contact_id)+", "+Integer.toString(group_id)+")");
    	
        return true;
    }

    @Override
    public boolean deleteGroupFromContact(int group_id, int contact_id) throws DAOException {
        executeUpdate("DELETE FROM user2group " +
        		"WHERE user_id="+contact_id+" " +
        				"AND group_id="+group_id);
        
        return true;
    }

    @Override
    public ArrayList<ContactDTO.ContactEmail> selectEmailAddressFromGroup(int group_id) throws DAOException {
        ArrayList<ContactDTO.ContactEmail> emails = new ArrayList<>();
        ContactDTO.ContactEmail line;

        result = executeQuery("SELECT * FROM user_email INNER JOIN user2group " +
        		"WHERE user2group.group_id="+Integer.toString(group_id)+" " +
        				"&& user_email.user_id=user2group.user_id && user_email.priority=1");

        while(nextDataSet(result)) {
            line = new ContactDTO().new ContactEmail();
        	
            try {
                line.email_id = result.getInt("email_id");
                line.email_type = result.getString("type");
                line.email_adress = result.getString("email");
                line.email_priority = result.getInt("priority");
            } catch (SQLException e) {
                e.printStackTrace();
                throw new DAOException("Colum does not Exists!");
            }

            emails.add(line);
        }
        
        closeResult(result);

        return emails;
    }
   

    /***************************************************************************
     * Private Methoden fuer Datenbankzugriff
     **************************************************************************/

    /**
     * Query ausfuehren
     * @param query String mit Datenbankbefehlen
     * @return Set mit Ergebnissen
     * @throws DAOException 
     */
    private ResultSet executeQuery(String query) throws DAOException{
        try {
            connection = getConnection();
            prepared_statement = connection.prepareStatement(query);
            result = prepared_statement.executeQuery();
            return result;
        } catch (DAOException | SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Query string is incorrect");
        }	
    }
    
    
    /**
     * Datensatz aktualisieren
     * @param query String mit Datenbankbefehlen
     * @throws DAOException 
     */
    private void executeUpdate(String query) throws DAOException {
        try {
            connection = getConnection();
            prepared_statement = connection.prepareStatement(query);
            prepared_statement.executeUpdate();
        } catch (DAOException | SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Query string is incorrect");
        }
    }
    
    
    
    /**
     * Neuer Datensatz zu Datenbank hinzufuegen
     * @param query
     * @return
     * @throws DAOException 
     */
    private int executeInsert(String query) throws DAOException {
        PreparedStatement statement;
        //Statement statement;
        ResultSet generatedKeys;
        int key = 0;

        try {
            statement = getConnection().prepareStatement(query, PreparedStatement.RETURN_GENERATED_KEYS);
            statement.executeUpdate();

            /* Key ermitteln */
            /** @TODO EXCEPTION java.sql.SQLException: Generated keys not requested. You need to specify Statement.RETURN_GENERATED_KEYS to Statement.executeUpdate() or Connection.prepareStatement().*/
            generatedKeys = statement.getGeneratedKeys();
            if(generatedKeys.next()){
                key = generatedKeys.getInt(1);    
            }

            statement.close();
        }
        catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Cannot insert datas into the database");
        }

        return key;
    }
    
    /**
     * Kontrolle ob noch Daten im Set vorhanden sind
     * @param result
     * @return true -> noch Daten vorhanden
     * @throws DAOException 
     */
    private boolean nextDataSet(ResultSet result) throws DAOException{
        try {
            return result.next();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Can't change database");
        }
    }
    
    
    /**
     * Neue Verbindung zu Datenbank aufbauen
     * @return Verbindungsdaten
     * @throws DAOException
     * @throws SQLException 
     */
    private Connection getConnection() throws DAOException, SQLException {
        
        return MySQLDAOFactory.getInstance().getConnection();
    }
    
    
    /**
     * Verbindung zu Datenbank schliessen
     * @throws DAOException 
     */
    private void closeConnection() throws DAOException { 
        try {
            if(connection != null)
               connection.close();
            if(prepared_statement != null) 
                prepared_statement.close();
            if(result != null)
                result.close();
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Can't close connection");
        }
    }
    
    
    private void closeResult(ResultSet result) throws DAOException {
    	try {
            result.close();
        } catch (SQLException e) {
            e.printStackTrace();
            throw new DAOException("Result closeing error");
        }
    }
    
    
    /**
     * Ergebnisse aus dem Set in DTO ablegen
     * @param result Ergebnis Set
     * @return DTO
     * @throws DAOException 
     */
    private GroupDTO getGroupDTO(ResultSet result) throws DAOException {
        GroupDTO group = new GroupDTO();
        
        try {    
            group.group_id = result.getInt("group_id");
            group.group_name = result.getString("name");
            return group;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Colum does not Exists!");
        }
        
       
    }
    
    
    /**
     * Einfaches DTO erzeugen
     * @return DTO
     */
    private GroupDTO getGroupDTO() {
        return new GroupDTO();
    }
    
    
    private ContactDTO getContactDTO(ResultSet result) throws DAOException{
        ContactDTO contact = new ContactDTO();
        
        try {
            contact.user_id = result.getInt("user_id");
            contact.user_prename= result.getString("prename");
            contact.user_lastname= result.getString("lastname");
            contact.user_state = result.getInt("state");
            return contact;
        } catch (SQLException ex) {
            ex.printStackTrace();
            throw new DAOException("Colum does not Exists!");
        }
    }
    
}
