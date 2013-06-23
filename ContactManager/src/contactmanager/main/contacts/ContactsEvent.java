package contactmanager.main.contacts;

/**
 * Events des Kontakt Paketes
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 02.05.2013
 */
public interface ContactsEvent {
    
    /* Allgemeine Angaben */
    public static final int CONTACT_DEFAULT_ID = 0;
    
    /* Property Changes */
    public static final String CONTACT_LIST_SELECT_EVENT = "CONTACT_LIST_SELECT";
    public static final String CONTACT_SEARCH_EVENT = "CONTACT_SEARCH";
    public static final String CONTACT_SELECT_EVENT = "CONTACT_SELECT";
    public static final String CONTACT_INSERT_EVENT = "CONTACT_INSERT";
    public static final String CONTACT_UPDATE_EVENT = "CONTACT_UPDATE";
    public static final String CONTACT_DELETE_EVENT = "CONTACT_DELETE";
    public static final String CONTACT_SELECT_GROUPS_EVENT = "CONTACT_SELECT_GROUPS";
    public static final String CONTACT_ADD_GROUP_EVENT = "CONTACT_ADD_GROUP";
    public static final String CONTACT_DELETE_GROUP_EVENT = "CONTACT_DELETE_GROUP";
    public static final String CONTACT_ALL_GROUP_EVENT = "CONTACT_ALL_GROUP";
    public static final String CONTACT_ONE_GROUP_EVENT = "CONTACT_ONE_GROUP";
}
