
package contactmanager.main.groups;

import contactmanager.main.AbstractController;
import contactmanager.main.AbstractView;
import contactmanager.main.SubController;
import contactmanager.main.frame.MainController;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 02.05.2013
 */
public final class GroupsController extends AbstractController implements SubController, GroupsInterface {
    
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
    }
 
    
    /***************************************************************************
     * MainController-> SubController Methoden
     **************************************************************************/
    
    /**
     * Aktuelle Datensaetze von der Datenbank holen
     */
    @Override
    public void updateData() {
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
     * Informationen zu einer Gruppe
     */
    public void getGroup() {
        int index;
        int list_size;
        GroupDTO group;
        int group_id;
        String group_name;
        
        index = groups_view.getSelectedGroupIndex(); //Selektierte Gruppe in der Ubersichtsliste
        list_size = groups_view.getGroupListSize(); //Groesse der Uerbsichtsliste
        
        /* Kontrolle ob die Selektion noch moeglich ist */
        if(index > 0 && index != list_size) {
            group_id = groups_view.getGroupIdOfIndex(index);
            group_name = groups_view.getGroupNameOfIndex(index);
            
            /* Nur gespeicherte Gruppen abfragen */
            if(group_id != GROUP_DEFAULT_ID) {
                group = getGroupDTO();
                group.group_id = group_id;
                group.group_name = group_name;
                
                /* Ungespeicherte Gruppen loeschen */
                if(groups_view.getGroupState(GROUP_DEFAULT_ID) == true)
                    groups_view.setGroupList(GROUP_DEFAULT_ID, group_name, GroupsView.GROUP_REMOVE_GROUP_WITH_ID);
                
                groups_view.setMouseWaitCursor(true);
                groups_model.getGroup(group);
                groups_view.setMouseWaitCursor(false);
            } else {
                groups_view.setMessageButtonState(false);
            }
        }
    }
    
    
    /**
     * Neue Gruppe hinzufuegen
     */
    public void addGroup() {
        
        /* Platzhalter in Gruppen-Ubersichtliste */
        groups_view.setGroupList(GROUP_DEFAULT_ID, GroupsView.GROUP_TAB_DEFAULT_NAME_TEXT, GroupsView.GROUP_ADD_GROUP);
        
        /* Detailsansicht zuruecksetzen */
        groups_view.setGroupName(GroupsView.GROUP_TAB_DEFAULT_NAME_TEXT);
        groups_view.setContactListEmpty();

        groups_view.setSaveButtonState(false);
    }
    
    
    /**
     * Gruppe loeschen
     */
    public void removeGroup() {
        GroupDTO group;
        int group_id;
        String group_name;
        int index;
        
        /* Selektierte Gruppe */
        index = groups_view.getSelectedGroupIndex();
        
        /* Kein Eintrag in Uebersicht selektiert */
        if(index == -1)
            return;
        
        group_id = groups_view.getGroupIdOfIndex(index);
        group_name = groups_view.getGroupNameOfIndex(index);
        
        /* Ungespeicherte Gruppe */
        if(group_id == GROUP_DEFAULT_ID) {
            groups_view.setGroupList(group_id, group_name, GroupsView.GROUP_REMOVE_GROUP_WITH_ID);
            selectGroup();
        }
        /* Gespeicherte Gruppe */    
        else {
            group = getGroupDTO();
            group.group_id = group_id;
            group.group_name = group_name;
            groups_view.setMouseWaitCursor(true);
            groups_model.removeGroup(group);
            groups_view.setMouseWaitCursor(false);
        }
    }
    
    
    /**
     * Gruppe speichern
     * @param group Gruppen Data Transfer Objekt
     */
    public void saveGroup() {
        GroupDTO group;
        int group_id;
        String group_name;
        int index;
        
        /* Selektierte Gruppe */
        index = groups_view.getSelectedGroupIndex();
        
        /* Kein Eintrag in Uebersicht selektiert */
        if(index == -1)
            return;
        
        group_id = groups_view.getGroupIdOfIndex(index);
        group_name = groups_view.getGroupNameOfIndex(index);
        group = getGroupDTO();
        group.group_id = group_id;
        group.group_name = group_name;
        
        /* Gruppe existiert noch nicht in Datenbank */
        groups_view.setMouseWaitCursor(true);
        if(group_id == GROUP_DEFAULT_ID) 
            groups_model.addGroup(group);
        /* Gespeicherte Gruppe */    
        else 
            groups_model.saveGroup(group);
        groups_view.setMouseWaitCursor(false);
    }
    
    
    /**
     * Suche angewaehlt
     */
    public void searchGroupFocusGained() {
        String text;
        
        text = groups_view.getSearchText();
        
        /* Falls noch keine Anpassungen vorherrschen, alles markieren */
        if(text.equals(GroupsView.GROUP_TAB_DEFAULT_SEARCH_TEXT) == true)
            groups_view.setSearchSelection(0, text.length());
    }
    
    
    /**
     * Suche abgewaehlt
     */
    public void searchGroupFocusLost() {
        
        groups_view.setSearchSelection(0, 0); //Nichts Selektieren
        
        /* Wenn die Eingabemaske leer ist, Standardtext einblenden */
        if(groups_view.getSearchText().equals(""))
            groups_view.setSearchText(GroupsView.GROUP_TAB_DEFAULT_SEARCH_TEXT);
    }
    
    /**
     * Nach Gruppen suchen
     */
    public void searchGroup() {
        String text;
        
        text = groups_view.getSearchText(); //Suchmuster
        
        /* Nur suchen, wenn nicht der Standardtext steht */
        if(text.equals(GroupsView.GROUP_TAB_DEFAULT_SEARCH_TEXT) == false) {
            groups_view.setMouseWaitCursor(true);
            groups_model.searchGroup(text);
            groups_view.setMouseWaitCursor(false);
        }
    }
    
    
    /**
     * Nachricht an alle Gruppen-Mitglieder senden
     * @param group Gruppen Data Transfer Objekt
     */
    public void sendMessage() {
        int index;
        int list_size;
        GroupDTO group;
        int group_id;
        String group_name;
        
        index = groups_view.getSelectedGroupIndex(); //Selektierte Gruppe in der Ubersichtsliste
        list_size = groups_view.getGroupListSize(); //Groesse der Uerbsichtsliste
        
        if(getEmailClientState() == true) {
        /* Kontrolle ob die Selektion noch moeglich ist */
        if(index > 0 && index != list_size) {
            group_id = groups_view.getGroupIdOfIndex(index);
            group_name = groups_view.getGroupNameOfIndex(index);
            
            /* Nur gespeicherte Gruppen abfragen */
            if(group_id != GROUP_DEFAULT_ID) {
                group = getGroupDTO();
                group.group_id = group_id;
                group.group_name = group_name;
                groups_model.sendMessage(group);
            }
        }
        }
    }
    
    
    
    
    
    /**
     * Gruppen-Name angewaehlt
     */
    public void nameGroupFocusGained() {
        String group_name;
        
        group_name = groups_view.getGroupName();
        
        if(group_name.equals(GroupsView.GROUP_TAB_DEFAULT_NAME_TEXT) == true) 
            groups_view.setGroupNameSelection(0, group_name.length());
    }
    
    
    /**
     * Gruppen-Name abgewaehlt
     */
    public void nameGroupFocusLost() {
        groups_view.setGroupNameSelection(0,0);
    }
    
    
    /**
     * Name der Gruppe wurde geaendert -> Kontrolle ob Sicherung moeglich
     */
    public void nameGroupChange() {
        int index;
        int list_size;
        int group_id;
        String group_name_old;
        String group_name_new;
        
        /* Informationen holen */
        index = groups_view.getSelectedGroupIndex();
        list_size = groups_view.getGroupListSize();
       
        /* Gruppenliste nur aendern wenn die Pausabilitaeten erfuellt sind */
        if(index > 0 && index != list_size) {
                group_name_new = groups_view.getGroupName();
                group_name_old = groups_view.getGroupNameOfIndex(index);
                group_id = groups_view.getGroupIdOfIndex(index);
                
                if(group_name_new.equals("") == false && group_name_new.equals(group_name_old) == false) {//&& 
                    groups_view.setGroupList(group_id, group_name_new, GroupsView.GROUP_ADD_GROUP);
                    groups_view.setSaveButtonState(true);
                } else {
                    groups_view.setSaveButtonState(false);
                }
        } 
    }
    
    
    /**
     * Erste Gruppe in Ubersichtsliste selektieren
     */
    public void selectGroup() {
        int group_quantity;
 
        group_quantity = groups_view.getGroupQuantity();
        
        /* Falls die Liste nicht leer ist, erster Eintrag selektieren */
        if(group_quantity > 0)
            groups_view.setSelectedGroupIndex(1);
    }
    
    
    /**
     * Email-Moeglichkeit kontrollieren
     */
    public boolean getEmailClientState() {
        return main_controller.getEmailClientStatus();
    }
    
    /**
     * Nachricht an entsprechende Empfaenger schreiben
     * @param email E-Mail Adressen
     */
    public void sendEmail(String email) {
        main_controller.sendEmail(email);
    }
    
    
    /***************************************************************************
     * Controller Methoden
     **************************************************************************/
    /**
     * Erzeugt ein Transfer Objekt
     * @return Transfer Objekt
     */
    private GroupDTO getGroupDTO() {
        return new GroupDTO();
    }
}

    

