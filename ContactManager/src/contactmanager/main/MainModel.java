
package contactmanager.main;

import contactmanager.main.AbstractModel;
import contactmanager.main.MainController;

/**
 * @author grosi
 * @version 0.1
 * @since 27.03.2013
 */
public class MainModel extends AbstractModel {
    
    private String applicationTitle = null;
    
    public MainModel() {
        super();
    }
    
    
    /**
     * Anderer Tab ausgewaehlt
     * @param tabName 
     */
    public void tabChange(String tabName) {
        String oldTitle = this.applicationTitle;
        this.applicationTitle = MainController.APPLICATION_TITLE + " - " + tabName;
        firePropertyChange(MainController.CURRENT_TAB_CHANGED_EVENT, oldTitle, applicationTitle);
    }

    
    /**
     * Programm beenden
     */
    public void closeApplication() {
        System.exit(1);
    }

}
