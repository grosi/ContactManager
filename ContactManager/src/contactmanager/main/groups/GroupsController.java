
package contactmanager.main.groups;

import contactmanager.main.AbstractController;
import contactmanager.main.SubController;
import contactmanager.main.frame.MainController;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 02.05.2013
 */
public final class GroupsController extends AbstractController implements SubController {
    
    private MainController main_controller;

    /* Modelle */
    private GroupsModel groups_model;
    
    /* Views */
    private GroupsView groups_view;
    
    
    public GroupsController(MainController mainController) {
        super();
        
        this.main_controller = mainController;
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        groups_view = new GroupsView(this);
        addView(groups_view);
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        groups_model = new GroupsModel(this);
        addModel(groups_model);
        
        /* Daten von Datenbank abfragen */
        /** @TODO erste wenn DB funktioniert getGroupList(); */
    }
 
    
    
    /***************************************************************************
     * Model/View -> MainController Methoden
     **************************************************************************/
    
    @Override
    public MainController getMainController() {
        return this.main_controller;
    }
    
    
    
    /***************************************************************************
     * GUI -> Controller Methoden
     **************************************************************************/
    
    public void getGroupList() {
        groups_model.getGroupList();
    }
    
    
    /**
     * Infromationen zu einer Gruppe
     * @param group Gruppen Data Transfer Objekt
     */
    public void getGroup(GroupDTO group) {
        groups_model.getGroup(group);
    }
    
    
    /**
     * Neue Gruppe hinzufuegen
     */
    public void addGroup() {
        groups_view.setDetailToDefault();
        groups_view.deselectGroupList();
    }
    
    
    /**
     * Gruppe loeschen
     * @param group Gruppen Data Transfer Objekt
     */
    public void removeGroup(GroupDTO group) {
        groups_model.removeGroup(group);
        groups_view.deselectGroupList();
    }
    
    
    /**
     * Gruppe speichern
     * @param group Gruppen Data Transfer Objekt
     */
    public void saveGroup(GroupDTO group) {
        
        /* Neue Gruppe erstellen */
        if(group.group_id < 0)
            groups_model.addGroup(group);
        /* Gruppe aktualisieren */
        else
            groups_model.saveGroup(group);
    }
    
    
    
}

    

