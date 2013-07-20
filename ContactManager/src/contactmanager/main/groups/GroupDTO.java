
package contactmanager.main.groups;

import contactmanager.main.contacts.ContactDTO;
import java.util.ArrayList;

/**
 * Data Transfer Objekt der Gruppen
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class GroupDTO {
    
    /* Allgemeine Gruppen Daten */
    public int group_id;
    public String group_name;
    
    /* Kontakte in der Gruppe */
    public ArrayList<ContactDTO> group_contacts = new ArrayList<>();
}
