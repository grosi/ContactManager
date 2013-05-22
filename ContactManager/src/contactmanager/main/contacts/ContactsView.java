package contactmanager.main.contacts;

import contactmanager.main.AbstractView;
import contactmanager.main.graphic.GraphicDesign;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_ADD_MNEMONIC;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_ADD_TOOLTIP;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_DEFAULT_NAME_TEXT;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_DEFAULT_SEARCH_TEXT;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPDETAIL_MIN_HEIGHT;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPDETAIL_MIN_WIDTH;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPLIST_MIN_HEIGHT;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPLIST_MIN_WIDTH;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPMEMBER_LABEL;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPOVERVIEW_LABEL;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_MESSAGE_MNEMONIC;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_MESSAGE_TOOLTIP;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_REMOVE_MNEMONIC;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_REMOVE_TOOLTIP;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_SAVE_MNEMONIC;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_SAVE_TOOLTIP;
import static contactmanager.main.graphic.GraphicDesign.IMAGES_FILEPATH;
import contactmanager.main.graphic.JSeparatorList;
import static contactmanager.main.groups.GroupsInterface.GROUP_ADD_CONTACT_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_DELETE_CONTACT_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_DELETE_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_INSERT_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_LIST_SELECT_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_SEARCH_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_SELECT_CONTACTS_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_SELECT_EVENT;
import static contactmanager.main.groups.GroupsInterface.GROUP_UPDATE_EVENT;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsView extends AbstractView implements GraphicDesign, ContactsInterface {
    
    private ContactsController controller;
    
    /* Linke Spalte */
    private JScrollPane list_scrollpane;
    private JSeparatorList separatorlist;
    
    /* Rechte Spalte */
    private JScrollPane detail_scrollpane;
    private JPanel detail_main_panel;
    private JPanel detail_static_panel_name;
    private JPanel detail_static_panel_prename;
    private JLabel detail_static_label;
    private JSeparator detail_static_separator;
    private JTextField detail_static_name_textfield;
    private JTextField detail_static_prename_textfield;
    private JPanel detail_dynamic_panel_email;
    private JScrollPane detail_member_scrollpane;
    private JSeparatorList detail_member_separatorlist;
    private JLabel detail_member_label;
    private JSeparator detail_dynamic_separator;
    
    /* Suche */
    private JTextField search_textfield;
    
    /* Schaltflaechen */
    private JButton add_button;
    private JButton remove_button;
    private JButton save_button;
    private JButton message_button;

    
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

        /* Linke Spalte */
        //Suchfeld
        search_textfield = new JTextField(GROUP_TAB_DEFAULT_SEARCH_TEXT);
        search_textfield.setBackground(Color.white);
        search_textfield.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                searchTextActionPerformed(ae);
            }
        });
        
        //Liste
        separatorlist = new JSeparatorList();
        separatorlist.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                groupListValueChanged(lse);
            }
        });
        
        //Scroll Pane
        list_scrollpane = new JScrollPane();
        list_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list_scrollpane.setMinimumSize(new Dimension(GROUP_TAB_GROUPLIST_MIN_WIDTH, GROUP_TAB_GROUPLIST_MIN_HEIGHT));
        list_scrollpane.setViewportView(separatorlist); //Separator Liste hinzufuegen
        

        /* Rechte Spalte */
        //Nachricht Schaltflaeche 
        message_button = new JButton();
        message_button.setIcon(new ImageIcon(IMAGES_FILEPATH+"messages32x32.png"));  
        message_button.setMnemonic(GROUP_TAB_MESSAGE_MNEMONIC);
        message_button.setToolTipText(CONTACT_TAB_MESSAGE_TOOLTIP);
        message_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                messageButtonActionPerformed(ae);
            }
        });
        
        //Speichern Schaltflaeche
        save_button = new JButton();
        save_button.setIcon(new ImageIcon(IMAGES_FILEPATH+"save32x32.png"));
        save_button.setMnemonic(GROUP_TAB_SAVE_MNEMONIC);
        save_button.setToolTipText(CONTACT_TAB_SAVE_TOOLTIP);
        save_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                saveButtonActionPerformed(ae);
            }
        });
        
        //Hinzufuegen Schaltflaeche
        add_button = new JButton();
        add_button.setIcon(new ImageIcon(IMAGES_FILEPATH+"add32x32.png"));
        add_button.setMnemonic(GROUP_TAB_ADD_MNEMONIC);
        add_button.setToolTipText(CONTACT_TAB_ADD_TOOLTIP);
        add_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addButtonActionPerformed(ae);
            }
        });
        
        //Entfernen Schaltflaeche
        remove_button = new JButton();
        remove_button.setIcon(new ImageIcon(IMAGES_FILEPATH+"remove32x32.png"));
        remove_button.setMnemonic(GROUP_TAB_REMOVE_MNEMONIC);
        remove_button.setToolTipText(CONTACT_TAB_REMOVE_TOOLTIP);
        remove_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                removeButtonActionPerformed(ae);
            }
        });
        
        
        //Static Detail Panel Name
        detail_static_panel_name = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_static_panel_name.setBackground(Color.white);
        detail_static_label = new JLabel(CONTACT_TAB_NAME_LABEL);
        detail_static_separator = new JSeparator();
        detail_static_name_textfield = new JTextField(CONTACT_TAB_DEFAULT_NAME_TEXT);
        detail_static_panel_name.add(detail_static_label, "cell 0 0");
        detail_static_panel_name.add(detail_static_separator, "cell 1 0");
        detail_static_panel_name.add(detail_static_name_textfield, "cell 0 1 2 1,growx");
        
        //Static Detail Panel Vorname      
        detail_static_panel_prename = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]min")); //Zeilen Grenzen
        detail_static_panel_prename.setBackground(Color.white);
        detail_static_label = new JLabel(CONTACT_TAB_PRENAME_LABEL);
        detail_static_separator = new JSeparator();
        detail_static_prename_textfield = new JTextField(CONTACT_TAB_DEFAULT_PRENAME_TEXT);
        detail_static_panel_prename.add(detail_static_label, "cell 0 0");
        detail_static_panel_prename.add(detail_static_separator, "cell 1 0");
        detail_static_panel_prename.add(detail_static_prename_textfield, "cell 0 1 2 1,growx");
        
        
        
        //Dynamic Panel E-Mail
        detail_dynamic_panel_email = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][grow,fill]min")); //Zeilen Grenzen
        detail_dynamic_panel_email.setBackground(Color.RED);
        detail_member_label = new JLabel(CONTACT_TAB_EMAIL_LABEL);
        detail_dynamic_separator = new JSeparator();
        detail_member_scrollpane = new JScrollPane();
        detail_member_separatorlist = new JSeparatorList();
        detail_member_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        detail_member_scrollpane.setViewportView(detail_member_separatorlist);
        detail_dynamic_panel_email.add(detail_member_label, "cell 0 0");
        detail_dynamic_panel_email.add(detail_dynamic_separator, "cell 1 0");
        detail_dynamic_panel_email.add(detail_member_scrollpane, "cell 0 1 2 1,growx");
        
        detail_member_separatorlist.addListMember("Hallo", 1);

        
        
        
        //Detail Haupt-Panel
        detail_main_panel = new JPanel(new MigLayout("", //Layout Grenzen
                "[grow,fill]", //Spalten Grenzen
                "[]min[grow,fill]")); //Zeilen Grenzen
        detail_main_panel.setBackground(Color.white);
        detail_main_panel.add(detail_static_panel_name, "cell 0 0");
        detail_main_panel.add(detail_static_panel_prename, "cell 0 1");
        detail_main_panel.add(detail_dynamic_panel_email, "cell 0 2");
        
        
        //Scroll Pane
        detail_scrollpane = new JScrollPane();
        detail_scrollpane.setMinimumSize(new Dimension(GROUP_TAB_GROUPDETAIL_MIN_WIDTH, GROUP_TAB_GROUPDETAIL_MIN_HEIGHT));
        detail_scrollpane.setViewportView(detail_main_panel);
        
        
        /* Alle Komponente zum Tab-Panel hinzufuegen */
        this.setLayout(new MigLayout("debug", //Layout Grenzen
                "rel[grow,fill]unrel[grow,fill]rel[]rel[]rel[]rel[]unrel", //Spalten Grenzen
                "unrel[]unrel[grow,fill]unrel")); //Zeilen Grenzen
        this.add(search_textfield, "growy");
        this.add(list_scrollpane, "cell 0 1");
        this.add(message_button, "cell 2 0");
        this.add(save_button, "cell 3 0");
        this.add(add_button, "cell 4 0");
        this.add(remove_button, "cell 5 0");
        this.add(detail_scrollpane, "cell 1 1 5 1");
        
    }                    

                                   
    
    
    /***************************************************************************
     * View -> Controller
     **************************************************************************/
    
    private void addButtonActionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void removeButtonActionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    private void saveButtonActionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void messageButtonActionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void searchTextActionPerformed(ActionEvent ae) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void groupListValueChanged(ListSelectionEvent lse) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.    
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
        switch(evt.getPropertyName()) {
            case GROUP_LIST_SELECT_EVENT:
                break;
                
            case GROUP_SEARCH_EVENT:
                break;
                
            case GROUP_SELECT_EVENT:
                break;
                
            case GROUP_INSERT_EVENT:
                break;
                
            case GROUP_UPDATE_EVENT:
                break;
                
            case GROUP_DELETE_EVENT:
                break;
                
            case GROUP_SELECT_CONTACTS_EVENT:
                break;
                
            case GROUP_ADD_CONTACT_EVENT:
                break;
                
            case GROUP_DELETE_CONTACT_EVENT:
                break;
                
            default:
                System.err.println("Unknows Event");
        }
    }
    
}