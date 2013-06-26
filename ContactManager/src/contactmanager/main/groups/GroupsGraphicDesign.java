
package contactmanager.main.groups;

import java.awt.event.KeyEvent;

/**
 * Grafikvorgaben des Gruppen Paketes
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public interface GroupsGraphicDesign {
    
    /** Allgemeine Angaben */
    public static final String GROUPS_TITLE = "Gruppen";
    
    
    /** Bilder Dateipfad */
    public static final String IMAGES_FILEPATH = "/contactmanager/main/graphic/images/";
    
    /** Dimensionen */
    // Linke Spalte
    public static final int GROUP_TAB_GROUPLIST_MIN_WIDTH = 250;
    public static final int GROUP_TAB_GROUPLIST_MIN_HEIGHT = 200;
    
    //Rechte Spalte
    public static final int GROUP_TAB_GROUPDETAIL_MIN_WIDTH = 250;
    public static final int GROUP_TAB_GROUPDETAIL_MIN_HEIGHT = 200;
    
    
    /** Schaltflaechen Shortcuts */
    public static final int GROUP_TAB_MESSAGE_MNEMONIC = KeyEvent.VK_M;
    public static final int GROUP_TAB_SAVE_MNEMONIC = KeyEvent.VK_S;
    public static final int GROUP_TAB_ADD_MNEMONIC = KeyEvent.VK_A;
    public static final int GROUP_TAB_REMOVE_MNEMONIC = KeyEvent.VK_R;
    
    
    /** Tool Tips */
    public static final String GROUP_TAB_MESSAGE_TOOLTIP = "E-Mail erstellen";
    public static final String GROUP_TAB_SAVE_TOOLTIP = "Gruppe speichern";
    public static final String GROUP_TAB_ADD_TOOLTIP = "Neue Gruppe erstellen";
    public static final String GROUP_TAB_REMOVE_TOOLTIP = "Gruppe löschen";
    
    
    /** Labels */
    public static final String GROUP_TAB_GROUPMEMBER_LABEL = "Gruppen-Mitglieder";
    public static final String GROUP_TAB_GROUPOVERVIEW_LABEL = "Gruppen-Übersicht";
    
    
    /** Textfelder */
    public static final String GROUP_TAB_DEFAULT_SEARCH_TEXT = "Suchbegriff eingeben...";
    public static final String GROUP_TAB_DEFAULT_NAME_TEXT = "Gruppen-Name";
}
