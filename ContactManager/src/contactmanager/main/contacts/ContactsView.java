package contactmanager.main.contacts;

import contactmanager.main.AbstractView;
import contactmanager.main.GraphicDesign;
import contactmanager.main.contacts.ContactsController;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;
import javax.swing.JTextField;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsView extends AbstractView implements GraphicDesign {
    
    private ContactsController controller;
    
    private JTextField nameTextField = new JTextField("Test");
    private JTextField nameCloneTextField = new JTextField("     ");
    private JButton changeButton = new JButton("Test");

    
    /**
     * View default Konstruktor
     * @param controller
     */
    public ContactsView(ContactsController controller) {
        super(controller.getMainController().getMainFrame());
        this.controller = controller;
        
        initComponents();

        mainFrame.addTab(ContactsController.CONTACTS_TITLE, this);
    }

    
    /**
     * Panel initialisieren
     */
    @Override
    protected void initComponents() {
        
        changeButton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                changeButtonActionPerformed(ae);
            }
        });
        
        nameTextField.addActionListener(new ActionListener() {
            
            @Override
            public void actionPerformed(ActionEvent ae) {
                nameTextFieldActionPerformed(ae);
            }
        });
        
        this.add(nameTextField);
        this.add(nameCloneTextField);
        this.add(changeButton); 
    }
    
    
    /***************************************************************************
     * View -> Controller
     **************************************************************************/
    
    /**
     * 
     * @param evt 
     */
    private void nameTextFieldActionPerformed(ActionEvent evt) {
        controller.changeElementName(nameTextField.getText());
    }
    
    private void changeButtonActionPerformed(ActionEvent evt) {
        controller.changeElementChange();
    }


    /***************************************************************************
     * Model -> View
     **************************************************************************/
   
    /**
     * Aenderungen aus dem Model in View uebernehmen
     * @param evt 
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case ContactsController.NAME_CHANGED_EVENT:
                nameCloneTextField.setText(evt.getNewValue().toString());
                System.out.println("HALLO ICH BIN EIN EVENT");
                break;
                
            case ContactsController.ADDRESS_CHANGED_EVENT:
                System.out.println("ADDRESS EVENT");
                break;
                
            default:
                System.err.println("Kein passender Component gefunden");
                
        }
    }
  
    
}
