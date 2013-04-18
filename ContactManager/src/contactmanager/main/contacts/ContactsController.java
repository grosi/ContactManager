package contactmanager.main.controller;

import contactmanager.main.view.ContactsView;
import contactmanager.main.model.ContactsModel;
import contactmanager.main.AbstractController;
import contactmanager.main.AbstractView;


/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class ContactsController extends AbstractController implements ContactsInterface, SubController {
    
    private MainController mainController;

    /* Modelle */
    private ContactsModel contactsModel;
    
    /* Views */
    private AbstractView contactsView;
    
    public ContactsController(MainController mainController) {
        super();
        
        this.mainController = mainController;
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        contactsModel = new ContactsModel();
        addModel(contactsModel);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        contactsView = new ContactsView(this);
        addView(contactsView);
    }
 
    
    @Override
    public MainController getMainController() {
        return this.mainController;
    }
    
    
    /***************************************************************************
     * GUI -> Model Methoden, inkl. Exception Handling
     **************************************************************************/
    
    /**
     * @TODO nur Testmethode
     * @param newName 
     */
    public void changeElementName(String newName) {
        contactsModel.setName(newName);
        //setModelProperty(ELEMENT_TEXT_PROPERTY, newName);
    }
    
    /**
     *  @TODO nur Testmethode 
     */
    public void changeElementChange() {
        contactsModel.setChange();
        //setModelProperty(ELEMENT_CHANGE_PROPERTY, this);
    }    
}
