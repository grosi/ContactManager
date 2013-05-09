
package contactmanager.main.dao;

import contactmanager.main.groups.Group;
import contactmanager.main.groups.GroupsDAO;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public class MySQLGroupsDAO implements GroupsDAO {
    
    
    public MySQLGroupsDAO() {}

    @Override
    public Group getGroup(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean deleteGroup(int id) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public boolean storeGroup(Group group) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
