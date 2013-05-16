
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.groups.GroupDTO;
import contactmanager.main.groups.GroupsDAO;
import java.util.ArrayList;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public class MySQLGroupsDAO implements GroupsDAO {
    
    
    public MySQLGroupsDAO() {}

    @Override
    public ArrayList<GroupDTO> selectGroupList() throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<GroupDTO> searchGroupList(String search_pattern) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ContactDTO selectGroup(int group_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean insertGroup(ContactDTO insert_group) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean updateGroup(ContactDTO update_group) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteGroup(int group_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ArrayList<ContactDTO> selectContactsFromGroup(int group_id) throws DAOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
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
    
}
