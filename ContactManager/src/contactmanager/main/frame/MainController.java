
package contactmanager.main.frame;

import contactmanager.main.contacts.ContactsController;
import contactmanager.main.AbstractController;
import contactmanager.main.dao.DAOFactory;
import contactmanager.main.groups.GroupsController;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class MainController extends AbstractController implements MainInterface {
    
    private MainFrame main_frame;
    private MainModel main_model;
    
    /** Subcontroller */
    private ContactsController contacts_controller;
    private GroupsController groups_controller;
    
    private boolean email_client_available;
    
    public MainController() {
        
        super();
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        main_model = new MainModel();
        addModel(main_model);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        main_frame = new MainFrame(this);
        addView(main_frame);
        
        /* E-Mail Client ueberpruefen */
        email_client_available = main_model.checkMailClient();
        
        /* Alle SubController */
        this.contacts_controller = new ContactsController(this);
        this.groups_controller = new GroupsController(this);    
    }

    /***************************************************************************
     * Subcontroller Methoden
     **************************************************************************/
    
    /**
     * Gibt Referenz auf das Hauptfenster zurueck
     * @return
     * @TODO Kapselung verbessern
     */
    public MainFrame getMainFrame() {
        return main_frame;
    }
    
    
    /**
     * Referenz auf DAO Factory
     * @return Referenz
     */
    public DAOFactory getDAOFactory() {
        return main_model.getDAOFactory();
    }
    
    
    /**
     * Status des E-Mail Clients des Betriebssystems
     * @return true -> Client vorhanden
     */
    public boolean getEmailClientStatus() {
        return email_client_available;
    }
    
    
    /**
     * E-Mail senden
     * @param email String mit E-Mail Adressen, getrennt durch ','
     * @return true -> Client bereit
     */
    public boolean sendEmail(String email) {
        return main_model.sendMail(email);
    }
    
    
    /***************************************************************************
     * GUI -> Model Methoden, inkl. Exception Handling
     **************************************************************************/
    
    /**
     * Anderer Tab auswaehlen 
     * @param tabName
     */
    public void changeTabSelection(String tabName) {
        main_model.tabChange(tabName);
        System.err.println("changeTabSelection");
    }

    
    /**
     * Programm beenden
     */
    public void closeApplication() {
        main_model.closeApplication();
    }
    
}
