
package contactmanager.main.groups;

import contactmanager.main.AbstractController;
import contactmanager.main.AbstractView;
import contactmanager.main.SubController;
import contactmanager.main.dao.DAOFactory;
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
        
        /* Mail-Client kontrollieren und View entsprechend anpassen */
        //setMessageState();
    }
 
    /***************************************************************************
     * MainController-> SubController Methoden
     **************************************************************************/
    
    /**
     * Aktuelle Datensaetze von der Datenbank holen
     */
    @Override
    public void updateData() {
        System.out.println("Groups UPDATE"); 
        groups_model.getGroupList();
    }
    
    
    /***************************************************************************
     * Model/View -> MainController Methoden
     **************************************************************************/
    
    /**
     * View zu Frame hinzufuegen
     * @param title
     * @param view 
     * @todo evtl in AbstractController einbauen!
     */
    public void addViewToFrame(String title, AbstractView view) {
        this.main_controller.addTabToMainFrame(title, view);
    }
    
    /**
     * Spezifische DAO erstellen
     * @return GroupsDAO
     * @todo evtl in AbstractController einbauen
     */
    @Override
    public Object getDAO() {
        return this.main_controller.getDAOFactory().getGroupsDAO();
    }
    
    
    /***************************************************************************
     * View -> Controller Methoden
     **************************************************************************/
    
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
        
        groups_view.addNewGroupToList();
        groups_view.setDetailToDefault();
    }
    
    
    /**
     * Gruppe loeschen
     * @param group Gruppen Data Transfer Objekt
     */
    public void removeGroup(GroupDTO group) {
        if(group.group_id > 0)
            groups_model.removeGroup(group);
    }
    
    
    /**
     * Gruppe speichern
     * @param group Gruppen Data Transfer Objekt
     */
    public void saveGroup(GroupDTO group) {
        
        /* Neue Gruppe erstellen */
        if(group.group_id < 1)
            groups_model.addGroup(group);
        /* Gruppe aktualisieren */
        else
            groups_model.saveGroup(group);
    }
    
    
    /**
     * Suche angewaehlt
     * @param text aktueller eingetragener Gruppen-Name
     */
    public void focusGainedSearchText(String text) {
        System.out.println("FOCUS SEARCH");
        if(text.compareTo(GroupsView.GROUP_TAB_DEFAULT_SEARCH_TEXT) == 0) 
            groups_view.selectSearchText();
    }
    
    
    /**
     * Suche abgewaehlt
     * @param aktueller eingetragener Gruppen-Name
     */
    public void focusLostSearchText(String text) {
        
        if(text.compareTo(GroupsView.GROUP_TAB_DEFAULT_SEARCH_TEXT) == 0)
            groups_view.deselectSearchText();
    }
    
    /**
     * Nach Gruppen suchen
     * @param text
     */
    public void searchGroup(String text) {
        groups_model.searchGroup(text);
    }
    
    
    /**
     * Nachricht an alle Gruppen-Mitglieder senden
     * @param group Gruppen Data Transfer Objekt
     */
    public void sendMessage(GroupDTO group) {
        groups_model.sendMessage(group);
    }
    
    
    /**
     * Nachricht an entsprechende Empfaenger schreiben
     * @param email E-Mail Adressen
     */
    public void sendMessage(String email) {
        main_controller.sendEmail(email);
    }
    
    
    /**
     * Gruppen-Name angewaehlt
     * @param text aktueller eingetragener Gruppen-Name
     */
    public void focusGainedGroupName(String text) {
        
        if(text.compareTo(GroupsView.GROUP_TAB_DEFAULT_NAME_TEXT) == 0) 
            groups_view.selectGroupName();
    }
    
    
    /**
     * Gruppen-Name abgewaehlt
     * @param aktueller eingetragener Gruppen-Name
     */
    public void focusLostGroupName(String text) {
        
        if(text.compareTo(GroupsView.GROUP_TAB_DEFAULT_NAME_TEXT) == 0)
            groups_view.deselectGroupName();
    }
    
    
    /**
     * Name der Gruppe wurde geaendert
     * @param oldname alter Gruppen-Name
     * @param newname neuer Gruppen-Name
     * @TODO Noch, wenn vorhanden, Gruppen DTO mitteilen
     */
    public void changeGroupName(String group_newname, String group_oldname, int group_id) {
        if(group_newname == null || group_newname.equals("")|| group_newname.equals(group_oldname)) {
            //groups_view.addNewGroupToList();
            groups_view.enableSaveButton(false);
        } else {
            groups_view.enableSaveButton(true);
            groups_view.updateGroupToList(group_newname, group_id);
        }
    }
    
    
    /**
     * Email-Moeglichkeit kontrollieren
     */
    public boolean getMessageState() {
        return main_controller.getEmailClientStatus();
//        if(main_controller.getEmailClientStatus())
//            groups_view.enableMessageButton(true);
//        else
//            groups_view.enableMessageButton(false);
    }
    
    
    
    /***************************************************************************
     * Model -> Controller Methoden
     **************************************************************************/
}

    

