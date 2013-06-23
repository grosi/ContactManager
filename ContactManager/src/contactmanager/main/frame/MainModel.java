
package contactmanager.main.frame;

import contactmanager.main.AbstractModel;
import contactmanager.main.dao.DAOException;
import contactmanager.main.dao.DAOFactory;
import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


/**
 * Haupt-Modell der Applikation
 * @author grosi
 * @version 0.1
 * @since 27.03.2013
 */
public class MainModel extends AbstractModel implements MainEvent {
    
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
        this.application_title = MainFrame.APPLICATION_TITLE + " - " + tabName;
        
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
     * @todo Testen
     */
    public DAOFactory getDAOFactory() throws DAOException{
        
        return DAOFactory.getDAOFactory(DAOFactory.MySQL);
    }
    
    
    /**
     * Betriebssystem auf E-Mail Client ueberpruefen
     */
    public boolean checkMailClient() {
        
        if(Desktop.isDesktopSupported() && 
                (os_desktop = Desktop.getDesktop()).isSupported(Desktop.Action.MAIL)) {
            return true;
        } else
            return false;
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
