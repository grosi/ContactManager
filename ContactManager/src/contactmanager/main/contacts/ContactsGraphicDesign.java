
package contactmanager.main.contacts;

import java.awt.event.KeyEvent;

/**
 * Grafikvorgaben des Kontakt Paketes
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public interface ContactsGraphicDesign {
    
    /** Allgemeine Angaben */
    public static final String CONTACTS_TITLE = "Kontakte";
    
    /** Bilder Dateipfad */
    public static final String IMAGES_FILEPATH = "/contactmanager/main/graphic/images/";
    
    
    /** Dimensionen */
    // Linke Spalte
    public static final int CONTACT_TAB_GROUPLIST_MIN_WIDTH = 250;
    public static final int CONTACT_TAB_GROUPLIST_MIN_HEIGHT = 200;
    
    //Rechte Spalte
    public static final int CONTACT_TAB_GROUPDETAIL_MIN_WIDTH = 250;
    public static final int CONTACT_TAB_GROUPDETAIL_MIN_HEIGHT = 200;
    
    
    /** Schaltflaechen Shortcuts */
    public static final int CONTACT_TAB_MESSAGE_MNEMONIC = KeyEvent.VK_M;
    public static final int CONTACT_TAB_SAVE_MNEMONIC = KeyEvent.VK_S;
    public static final int CONTACT_TAB_ADD_MNEMONIC = KeyEvent.VK_A;
    public static final int CONTACT_TAB_REMOVE_MNEMONIC = KeyEvent.VK_R;
    
    
    /** Tool Tips */
    public static final String CONTACT_TAB_MESSAGE_TOOLTIP = "E-Mail erstellen";
    public static final String CONTACT_TAB_SAVE_TOOLTIP = "Kontakt speichern";
    public static final String CONTACT_TAB_ADD_TOOLTIP = "Neuer Kontakt erstellen";
    public static final String CONTACT_TAB_REMOVE_TOOLTIP = "Kontakt löschen";
    
    
    /** Labels */
    public static final String CONTACT_TAB_NAME_LABEL = "Name";
    public static final String CONTACT_TAB_PRENAME_LABEL = "Vorname";
    public static final String CONTACT_TAB_EMAIL_LABEL = "E-Mail Adresse";
    public static final String CONTACT_TAB_ADRESS_LABEL = "Adresse";
    public static final String CONTACT_TAB_PHONE_LABEL = "Telefonnummer";
    public static final String CONTACT_TAB_GROUP_LABEL = "Gruppen wählen";
    
    
    /** Textfelder */
    public static final String CONTACT_TAB_DEFAULT_SEARCH_TEXT = "Suchbegriff eingeben...";
    public static final String CONTACT_TAB_DEFAULT_NAME_TEXT = "Name eingeben";
    public static final String CONTACT_TAB_DEFAULT_PRENAME_TEXT = "Vorname eingeben";
    public static final String CONTACT_TAB_DEFAULT_EMPTYNAME_TEXT = "Keine Kontakte vorhanden";
    public static final String CONTACT_TAB_DEFAULT_EMAIL_TEXT = "E-Mail Adresse eingeben";
    public static final String CONTACT_TAB_DEFAULT_PHONE_TEXT = "Telefonnummer eingeben";
    public static final String CONTACT_TAB_DEFAULT_STREET_TEXT = "Strasse";
    public static final String CONTACT_TAB_DEFAULT_CODE_TEXT = "PLZ";
    public static final String CONTACT_TAB_DEFAULT_CITY_TEXT = "Stadt/Ort";
    public static final String CONTACT_TAB_DEFAULT_COUNTRY_TEXT = "Land";
    
    
}
