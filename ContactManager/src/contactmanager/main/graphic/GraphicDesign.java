/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactmanager.main.graphic;

import java.awt.Color;
import java.awt.event.KeyEvent;

/**
 * Globale Definitionen, die die UI beeinflussen
 * @author grosi
 * @version 0.1
 * @since 27.03.2013
 */
public interface GraphicDesign {
    
    /** Bilder Dateipfad */
    public static final String IMAGES_FILEPATH = "src/contactmanager/main/graphic/images/";
    
    /** Farben */
    public static final Color TAB_BACKGROUND_COLOR = new Color(179, 164, 164, 255);
    
    
    /** Dimensionen */
    /* Frame */
    public static final int FRAME_MIN_WIDTH = 800;
    public static final int FRAME_MIN_HEIGHT = 500;
    
    /* Gruppen Tab */
    // Linke Spalte
    public static final int GROUP_TAB_GROUPLIST_MIN_WIDTH = 250;
    public static final int GROUP_TAB_GROUPLIST_MIN_HEIGHT = 200;
    
    //Rechte Spalte
    public static final int GROUP_TAB_GROUPDETAIL_MIN_WIDTH = 250;
    public static final int GROUP_TAB_GROUPDETAIL_MIN_HEIGHT = 200;
    
    
    
    /** Schaltflaechen Shortcuts */
    
    /* Kontake Tab */
    
    /* Gruppen Tab */
    public static final int GROUP_TAB_MESSAGE_MNEMONIC = KeyEvent.VK_M;
    public static final int GROUP_TAB_SAVE_MNEMONIC = KeyEvent.VK_S;
    public static final int GROUP_TAB_ADD_MNEMONIC = KeyEvent.VK_A;
    public static final int GROUP_TAB_REMOVE_MNEMONIC = KeyEvent.VK_R;
    
    
    
    /** Tool Tips */
    
    /* Gruppen Tab */
    public static final String GROUP_TAB_MESSAGE_TOOLTIP = "E-Mail erstellen";
    public static final String GROUP_TAB_SAVE_TOOLTIP = "Gruppe speichern";
    public static final String GROUP_TAB_ADD_TOOLTIP = "Neue Gruppe erstellen";
    public static final String GROUP_TAB_REMOVE_TOOLTIP = "Gruppe löschen";
    
    /* Kontakte Tab */
    public static final String CONTACT_TAB_MESSAGE_TOOLTIP = "E-Mail erstellen";
    public static final String CONTACT_TAB_SAVE_TOOLTIP = "Kontakt speichern";
    public static final String CONTACT_TAB_ADD_TOOLTIP = "Neuer Kontakt erstellen";
    public static final String CONTACT_TAB_REMOVE_TOOLTIP = "Kontakt löschen";
    
    
    /** Labels */
    
    
    /* Gruppen Tab */
    public static final String GROUP_TAB_GROUPMEMBER_LABEL = "Gruppen-Mitglieder";
    public static final String GROUP_TAB_GROUPOVERVIEW_LABEL = "Gruppen-Übersicht";
    
    /* Kontakte Tab */
    public static final String CONTACT_TAB_NAME_LABEL = "Name";
    public static final String CONTACT_TAB_PRENAME_LABEL = "Vorname";
    public static final String CONTACT_TAB_EMAIL_LABEL = "E-Mail Adresse";
    public static final String CONTACT_TAB_ADRESS_LABEL = "Adresse";
    public static final String CONTACT_TAB_PHONE_LABEL = "Telefonnummer";
    public static final String CONTACT_TAB_GROUP_LABEL = "Gruppen";
    
    
    /** Textfelder */
    
    /* Gruppen Tab */
    public static final String GROUP_TAB_DEFAULT_SEARCH_TEXT = "Suchbegriff eingeben...";
    public static final String GROUP_TAB_DEFAULT_NAME_TEXT = "Gruppen-Name";
    
    
    /* Kontakte Tab */
    public static final String CONTACT_TAB_DEFAULT_SEARCH_TEXT = "Suchbegriff eingeben...";
    public static final String CONTACT_TAB_DEFAULT_NAME_TEXT = "Name eingeben";
    public static final String CONTACT_TAB_DEFAULT_PRENAME_TEXT = "Vorname eingeben";
    
    
    
    
}
