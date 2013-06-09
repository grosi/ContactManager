
package contactmanager.main.groups;

import contactmanager.main.contacts.ContactDTO;
import java.util.ArrayList;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public class GroupDTO {
    
    /* Allgemeine Gruppen Daten */
    public int group_id;
    public String group_name;
    
    /* Kontakte in der Gruppe */
    public ArrayList<ContactDTO> group_contacts = new ArrayList<>();
}
