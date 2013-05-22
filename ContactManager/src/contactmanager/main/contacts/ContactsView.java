package contactmanager.main.contacts;

import contactmanager.main.AbstractView;
import contactmanager.main.graphic.GraphicDesign;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_ADD_MNEMONIC;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_DEFAULT_SEARCH_TEXT;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPDETAIL_MIN_HEIGHT;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPDETAIL_MIN_WIDTH;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPLIST_MIN_HEIGHT;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_GROUPLIST_MIN_WIDTH;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_MESSAGE_MNEMONIC;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_REMOVE_MNEMONIC;
import static contactmanager.main.graphic.GraphicDesign.GROUP_TAB_SAVE_MNEMONIC;
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
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.Map;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
    private static JPanel detail_dynamic_panel_email;
    private JLabel detail_dynamic_label;
    private JSeparator detail_dynamic_separator;
    private static JButton detail_dynamic_addbutton;
    
    /* Suche */
    private JTextField search_textfield;
    
    /* Schaltflaechen */
    private JButton add_button;
    private JButton remove_button;
    private JButton save_button;
    private JButton message_button;
    
    
    
    private static ArrayList<JButton> email_send_button = new ArrayList<>();
    private static ArrayList<JButton> email_remove_button = new ArrayList<>();
    private static ArrayList<JComboBox> email_combo = new ArrayList<>();
    private static ArrayList<JTextField> email_text = new ArrayList<>();
    private static ArrayList<JPanel> email_panel = new ArrayList<>();

    
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
                "[][]min")); //Zeilen Grenzen
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
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_email.setBackground(Color.white);
        detail_dynamic_label = new JLabel(CONTACT_TAB_EMAIL_LABEL);
        detail_dynamic_separator = new JSeparator();
        detail_dynamic_addbutton = new JButton("add");
        detail_dynamic_panel_email.add(detail_dynamic_label, "cell 0 0");
        detail_dynamic_panel_email.add(detail_dynamic_separator, "cell 1 0,wrap");
        detail_dynamic_panel_email.add(detail_dynamic_addbutton,"wrap");
        addEmail();
        

        
        
        
        //Detail Haupt-Panel
        detail_main_panel = new JPanel(new MigLayout("", //Layout Grenzen
                "[grow,fill]", //Spalten Grenzen
                "[top]")); //Zeilen Grenzen
        detail_main_panel.setBackground(Color.white);
        detail_main_panel.add(detail_static_panel_name, "wrap");
        detail_main_panel.add(detail_static_panel_prename, "wrap");
        detail_main_panel.add(detail_dynamic_panel_email, "wrap");
        
       detail_dynamic_addbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addEmail("sigro@gmx.ch", "Private");
                System.out.println("ADD");
            }
        });
        
        
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

    
        private static void addEmail(String email, String type) {
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_email.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_email.getComponents();
        
        String[] email_types = {"Private", "Business"};
        JPanel email_new = new JPanel(new MigLayout("wrap 4"));
        JComboBox email_type = new JComboBox(email_types);
        JTextField email_adress = new JTextField(email);
        ImageIcon remove_image = new ImageIcon("remove.png");
        JButton remove_email = new JButton("Löschen");
        ImageIcon send_image = new ImageIcon("remove.png");
        JButton send_email = new JButton(send_image);
        
        
        email_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Email Type geändert");
            }
        });
        
        email_adress.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                System.out.println("Adresse angewählt"); 
                selectEmail(fe);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                System.out.println("Adresse abgewählt");
                deselectEmail(fe);
            }
        });
        
        remove_email.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Email löschen");
                removeEmail(ae);
            }
        });
        
        send_email.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Email schreiben");
            }
        });
        
        
        detail_dynamic_panel_email.removeAll();
        
        for(Component c : all_components) {
           
            if(c instanceof JButton) {

                email_type.setSelectedIndex(1);
                email_new.add(email_type);
                email_new.add(email_adress, " span 2");
                email_new.add(send_email);
                
                detail_dynamic_panel_email.add(email_new, "growx,wrap");
            }
            
            detail_dynamic_panel_email.add(c, constraint_map.get(c));
        }
        
        email_panel.add(email_new);
        email_combo.add(email_type);
        email_text.add(email_adress);
        email_remove_button.add(remove_email);
        email_send_button.add(send_email);
       
        detail_dynamic_panel_email.revalidate();
        
        System.out.println("ADD EXIST");
    }
    
    
    
    
     private static void addEmail() {
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_email.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_email.getComponents();
        String[] email_types = {"Default", "Private", "Business"};
        JPanel email_new = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]"));
        JComboBox email_type = new JComboBox(email_types);
        JTextField email_adress = new JTextField("Email-Adreese eingeben");
        
        email_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Email Type geändert");
            }
        });
        
        
        detail_dynamic_panel_email.removeAll();
        
        for(Component c : all_components) {
             
            if(c instanceof JButton) {
                
                email_new.add(email_type);
                email_new.add(email_adress,"span 2");
                
                detail_dynamic_panel_email.add(email_new, "cell 0 1 2 1,growx,wrap");
            }
            
            detail_dynamic_panel_email.add(c, constraint_map.get(c));
        }
        
        
        
        detail_dynamic_panel_email.revalidate();
    }
                                   
    
     
        private static void removeEmail(ActionEvent ae) {
        JButton remove = (JButton)ae.getSource();
        int index = email_remove_button.indexOf(remove);
        JPanel panel = email_panel.get(index);
        
        detail_dynamic_panel_email.remove(panel);
        detail_dynamic_panel_email.revalidate();
        
    }
    
     
        
    private static void selectEmail(FocusEvent hallo) {
        JTextField text = (JTextField)hallo.getSource();
        int index = email_text.indexOf(text);
        JButton send = email_send_button.get(index);
        JButton remove = email_remove_button.get(index);
        JPanel panel = email_panel.get(index);
        
        panel.remove(send);
        panel.add(remove);
        
        panel.revalidate();
        
    }
    
    
    
        private static void deselectEmail(FocusEvent fe) {
        JTextField text = (JTextField)fe.getSource();
        int index = email_text.indexOf(text);
        JButton send = email_send_button.get(index);
        JButton remove = email_remove_button.get(index);
        JPanel panel = email_panel.get(index);
        
        panel.remove(remove);
        panel.add(send);
        
        panel.revalidate();
        
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