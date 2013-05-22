
package contactmanager.main.groups;

import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public final class GroupsModel extends AbstractModel implements GroupsInterface {
    
    private GroupsController controller;
    private GroupsDAO groupsdao;
    
    public GroupsModel(GroupsController controller) {
        super();
        
        this.controller = controller;
        
        //this.groupsdao = controller.getMainController().getDAOFactory().getGroupsDAO();   
    }
    
    
    /**
     * Alle vorhanden Gruppen der Datenbank einlesen
     */
    public void getGroupList() {
        
        ArrayList<GroupDTO> group_list;
        
        try {
            group_list = groupsdao.selectGroupList();
        } catch (DAOException ex) {
            group_list = null;
        }
        
        firePropertyChange(GROUP_LIST_SELECT_EVENT, group_list, group_list);
    }
    
    
    /**
     * Eine spezifische Gruppe der Datenbank einlesen
     * @param group Gruppen Data Transfer Objekt
     */
    public void getGroup(GroupDTO group) {
        
        try {
            group = groupsdao.selectGroup(group.group_id);
        } catch (DAOException ex) {
            group = null;
        }
        
        firePropertyChange(GROUP_SELECT_EVENT, group, group);
    }
    
    
    /**
     * Neue Gruppe zu Datenbank hinzufuegen
     * @param group Gruppen Data Transfer Objekt
     */
    public void addGroup(GroupDTO group) {

        try {
            group.group_id = groupsdao.insertGroup(group);
        } catch (DAOException ex) {
            group = null;
        }
        
        firePropertyChange(GROUP_INSERT_EVENT, group, group);
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
    
    
    /**
     * Bestehende Gruppe in der Datenbank speichern (Aktualisieren)
     * @param group
     */
    public void saveGroup(GroupDTO group) {
        try {
            groupsdao.updateGroup(group);
        } catch (DAOException ex) {
            group = null;
        }
        
        firePropertyChange(GROUP_UPDATE_EVENT, group, group);
    }

    
}
