
package contactmanager.main.groups;

import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.dao.DAOException;
import java.util.ArrayList;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public interface GroupsDAO {
    
    /* Gruppenlisten */
    public ArrayList<GroupDTO> selectGroupList() throws DAOException;
    public ArrayList<GroupDTO> searchGroupList(String search_pattern) throws DAOException;
    
    /* Schnittstelle der einzelnen Kontakte */
    public GroupDTO selectGroup(int group_id) throws DAOException;
    public int insertGroup(GroupDTO insert_group) throws DAOException;
    public boolean updateGroup(GroupDTO update_group) throws DAOException;
    public boolean deleteGroup(int group_id) throws DAOException;
    
    /* Schnittstelle der Kontaktzugehoerigkeit */
    public ArrayList<ContactDTO> selectContactsFromGroup(int group_id) throws DAOException;
    public boolean addGroupToContact(int group_id, int contact_id) throws DAOException;
    public boolean deleteGroupFromContact(int group_id, int contact_id) throws DAOException;
    
}
