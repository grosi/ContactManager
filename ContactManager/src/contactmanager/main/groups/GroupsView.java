
package contactmanager.main.groups;

import contactmanager.main.AbstractView;
import contactmanager.main.GraphicDesign;
import java.beans.PropertyChangeEvent;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public class GroupsView extends AbstractView implements GraphicDesign{
    
    private GroupsController controller;

    
    /**
     * View default Konstruktor
     * @param controller
     */
    public GroupsView(GroupsController controller) {
        super(controller.getMainController().getMainFrame());
        this.controller = controller;
        
        initComponents();

        mainFrame.addTab(GroupsController.GROUPS_TITLE, this);
    }
    
    
    
    
    @Override
    protected void initComponents() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    /***************************************************************************
     * View -> Controller
     **************************************************************************/
    
    
    
    
    /***************************************************************************
     * Model -> View
     **************************************************************************/
    /**
     * Aenderungen aus dem Model in View uebernehmen
     * @param evt 
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
