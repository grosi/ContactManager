
package contactmanager.main.frame;

import contactmanager.main.contacts.ContactsController;
import contactmanager.main.AbstractController;
import contactmanager.main.AbstractView;
import contactmanager.main.SubController;
import contactmanager.main.dao.DAOException;
import contactmanager.main.dao.DAOFactory;
import contactmanager.main.groups.GroupsController;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class MainController extends AbstractController implements MainInterface {

    /* View */
    private MainFrame main_frame;
    
    /* Modell */
    private MainModel main_model;

    /* Subcontroller */
    private ArrayList<SubController> sub_controller;

    public MainController() {

        super();

        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        main_model = new MainModel();
        addModel(main_model);

        /* Views eintragen -> werden durch Modelle aktualisiert */
        main_frame = new MainFrame(this);
        addView(main_frame);

        /* Alle SubController */
        sub_controller = new ArrayList<>();
        sub_controller.add(new ContactsController(this));
        sub_controller.add(new GroupsController(this));
        
        updataData();
    }

    
    /***************************************************************************
     * SubController -> MainController Methoden
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
     * Fuegt einen neuen Tab zum Hauptfenster hinzu
     * @param name Name des neuen Tabs
     * @param view View des neuen Tabs
     * @todo Testen!!!
     */
    public void addTabToMainFrame(String name, AbstractView view) {
        main_frame.setTab(name, view);
    }


    /**
     * Referenz auf DAO Factory
     * @return Referenz
     */
    public DAOFactory getDAOFactory() {
        DAOFactory daofactory = null;
        try {
            daofactory = main_model.getDAOFactory();
        } catch (DAOException ex) {
            setStatusBar("Datenbank nicht erreichbar");
        } finally {
            return daofactory;
        }
    }


    /**
     * Status des E-Mail Clients des Betriebssystems
     * @return true -> Client vorhanden
     */
    public boolean getEmailClientStatus() {
        return main_model.checkMailClient();
    }


    /**
     * E-Mail senden
     * @param email String mit E-Mail Adressen, getrennt durch ','
     * @return true -> Client bereit
     */
    public boolean sendEmail(String email) {
        return main_model.sendMail(email);
    }
    
    
    /**
     * Status schreiben
     * @param text Status-Text
     */
    public void setStatusBar(String text) {
        main_frame.setStatusBar(text);
    }


    /***************************************************************************
     * GUI -> Model Methoden, inkl. Exception Handling
     **************************************************************************/

    /**
     * Anderer Tab auswaehlen
     * @param tabName
     * @todo testen
     */
    public void changeTabSelection(String tabName) {
        main_model.tabChange(tabName);
        setStatusBar(tabName + " Tab ausgewaehlt");
        
        /* Daten aller SubController aktualisieren */
        if(sub_controller != null) 
            updataData();
        
    }


    /**
     * Programm beenden
     */
    public void closeApplication() {
        main_model.closeApplication();
    }
    
    
    /***************************************************************************
     * Controller Methoden
     **************************************************************************/
    /**
     * Daten der SubController aktualisieren
     */
    public final void updataData() {
        main_frame.setMouseWaitCursor(true);
        
        for(SubController controller : sub_controller)
            controller.updateData();
        
        main_frame.setMouseWaitCursor(false);
    }

}
