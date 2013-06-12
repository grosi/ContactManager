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
    private JPanel detail_dynamic_panel_email;
    private JPanel detail_dynamic_panel_address;
    private JLabel detail_dynamic_label_email;
    private JLabel detail_dynamic_label_adress;
    private JSeparator detail_dynamic_separator_email;
    private JSeparator detail_dynamic_separator_address;
    private JButton detail_dynamic_addbutton;
    private JTextField email_adress;
    
    /* Suche */
    private JTextField search_textfield;
    
    /* Schaltflaechen */
    private JButton add_button;
    private JButton remove_button;
    private JButton save_button;
    private JButton message_button;
    
    
    
    private ArrayList<JButton> email_send_button = new ArrayList<>();
    private ArrayList<JButton> email_remove_button = new ArrayList<>();
    private ArrayList<JComboBox> email_combo = new ArrayList<>();
    private ArrayList<JTextField> email_text = new ArrayList<>();
    private ArrayList<JPanel> email_panel = new ArrayList<>();
    
    private ArrayList<JButton> address_remove_button = new ArrayList<>();
    private ArrayList<JComboBox> address_combo = new ArrayList<>();
    private ArrayList<JTextField> address_street = new ArrayList<>();
    private ArrayList<JTextField> address_code = new ArrayList<>();
    private ArrayList<JTextField> address_city = new ArrayList<>();
    private ArrayList<JTextField> address_country = new ArrayList<>();
    private ArrayList<JPanel> address_panel = new ArrayList<>();
        
    private ArrayList<JButton> phone_remove_button = new ArrayList<>();
    private ArrayList<JComboBox> phone_combo = new ArrayList<>();
    private ArrayList<JTextField> phone_text = new ArrayList<>();
    private ArrayList<JPanel> phone_panel = new ArrayList<>();
    

    private JPanel detail_dynamic_panel_phone;
    private JLabel detail_dynamic_label_phone;
    private JSeparator detail_dynamic_separator_phone;
    private JButton detail_dynamic_addbutton_address;
    private JButton detail_dynamic_addbutton_phone;
    private ImageIcon detail_dynamic_imageicon_address;
    private ImageIcon detail_dynamic_imageicon_phone;
    private ImageIcon detail_dynamic_imageicon_email;

    
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
        detail_dynamic_panel_email = new JPanel(new MigLayout("fill", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_email.setBackground(Color.white);
        detail_dynamic_label_email = new JLabel(CONTACT_TAB_EMAIL_LABEL);
        detail_dynamic_separator_email = new JSeparator();
        detail_dynamic_imageicon_email = new ImageIcon(IMAGES_FILEPATH+"add16x16.png");
        detail_dynamic_addbutton = new JButton("      Hinzufügen");
        detail_dynamic_addbutton.setIcon(detail_dynamic_imageicon_email);
        detail_dynamic_panel_email.add(detail_dynamic_label_email, "cell 0 0");
        detail_dynamic_panel_email.add(detail_dynamic_separator_email, "wrap");
        detail_dynamic_panel_email.add(detail_dynamic_addbutton,"wrap");
        
        
       detail_dynamic_addbutton.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addEmail("E-Mail Adresse eingeben", "Default");
//                email_adress.setText("E-Mail Adresse eingeben");
                System.out.println("ADD");
            }
        });
        
        
        
        
        
        //Dynamic Panel Adresse
        detail_dynamic_panel_address = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_address.setBackground(Color.white);
        detail_dynamic_label_adress = new JLabel(CONTACT_TAB_ADRESS_LABEL);
        detail_dynamic_separator_address = new JSeparator();
        detail_dynamic_imageicon_address = new ImageIcon(IMAGES_FILEPATH+"add16x16.png");
        detail_dynamic_addbutton_address = new JButton("      Hinzufügen");
        detail_dynamic_addbutton_address.setIcon(detail_dynamic_imageicon_address);
        detail_dynamic_panel_address.add(detail_dynamic_label_adress, "cell 0 0");
        detail_dynamic_panel_address.add(detail_dynamic_separator_address, "cell 1 0,wrap");
        detail_dynamic_panel_address.add(detail_dynamic_addbutton_address,"wrap");
        
    
        detail_dynamic_addbutton_address.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addAddress("Strasse", "PLZ", "Stadt/Ort", "Land", "Default");

                System.out.println("ADD");
            }
        });
        
        
        //Dynamic Panel Telefonnummer
        detail_dynamic_panel_phone = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_phone.setBackground(Color.white);
        detail_dynamic_label_phone = new JLabel(CONTACT_TAB_PHONE_LABEL);
        detail_dynamic_separator_phone = new JSeparator();
        detail_dynamic_imageicon_phone = new ImageIcon(IMAGES_FILEPATH+"add16x16.png");
        detail_dynamic_addbutton_phone = new JButton("      Hinzufügen");
        detail_dynamic_addbutton_phone.setIcon(detail_dynamic_imageicon_phone);
        detail_dynamic_panel_phone.add(detail_dynamic_label_phone, "cell 0 0");
        detail_dynamic_panel_phone.add(detail_dynamic_separator_phone, "cell 1 0,wrap");
        detail_dynamic_panel_phone.add(detail_dynamic_addbutton_phone,"wrap");
        detail_dynamic_addbutton_phone.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addPhone("Telefonnummer eingeben", "Default");

                System.out.println("ADD");
            }
        });       
        
        
        
        

        
        
        
        //Detail Haupt-Panel
        detail_main_panel = new JPanel(new MigLayout("", //Layout Grenzen
                "[grow,fill]", //Spalten Grenzen
                "[top]")); //Zeilen Grenzen
        detail_main_panel.setBackground(Color.white);
        detail_main_panel.add(detail_static_panel_name, "wrap");
        detail_main_panel.add(detail_static_panel_prename, "wrap");
        detail_main_panel.add(detail_dynamic_panel_email, "wrap");
        detail_main_panel.add(detail_dynamic_panel_address, "wrap");
        detail_main_panel.add(detail_dynamic_panel_phone, "wrap");
        
        
       
        
        
        //Scroll Pane
        detail_scrollpane = new JScrollPane();
        detail_scrollpane.setMinimumSize(new Dimension(GROUP_TAB_GROUPDETAIL_MIN_WIDTH, GROUP_TAB_GROUPDETAIL_MIN_HEIGHT));
        detail_scrollpane.setViewportView(detail_main_panel);
        
        
        /* Alle Komponente zum Tab-Panel hinzufuegen */
        this.setLayout(new MigLayout("", //Layout Grenzen
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

    private void addPhone(String phone, String type) {
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_phone.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_phone.getComponents();
        
        String[] phone_types = {"Private", "Business"};
        JPanel phone_new = new JPanel(new MigLayout("wrap 4"));
        JComboBox phone_type = new JComboBox(phone_types);
        phone_type.setSelectedItem(type);
        JTextField phone_nummer = new JTextField(phone);
        ImageIcon remove_image = new ImageIcon(IMAGES_FILEPATH+"remove16x16.png");
        JButton remove_phone = new JButton("Löschen");
        remove_phone.setIcon(remove_image);
        phone_nummer.setMinimumSize(new Dimension(100, 0));
        
        
        phone_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Nummer Type geändert");
            }
        });
        
        phone_nummer.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                System.out.println("Nummer angewählt"); 
                selectPhone(fe);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                System.out.println("Nummer abgewählt");
                deselectPhone(fe);
            }
        });
        
        remove_phone.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Nummer löschen");
                removePhone(ae);
            }
        });
        

        
        
        detail_dynamic_panel_phone.removeAll();
        
        for(Component c : all_components) {
           
            if(c instanceof JButton) {
                phone_new.add(phone_type);
                phone_new.add(phone_nummer, " span 2");
                detail_dynamic_panel_phone.add(phone_new, "span 2,growx,wrap");
                
 
           }
            
            detail_dynamic_panel_phone.add(c, constraint_map.get(c));
            
            
        }


        
        phone_panel.add(phone_new);
        phone_combo.add(phone_type);
        phone_text.add(phone_nummer);
        phone_remove_button.add(remove_phone);

       
        detail_dynamic_panel_phone.revalidate();
        
        System.out.println("ADD EXIST");
    }
    
    
      private void removePhone(ActionEvent ae) {
        JButton remove = (JButton)ae.getSource();
        int index = phone_remove_button.indexOf(remove);
        JPanel panel = phone_panel.get(index);
        
        detail_dynamic_panel_phone.remove(panel);
        detail_dynamic_panel_phone.revalidate();
        
    }
    
     
        
    private void selectPhone(FocusEvent hallo) {
        JTextField text = (JTextField)hallo.getSource();
        int index = phone_text.indexOf(text);
        JButton remove = phone_remove_button.get(index);
        JPanel panel = phone_panel.get(index);
        
  
        panel.add(remove);
        remove.setVisible(true);
        panel.revalidate();
        
    }
    
    
    
        private void deselectPhone(FocusEvent fe) {
        JTextField text = (JTextField)fe.getSource();
        int index = phone_text.indexOf(text);
        JButton remove = phone_remove_button.get(index);
        JPanel panel = phone_panel.get(index);
        remove.setVisible(false);
        panel.remove(remove);
        panel.revalidate();
        
    }
   
    
        private void addAddress(String street, String code, String city, String country ,String type){
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_address.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_address.getComponents();
        
        String[] adress_types = {"Privat","Business", "Bill"};
        JPanel address_new = new JPanel(new MigLayout("","rel[]rel[]rel[]min",""));
        JComboBox address_type = new JComboBox(adress_types);
        address_type.setSelectedItem("type");
        JTextField street_address = new JTextField(street);
        JTextField code_address = new JTextField(code);
        JTextField city_address = new JTextField(city);
        JTextField country_address = new JTextField(country);
        ImageIcon remove_image = new ImageIcon(IMAGES_FILEPATH+"remove16x16.png");
        JButton remove_address = new JButton("Löschen");
        remove_address.setIcon(remove_image);
        
        street_address.setMinimumSize(new Dimension(200, 0));
        code_address.setMinimumSize(new Dimension(30, 0));
        city_address.setMinimumSize(new Dimension(166, 0));
        country_address.setMinimumSize(new Dimension(100, 0));
        
         address_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Email Type geändert");
            }
        });
        
         street_address.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                System.out.println("Adresse angewählt"); 
                selectAddress(fe,1);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                System.out.println("Adresse abgewählt");
                deselectAddress(fe,1);
            }
        });
    
           code_address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                System.out.println("Adresse angewählt"); 
                selectAddress(fe,2);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                System.out.println("Adresse abgewählt");
                deselectAddress(fe,2);
            }
        });
         
          city_address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                System.out.println("Adresse angewählt"); 
                selectAddress(fe,3);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                System.out.println("Adresse abgewählt");
                deselectAddress(fe,3);
            }
        });
          
         country_address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                System.out.println("Adresse angewählt"); 
                selectAddress(fe,4);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                System.out.println("Adresse abgewählt");
                deselectAddress(fe,4);
            }
        });
         
           
           
        remove_address.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("Email löschen");
                removeAddress(ae);
            }
        });
        
         for(Component c : all_components) {
           
            if(c instanceof JButton) {
                address_new.add(address_type, "dock west");
                address_new.add(street_address, "cell 0 0 10 1");
                address_new.add(code_address, "cell 0 1");
                address_new.add(city_address, " cell 1 1 9 1");
                address_new.add(country_address, " cell 0 2 10 1");
                
 
                detail_dynamic_panel_address.add(address_new, "span 2,growx,wrap");
                
 
           }
            
            detail_dynamic_panel_address.add(c, constraint_map.get(c));
            
            
        }


        
        address_panel.add(address_new);
        address_combo.add(address_type);
        address_street.add(street_address);
        address_code.add(code_address);
        address_city.add(city_address);
        address_country.add(country_address);
        address_remove_button.add(remove_address);

       
        detail_dynamic_panel_address.revalidate();
        
        System.out.println("ADD EXIST");       
          
            
        }
        
        
     private void removeAddress(ActionEvent ae) {
        JButton remove = (JButton)ae.getSource();
        int index = address_remove_button.indexOf(remove);
        JPanel panel = address_panel.get(index);
        detail_dynamic_panel_address.remove(panel);
        detail_dynamic_panel_address.revalidate();
        
    }
    
     
        
    private void selectAddress(FocusEvent fe, int indicator) {
        JTextField text = (JTextField)fe.getSource();
        int index = 0;
        switch(indicator){
                case 1: index = address_street.indexOf(text);
                    break;
                case 2: index = address_code.indexOf(text);
                    break;
                case 3: index = address_city.indexOf(text);
                    break;
                case 4: index = address_country.indexOf(text);
                    break;
                 
        }
        
        JButton remove = address_remove_button.get(index);
        JPanel panel = address_panel.get(index);
        panel.add(remove, "dock east");
        remove.setVisible(true);
        panel.revalidate();      
    }
    
    
    
        private void deselectAddress(FocusEvent fe, int indicator) {
        JTextField text = (JTextField)fe.getSource();
        int index = 0;
        switch(indicator){
                case 1: index = address_street.indexOf(text);
                    break;
                case 2: index = address_code.indexOf(text);
                    break;
                case 3: index = address_city.indexOf(text);
                    break;
                case 4: index = address_country.indexOf(text);
                    break;
        }         
        
        
        
        JButton remove = address_remove_button.get(index);
        JPanel panel = address_panel.get(index);   
        remove.setVisible(false);
        panel.remove(remove);       
        panel.revalidate();      
    }
    
        private void addEmail(String email, String type) {
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_email.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_email.getComponents();
        
        String[] email_types = {"Private", "Business"};
        JPanel email_new = new JPanel(new MigLayout("wrap 4"));
        JComboBox email_type = new JComboBox(email_types);
        email_type.setSelectedItem(type);
        JTextField email_adress = new JTextField(email);
        ImageIcon remove_image = new ImageIcon(IMAGES_FILEPATH+"remove16x16.png");
        JButton remove_email = new JButton("Löschen");
        remove_email.setIcon(remove_image);
        ImageIcon send_image = new ImageIcon(IMAGES_FILEPATH+"messages16x16.png");
        JButton send_email = new JButton("Senden");
        send_email.setIcon(send_image);
        email_adress.setMinimumSize(new Dimension(100, 0));
        
        
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
                email_new.add(email_type);
                email_new.add(email_adress, " span 2");
                email_new.add(send_email);
                detail_dynamic_panel_email.add(email_new, "span 2,growx,wrap");
                
 
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
    
    
    
    
     private void addEmail() {
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_email.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_email.getComponents();
        String[] email_types = {"Default", "Private", "Business"};
        JPanel email_new = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]"));
        JComboBox email_type = new JComboBox(email_types);
        email_adress = new JTextField("Email-Adreese eingeben");
        
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
                                   
    
     
        private void removeEmail(ActionEvent aeremove) {
        JButton remove = (JButton)aeremove.getSource();
        int index = email_remove_button.indexOf(remove);
        JPanel panel = email_panel.get(index);
        
        detail_dynamic_panel_email.remove(panel);
        detail_dynamic_panel_email.revalidate();
        panel.revalidate();
    }
    
     
        
    private void selectEmail(FocusEvent feselct) {
        JTextField text = (JTextField)feselct.getSource();
        int index = email_text.indexOf(text);
        JButton send = email_send_button.get(index);
        JButton remove = email_remove_button.get(index);
        JPanel panel = email_panel.get(index);
        
        panel.remove(send);
        
        panel.add(remove);      
        remove.setVisible(true);

        
        panel.revalidate();
        detail_dynamic_label_email.revalidate();
      
    }
    
    
    
        private void deselectEmail(FocusEvent fedeselect) {
        JTextField text = (JTextField)fedeselect.getSource();
        int index = email_text.indexOf(text);
        JButton send = email_send_button.get(index);
        JButton remove = email_remove_button.get(index);
        JPanel panel = email_panel.get(index);
        remove.setVisible(false);
        panel.remove(remove);
        panel.add(send);

        
        panel.revalidate();
        detail_dynamic_label_email.revalidate();
        
    }
   
    public void enableSaveButton(boolean state) {
        save_button.setEnabled(state);
    }
    
    
    /**
     * Message-Button aktiviern oder deaktivieren
     * @param state
     */
    public void enableMessageButton(boolean state) {
        message_button.setEnabled(state);
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