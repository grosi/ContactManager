
package contactmanager.main.groups;

import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public class GroupsModel extends AbstractModel implements GroupsInterface {
    
    private GroupsController controller;
    private GroupsDAO groupsdao;
    
    public GroupsModel(GroupsController controller) {
        super();
        
        this.controller = controller;
        
        //this.groupsdao = controller.getMainController().getDAOFactory().getGroupsDAO();
           
    }
    
    
    /**
     * Entfernt eine Gruppe
     * @param group Gruppen Data Transfer Objekt
     */
    public void removeGroup(GroupDTO group) {
        
        try {
            groupsdao.deleteGroup(group.group_id);
        } catch (DAOException ex) {
            group = null;
        }
        
        firePropertyChange(GROUP_DELETE_EVENT, group, group);    
    }

    
}
