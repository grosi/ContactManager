
package contactmanager.main.groups;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public interface GroupsDAO {
    
    public Group getGroup(int id);
    public boolean deleteGroup(int id);
    public boolean storeGroup(Group group);
    
}
