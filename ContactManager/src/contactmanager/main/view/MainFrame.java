package contactmanager.main.view;

import contactmanager.main.AbstractFrame;
import contactmanager.main.controller.MainController;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class MainFrame extends AbstractFrame {

    private final MainController controller;
    private final JTabbedPane tabPane;
    
    
    public MainFrame(MainController controller) {
        
        this.controller = controller;

        //Tabs
        tabPane = new JTabbedPane();
        tabPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                tabPaneChangeEvent(ce);
            }
        });
        
        //Hauptfenster
        
        tabPane.add("Test",new JPanel());
        this.add(tabPane);
        
        this.setSize(1000, 600);
        this.setBounds(200, 200, 500, 100);
        this.setVisible(true);
        
        
        this.addWindowListener(new WindowListener() {

            @Override
            public void windowOpened(WindowEvent we) {
                windowOpenedEvent(we);
            }

            @Override
            public void windowClosing(WindowEvent we) {
                windowClosingEvent(we);
            }

            @Override
            public void windowClosed(WindowEvent we) {
                windowClosedEvent(we);
            }

            @Override
            public void windowIconified(WindowEvent we) {
                windowIconifedEvent(we);
            }

            @Override
            public void windowDeiconified(WindowEvent we) {
                windowDeiconifedEvent(we);
            }

            @Override
            public void windowActivated(WindowEvent we) {
                windowActivatedEvent(we);
            }

            @Override
            public void windowDeactivated(WindowEvent we) {
                windowDeactivatedEvent(we);
            }
        });
    }
    
    /**
     *  Methoden fuer Subviews
     */
    
    /**
     * Weiterer Tab hinzufuegen
     * @param panelName Name des Tabs
     * @param panel Referenz auf Tab
     */
    public void addTab(String panelName, JPanel panel) {
        tabPane.add(panelName, panel);
    }
    
    
    /***************************************************************************
     * View -> Controller
     **************************************************************************/
    
    /**
     * Anderer Tab wurde ausgewaehlt
     * @param ce 
     */
    private void tabPaneChangeEvent(ChangeEvent ce) {
        JTabbedPane currentTab = (JTabbedPane) ce.getSource();
        int index = currentTab.getSelectedIndex();
        
        controller.changeTabSelection(currentTab.getTitleAt(index));
    }
    

    private void windowOpenedEvent(WindowEvent we) {
        /**@TODO */
    }
    
    private void windowClosingEvent(WindowEvent we) {
        controller.closeApplication();
    }
    
    private void windowClosedEvent(WindowEvent we) {
        /**@TODO */
    }
    
    private void windowIconifedEvent(WindowEvent we) {
        /**@TODO */ 
    }
    
    private void windowDeiconifedEvent(WindowEvent we) {
        /**@TODO */ 
    }
       
    private void windowActivatedEvent(WindowEvent we) {
        /**@TODO */ 
    }
    
    private void windowDeactivatedEvent(WindowEvent we) {
        /**@TODO */ 
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
            case MainController.CURRENT_TAB_CHANGED_EVENT:
                this.setTitle(evt.getNewValue().toString());
                break;
            default:
                System.err.println("Kein passender Component gefunden");
        }
    }
}
