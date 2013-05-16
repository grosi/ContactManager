
package contactmanager.main.frame;

import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import contactmanager.main.dao.DAOFactory;

/**
 * @author grosi
 * @version 0.1
 * @since 27.03.2013
 */
public class MainModel extends AbstractModel implements MainInterface {
    
    private DAOFactory daofactory;
    
    private String application_title = null;
    
    public MainModel() {
        super();
        
        try {
            daofactory = DAOFactory.getDAOFactory(DAOFactory.MySQL);
        } catch (DAOException ex) {
            System.err.println("Datenbank nicht erreichtbar");
        }
    }
    
    
    /**
     * Anderer Tab ausgewaehlt
     * @param tabName 
     */
    public void tabChange(String tabName) {
        String oldTitle = this.application_title;
        this.application_title = MainController.APPLICATION_TITLE + " - " + tabName;
        firePropertyChange(CURRENT_TAB_CHANGED_EVENT, oldTitle, application_title);
    }

    
    /**
     * Programm beenden
     */
    public void closeApplication() {
        System.exit(1);
    }
    
    
    /**
     * Referenz auf DAO Factory
     * @return Referenz
     */
    public DAOFactory getDAOFactory() {
        return this.daofactory;
    }

}
