package contactmanager.main.frame;

import contactmanager.main.AbstractFrame;
import contactmanager.main.graphic.GraphicDesign;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class MainFrame extends AbstractFrame implements GraphicDesign, MainInterface {

    private final MainController controller;
    private final JTabbedPane tabPane;
    
    
    public MainFrame(MainController controller) {
        
        this.controller = controller;
        
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            /**@TODO */
        }

        //Tabs
        tabPane = new JTabbedPane();
        tabPane.setBackground(Color.yellow);
        tabPane.setMinimumSize(new Dimension(150,150));
        tabPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                tabPaneChangeEvent(ce);
            }
        });
       
        
        //Hauptfenster 
        //tabPane.add("<html><body leftmargin=15 topmargin=8 marginwidth=15 marginheight=5>Test</body></html>",new JPanel());
        
       // this.setLayout(new FlowLayout(FlowLayout.CENTER));
        this.add(tabPane);
        
        this.setSize(1000, 600);
        this.setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));
        this.setResizable(true);
        //this.setBounds(200, 200, 500, 100);
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
    
    /***************************************************************************
     *  Methoden fuer Subviews
     **************************************************************************/
    
    /**
     * Weiterer Tab hinzufuegen
     * @param panelName Name des Tabs
     * @param panel Referenz auf Tab
     * @TODO Groesse des Tabs beruecksichtigen
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
        System.out.println("Frame OPEN");
        int index = tabPane.getSelectedIndex();
        //controller.changeTabSelection(tabPane.getTitleAt(index));
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
            case CURRENT_TAB_CHANGED_EVENT:
                System.out.println("EVENT");
                this.setTitle(evt.getNewValue().toString());
                break;
            default:
                System.err.println("Unknows Event");
        }
    }
}
