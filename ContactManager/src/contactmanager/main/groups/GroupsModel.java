
package contactmanager.main.groups;

import contactmanager.main.AbstractModel;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public class GroupsModel extends AbstractModel{
    
    private GroupsController controller;
    private GroupsDAO groupsdao;
    
    public GroupsModel(GroupsController controller) {
        super();
        
        this.controller = controller;
        //this.groupsdao = controller.getMainController().getDAOFactory().getGroupsDAO();
           
    }

    
}
