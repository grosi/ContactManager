
package contactmanager.main.frame;

import contactmanager.main.contacts.ContactsController;
import contactmanager.main.AbstractController;
import contactmanager.main.AbstractController;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class MainController extends AbstractController implements MainInterface {
    
    private MainFrame mainFrame;
    private MainModel mainModel;
    
    /** Subcontroller */
    private ContactsController contactsController;
    
    public MainController() {
        
        super();
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        mainModel = new MainModel();
        addModel(mainModel);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        mainFrame = new MainFrame(this);
        addView(mainFrame);
        
        /* Alle SubController */
        this.contactsController = new ContactsController(this);
    }

    /***************************************************************************
     * Subcontroller Methoden
     **************************************************************************/
    
    /**
     * Gibt Referenz auf das Hauptfenster zurueck
     * @return
     */
    public MainFrame getMainFrame() {
        return mainFrame;
    }
    
    
    
    /***************************************************************************
     * GUI -> Model Methoden, inkl. Exception Handling
     **************************************************************************/
    
    /**
     * Anderer Tab auswaehlen 
     * @param tabName
     */
    public void changeTabSelection(String tabName) {
        mainModel.tabChange(tabName);
        System.err.println("changeTabSelection");
    }

    
    /**
     * Programm beenden
     */
    public void closeApplication() {
        mainModel.closeApplication();
    }
    
}
