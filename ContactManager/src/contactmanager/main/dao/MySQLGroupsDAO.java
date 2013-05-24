
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.groups.GroupDTO;
import contactmanager.main.groups.GroupsDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

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

        result = executeQuery("SELECT `group_id`, `name` FROM `group` WHERE `name` LIKE '%e%' ORDER BY `name` ASC");

        while(nextDataSet(result)) {
            groups.add(getGroupDTO(result));
        }
        //closeConnection();

        return groups;
    }

    @Override
    public ArrayList<GroupDTO> searchGroupList(String search_pattern) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public GroupDTO selectGroup(int group_id) throws DAOException {
        ArrayList<ContactDTO> contacts;
        GroupDTO group =null;

        result = executeQuery("SELECT `group_id`, `name` FROM `group` WHERE `group_id`="+group_id);
        nextDataSet(result);
        group = getGroupDTO(result);
        
        contacts = selectContactsFromGroup(group.group_id);
        group.group_contacts = contacts;
        
        //closeConnection();
        
        return group;
    }

    @Override
    public int insertGroup(GroupDTO insert_group) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateGroup(GroupDTO update_group) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteGroup(int group_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ContactDTO> selectContactsFromGroup(int group_id) throws DAOException {
        ArrayList<ContactDTO> contacts = new ArrayList<>();
        
        result = executeQuery("SELECT u.* FROM `user` AS u, `user2group` AS c WHERE c.group_id = "+group_id+" AND u.user_id = c.user_id ORDER BY u.prename ASC, u.lastname ASC");
         
//        SELECT u . *
//FROM `user` AS u, `user2group` AS c
//WHERE c.group_id =1
//AND u.user_id = c.user_id
//ORDER BY u.prename ASC , u.lastname ASC
        
        while(nextDataSet(result)) {
            contacts.add(getContactDTO(result));
        }
        //closeConnection();
        
        return contacts;
    }

    @Override
    public boolean addGroupToContact(int group_id, int contact_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteGroupFromContact(int group_id, int contact_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
