
package contactmanager.main.frame;

import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import contactmanager.main.dao.DAOFactory;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * @author grosi
 * @version 0.1
 * @since 27.03.2013
 */
public class MainModel extends AbstractModel implements MainInterface {
    
    private DAOFactory daofactory;
    
    private String application_title = null;
    
    private Desktop os_desktop;
    
    public MainModel() {
        super();
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
     * MySQL Factory instanzieren
     */
    public void createDAOFactory() {
        try {
            this.daofactory = DAOFactory.getDAOFactory(DAOFactory.MySQL);
        } catch (DAOException ex) {
            System.err.println("Datenbank nicht erreichtbar");
        }
    }
    
    
    /**
     * Referenz auf DAO Factory
     * @return Referenz
     */
    public DAOFactory getDAOFactory() {
        return this.daofactory;
    }
    
    
    /**
     * Betriebssystem auf E-Mail Client ueberpruefen
     */
    public boolean checkMailClient() {
        
        if(Desktop.isDesktopSupported() && 
                (os_desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            System.out.println("MAIL OK");
            return true;
        } else {
            System.out.println("MAIL ERROR");
            return false;
        }
    }
    
    
    /**
     * E-Mail senden, respektive Mail-Client oeffnen
     * @param mail String mit E-Mail-Adressen der Empfaenger
     * @return true -> E-Mail sende bereit
     */
    public boolean sendMail(String mail) {
        try {
            URI uri = new URI("mailto:"+mail);
            os_desktop.mail(uri);
            return true;
        } catch (URISyntaxException | IOException ex) {
            return false;
        }
    }
    

}
