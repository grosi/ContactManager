
package contactmanager.main.frame;

import contactmanager.main.AbstractModel;
import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import contactmanager.main.dao.DAOFactory;
import contactmanager.main.dao.MySQLDAOFactory;
import contactmanager.main.frame.MainController;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author grosi
 * @version 0.1
 * @since 27.03.2013
 */
public class MainModel extends AbstractModel {
    
    private DAOFactory daofactory;
    
    private String applicationTitle = null;
    
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
    
    
    /**
     * Referenz auf DAO Factory
     * @return Referenz
     */
    public DAOFactory getDAOFactory() {
        return this.daofactory;
    }

}
