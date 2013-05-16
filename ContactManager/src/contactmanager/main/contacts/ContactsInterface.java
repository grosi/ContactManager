package contactmanager.main.contacts;

/**
 *
 * @author grosi
 */
public interface ContactsInterface {
    
    /* Allgemeine Angaben */
    public static final String CONTACTS_TITLE = "Kontakte";
    
    /* Property Changes */
    public static final String CONTACT_LIST_SELECT_EVENT = "CONTACT_LIST_SELECT";
    public static final String CONTACT_SEARCH_EVENT = "CONTACT_SEARCH";
    public static final String CONTACT_SELECT_EVENT = "CONTACT_SELECT";
    public static final String CONTACT_INSERT_EVENT = "CONTACT_INSERT";
    public static final String CONTACT_UPDATE_EVENT = "CONTACT_UPDATE";
    public static final String CONTACT_DELETE_EVENT = "CONTACT_DELETE";
    public static final String CONTACT_SELECT_CONTACTS_EVENT = "CONTACT_SELECT_GROUPS";
    public static final String CONTACT_ADD_CONTACT_EVENT = "CONTACT_ADD_GROUP";
    public static final String CONTACT_DELETE_CONTACT_EVENT = "CONTACT_DELETE_GROUP";    
}
