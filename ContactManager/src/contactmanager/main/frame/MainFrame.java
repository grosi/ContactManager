package contactmanager.main.frame;

import contactmanager.main.AbstractFrame;
import contactmanager.main.graphic.GraphicDesign;
import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.beans.PropertyChangeEvent;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.SwingConstants;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
import javax.swing.border.BevelBorder;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class MainFrame extends AbstractFrame implements GraphicDesign, MainInterface {
    
    /* Controller */
    private MainController controller;
    
    /* GUI */
    private JTabbedPane tabPane;
    private JLabel state_label;
    
    
    /**
     * View default Konstruktor
     * @param controller
     */
    public MainFrame(MainController controller) {
        super();
        this.controller = controller;
        
        /* Alle Komponente Initialisieren */
        initComponents();
    }
    
    
    
    /***************************************************************************
     * Alle Grafikkomponenten zeichnen und anordnen
     **************************************************************************/
    public void initComponents() {
        
        /* Look and Feel setzen */
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException | IllegalAccessException | InstantiationException | UnsupportedLookAndFeelException e) {
            try {
                System.err.println("No Look an Feel for your OS");
                UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
            } catch (    ClassNotFoundException | InstantiationException | IllegalAccessException | UnsupportedLookAndFeelException ex) {
                System.err.println("Setup Look and Feel crashed");
            }
        }

        /* Tabs fuer die verschiedenen Views */
        tabPane = new JTabbedPane();
        tabPane.addChangeListener(new ChangeListener() {

            @Override
            public void stateChanged(ChangeEvent ce) {
                tabPaneChangeEvent(ce);
            }
        });
        
        
        /* Statusbar */
        state_label = new JLabel();
        state_label.setHorizontalAlignment(SwingConstants.RIGHT);
        
        
        /* Frame */
        this.setLayout(new BorderLayout());
        this.add(tabPane, BorderLayout.CENTER);
        this.add(state_label, BorderLayout.SOUTH);
        this.setMinimumSize(new Dimension(FRAME_MIN_WIDTH, FRAME_MIN_HEIGHT));
        this.setResizable(true);
        this.setVisible(true);
        this.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent we) {
                windowClosingEvent(we);
            }
        });

        
    }
    /***************************************************************************
     * View -> Controller
     * Methoden werden direkt von Listener der Grafikelementen angesprochen
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
    

//    private void windowOpenedEvent(WindowEvent we) {
//        int index = tabPane.getSelectedIndex();
//        //controller.changeTabSelection(tabPane.getTitleAt(index));
//        /**@TODO */
//    }
    
    private void windowClosingEvent(WindowEvent we) {
        controller.closeApplication();
    }
    
//    private void windowClosedEvent(WindowEvent we) {
//        /**@TODO */
//    }
//    
//    private void windowIconifedEvent(WindowEvent we) {
//        /**@TODO */ 
//    }
//    
//    private void windowDeiconifedEvent(WindowEvent we) {
//        /**@TODO */ 
//    }
//       
//    private void windowActivatedEvent(WindowEvent we) {
//        /**@TODO */ 
//    }
//    
//    private void windowDeactivatedEvent(WindowEvent we) {
//        /**@TODO */ 
//    }
    
    
    /***************************************************************************
     * Controller -> View
     * setter und getter Methoden, die dem Controller das Steuern ermoeglichen
     **************************************************************************/
    
    /**
     * Weiterer Tab hinzufuegen
     * @param panelName Name des Tabs
     * @param panel Referenz auf Tab
     * @TODO Groesse des Tabs beruecksichtigen
     */
    public void setTab(String panelName, JPanel panel) {
        this.tabPane.add(panelName, panel);
    }
    
    /**
     * Mauszeiger aendern
     * @param state true: Sanduhr; false: Standard
     */
    public void setMouseWaitCursor(boolean state) {
        if(state == true)
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        else
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
    
    /**
     * Statusbar Text setzen
     * @param text Status-Text
     */
    public void setStatusBar(String text) {
        this.state_label.setText(text+"  ");
    }

    /***************************************************************************
     * Model -> View
     * Ausgeloeste Events des Modells, welche das View ueber Aenderungen informieren
     **************************************************************************/
    /**
     * Aenderungen aus dem Model in View uebernehmen
     * @param evt 
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        switch (evt.getPropertyName()) {
            case CURRENT_TAB_CHANGED_EVENT:
                this.setTitle(evt.getNewValue().toString());
                break;
            default:
                System.err.println("Unknows Event");
        }
    }
}
