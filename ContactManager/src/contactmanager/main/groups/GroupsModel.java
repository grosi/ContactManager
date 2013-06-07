
package contactmanager.main.groups;

import contactmanager.main.AbstractModel;
import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.dao.DAOException;
import java.util.ArrayList;

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
        
        this.groupsdao = (GroupsDAO)controller.getDAO();
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
        
        firePropertyChange(GROUP_LIST_SELECT_EVENT, null, group_list);
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
        
        firePropertyChange(GROUP_SELECT_EVENT, null, group);
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
        
        firePropertyChange(GROUP_INSERT_EVENT, null, group);
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
        
        firePropertyChange(GROUP_DELETE_EVENT, null, group);    
    }
    
    
    /**
     * Bestehende Gruppe in der Datenbank speichern (Aktualisieren)
     * @param group Gruppen Data Transfer Objekt
     */
    public void saveGroup(GroupDTO group) {
        try {
            groupsdao.updateGroup(group);
        } catch (DAOException ex) {
            group = null;
        }
        
        firePropertyChange(GROUP_UPDATE_EVENT, null, group);
    }
    
    
    /**
     * Nach bestehenden Gruppen suchen, die zu dem angegeben Text passen
     * @param text
     */
    public void searchGroup(String text) {
        ArrayList<GroupDTO> group_list;
        try {
            group_list = groupsdao.searchGroupList(text);
        } catch (DAOException ex) {
            group_list = null;
        }
        
        firePropertyChange(GROUP_SEARCH_EVENT, null, group_list);
    }
    
    
    /**
     * E-Mail Adressen der Gruppen-Mitglieder einlesen
     * @param group Gruppen Data Transfer Objekt
     * @TODO E-Mail Prioritaeten beruecksichtigen
     */
    public void sendMessage(GroupDTO group) {
        
        try {
            String email = null;
            group = groupsdao.selectGroup(group.group_id);
            
            for(ContactDTO groupmember : group.group_contacts) {
                if(groupmember.contact_email.get(0) != null)
                    email += groupmember.contact_email.get(0).email_adress;
            }
            
            controller.sendMessage(email);
        } catch (DAOException ex) {
            
        }
    }

    
}
