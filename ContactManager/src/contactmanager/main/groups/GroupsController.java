
package contactmanager.main.groups;

import contactmanager.main.AbstractController;
import contactmanager.main.AbstractView;
import contactmanager.main.SubController;
import contactmanager.main.frame.MainController;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 02.05.2013
 */
public class GroupsController extends AbstractController implements SubController, GroupsInterface {
    
    private MainController main_controller;

    /* Modelle */
    private GroupsModel groups_model;
    
    /* Views */
    private AbstractView groups_view;
    
    
    public GroupsController(MainController mainController) {
        super();
        
        this.main_controller = mainController;
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        groups_model = new GroupsModel(this);
        addModel(groups_model);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        groups_view = new GroupsView(this);
        addView(groups_view);
    }
 
    
    
    /***************************************************************************
     * Model/View -> MainController Methoden, inkl. Exception Handling
     **************************************************************************/
    
    @Override
    public MainController getMainController() {
        return this.main_controller;
    }
    
    
    
    /***************************************************************************
     * GUI -> Model Methoden, inkl. Exception Handling
     **************************************************************************/
    
    
}

    

