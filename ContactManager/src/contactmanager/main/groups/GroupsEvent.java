
package contactmanager.main.groups;

/**
 * Events des Gruppen Paketes
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 02.05.2013
 */
public interface GroupsEvent {
    
    /* Allgemeine Angaben */
    public static final int GROUP_DEFAULT_ID = 0;

    
    /* Gruppen Handlen */
    public static final String GROUP_ADD_GROUP = "ADD";
    public static final String GROUP_REMOVE_GROUP_WITH_ID = "REMOVE_ID";
    public static final String GROUP_REMOVE_GROUP_WITH_INDEX = "REMOVE_INDEX";


    /* Property Changes */
    public static final String GROUP_LIST_SELECT_EVENT = "GROUP_LIST_SELECT";
    public static final String GROUP_SEARCH_EVENT = "GROUP_SEARCH";
    public static final String GROUP_SELECT_EVENT = "GROUP_SELECT";
    public static final String GROUP_INSERT_EVENT = "GROUP_INSERT";
    public static final String GROUP_UPDATE_EVENT = "GROUP_UPDATE";
    public static final String GROUP_DELETE_EVENT = "GROUP_DELETE";
    public static final String GROUP_SELECT_CONTACTS_EVENT = "GROUP_SELECT_CONTACTS";
    public static final String GROUP_ADD_CONTACT_EVENT = "GROUP_ADD_CONTACT";
    public static final String GROUP_DELETE_CONTACT_EVENT = "GROUP_DELETE_CONTACT";    
}
