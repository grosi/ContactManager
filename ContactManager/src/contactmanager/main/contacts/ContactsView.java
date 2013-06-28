package contactmanager.main.contacts;

import contactmanager.main.AbstractView;
import contactmanager.main.contacts.ContactDTO.ContactAdress;
import contactmanager.main.contacts.ContactDTO.ContactEmail;
import contactmanager.main.contacts.ContactDTO.ContactPhone;
import static contactmanager.main.contacts.ContactsEvent.CONTACT_DEFAULT_ID;
import contactmanager.main.graphic.JSeparatorList;
import contactmanager.main.graphic.JSeparatorList.ListMember;
import contactmanager.main.groups.GroupDTO;
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
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;

/**
 * Kontakt-View
 * @author Philipp Eder, Simon Grossenbacher (Design)
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsView extends AbstractView implements ContactsGraphicDesign, ContactsEvent {
    
    /* Controller */
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
    private JButton detail_dynamic_addbutton_email;
    private JPanel detail_dynamic_panel_phone;
    private JLabel detail_dynamic_label_phone;
    private JSeparator detail_dynamic_separator_phone;
    private JButton detail_dynamic_addbutton_address;
    private JButton detail_dynamic_addbutton_phone;
    private ImageIcon detail_dynamic_imageicon_address;
    private ImageIcon detail_dynamic_imageicon_phone;
    private ImageIcon detail_dynamic_imageicon_email;
    private JPanel detail_dynamic_panel_group;
    private JLabel detail_dynamic_label_group;
    private JSeparator detail_dynamic_separator_group;
    private ImageIcon detail_dynamic_imageicon_group;
    private JButton detail_dynamic_addbutton_group;
    private JComboBox detail_dynamic_combobox_group;
    private JPanel detail_dynamic_panel_group_choose;
    
    /* Suche */
    private JTextField search_textfield;
    
    /* Schaltflaechen */
    private JButton add_button;
    private JButton remove_button;
    private JButton save_button;
    private JButton message_button;
    
    /* E-Mail */
    private ArrayList<JButton> email_send_button = new ArrayList<>();
    private ArrayList<JButton> email_remove_button = new ArrayList<>();
    private ArrayList<JComboBox> email_combo = new ArrayList<>();
    private ArrayList<JTextField> email_text = new ArrayList<>();
    private ArrayList<JPanel> email_panel = new ArrayList<>();
    private ArrayList<Integer> email_id = new ArrayList<>();
    
    /* Adressen */
    private ArrayList<JButton> address_remove_button = new ArrayList<>();
    private ArrayList<JComboBox> address_combo = new ArrayList<>();
    private ArrayList<JTextField> address_street = new ArrayList<>();
    private ArrayList<JTextField> address_code = new ArrayList<>();
    private ArrayList<JTextField> address_city = new ArrayList<>();
    private ArrayList<JTextField> address_country = new ArrayList<>();
    private ArrayList<JPanel> address_panel = new ArrayList<>();
    private ArrayList<Integer> address_id = new ArrayList<>();
     
    /* Telefonnummer */
    private ArrayList<JButton> phone_remove_button = new ArrayList<>();
    private ArrayList<JComboBox> phone_combo = new ArrayList<>();
    private ArrayList<JTextField> phone_text = new ArrayList<>();
    private ArrayList<JPanel> phone_panel = new ArrayList<>();
    private ArrayList<Integer> phone_id = new ArrayList<>();
    
    /* Gruppen */
    private ArrayList<JButton> group_remove_button = new ArrayList<>();
    private ArrayList<JTextField> group_text = new ArrayList<>();
    private ArrayList<JPanel> group_panel = new ArrayList<>();
    private ArrayList<Integer> group_id = new ArrayList<>();
    
    /* Alle vorhandenen Gruppen*/
    public ArrayList<GroupDTO> all_groups = new ArrayList<>();
    
    /*Gruppen von Kontakt*/
    public ArrayList<GroupDTO> contact_groups = new ArrayList<>();
    
    /*Geloeschte Daten*/
    public ArrayList<Integer> remove_groups = new ArrayList<>();
    public ArrayList<Integer> remove_addresses = new ArrayList<>();
    public ArrayList<Integer> remove_phones = new ArrayList<>();
    public ArrayList<Integer> remove_emails = new ArrayList<>();   

    
    
    /**
     * View default Konstruktor
     * @param controller
     */
    public ContactsView(ContactsController controller) {
        super();
        this.controller = controller;
        
        /* Alle Komponente Initialisieren */
        initComponents();

        /* Kontakt Tab zu Frame hinzufuegen */
        this.controller.addViewToFrame(CONTACTS_TITLE, this);
    }

    
    
    /***************************************************************************
     * Alle Grafikkomponenten zeichnen und anordnen
     **************************************************************************/
    @Override
    protected void initComponents() {

        /* Linke Spalte */
        //Suchfeld
        search_textfield = new JTextField(CONTACT_TAB_DEFAULT_SEARCH_TEXT);
        search_textfield.setBackground(Color.white);
        search_textfield.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                searchTextFocusGained(fe);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                searchTextFocusLost(fe);
            }
        });
        search_textfield.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                searchTextChange(de);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                searchTextChange(de);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                searchTextChange(de);
            }
        });
        
        //Liste
        separatorlist = new JSeparatorList();
        separatorlist.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                contactListValueChanged(lse);
            }
        });
        
        //Scroll Pane
        list_scrollpane = new JScrollPane();
        list_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        list_scrollpane.setMinimumSize(new Dimension(CONTACT_TAB_GROUPLIST_MIN_WIDTH, CONTACT_TAB_GROUPLIST_MIN_HEIGHT));
        list_scrollpane.setViewportView(separatorlist); //Separator Liste hinzufuegen
        

        /* Rechte Spalte */
        //Nachricht Schaltflaeche 
        message_button = new JButton();
        message_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"messages32x32.png")));  
        message_button.setMnemonic(CONTACT_TAB_MESSAGE_MNEMONIC);
        message_button.setToolTipText(CONTACT_TAB_MESSAGE_TOOLTIP);
        message_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                messageButtonActionPerformed(ae);
            }
        });
        
        //Speichern Schaltflaeche
        save_button = new JButton();
        save_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"save32x32.png")));
        save_button.setMnemonic(CONTACT_TAB_SAVE_MNEMONIC);
        save_button.setToolTipText(CONTACT_TAB_SAVE_TOOLTIP);
        save_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                saveButtonActionPerformed(ae);
            }
        });
        
        //Hinzufuegen Schaltflaeche
        add_button = new JButton();
        add_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"add32x32.png")));
        add_button.setMnemonic(CONTACT_TAB_ADD_MNEMONIC);
        add_button.setToolTipText(CONTACT_TAB_ADD_TOOLTIP);
        add_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addButtonActionPerformed(ae);
            }
        });
        
        //Entfernen Schaltflaeche
        remove_button = new JButton();
        remove_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"remove32x32.png")));
        remove_button.setMnemonic(CONTACT_TAB_REMOVE_MNEMONIC);
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
        detail_static_name_textfield.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                contactnameTextFocusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                contactnameTextFocusLost(e);
            }
        });
        detail_static_name_textfield.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                contactnameTextChange(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                contactnameTextChange(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                contactnameTextChange(e);
            }
        });
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
        detail_static_prename_textfield.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent e) {
                contactprenameTextFocusGained(e);
            }

            @Override
            public void focusLost(FocusEvent e) {
                contactprenameTextFocusLost(e);
            }
        });
        detail_static_prename_textfield.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent e) {
                contactprenameTextChange(e);
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                contactprenameTextChange(e);
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                contactprenameTextChange(e);
            }
        });
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
        detail_dynamic_imageicon_email = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"add16x16.png"));
        detail_dynamic_addbutton_email = new JButton("      Hinzufügen");
        detail_dynamic_addbutton_email.setIcon(detail_dynamic_imageicon_email);
        detail_dynamic_panel_email.add(detail_dynamic_label_email, "cell 0 0");
        detail_dynamic_panel_email.add(detail_dynamic_separator_email, "wrap");
        detail_dynamic_panel_email.add(detail_dynamic_addbutton_email,"span,wrap");
        detail_dynamic_addbutton_email.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addEmail(CONTACT_TAB_DEFAULT_EMAIL_TEXT, "Default",CONTACT_DEFAULT_ID);
                System.err.println("MLKDSNMCKDSNCLKNDS");
            }
        });
        
        
        //Dynamic Panel Adresse
        detail_dynamic_panel_address = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_address.setBackground(Color.white);
        detail_dynamic_label_adress = new JLabel(CONTACT_TAB_ADRESS_LABEL);
        detail_dynamic_separator_address = new JSeparator();
        detail_dynamic_imageicon_address = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"add16x16.png"));
        detail_dynamic_addbutton_address = new JButton("      Hinzufügen");
        detail_dynamic_addbutton_address.setIcon(detail_dynamic_imageicon_address);
        detail_dynamic_panel_address.add(detail_dynamic_label_adress, "cell 0 0");
        detail_dynamic_panel_address.add(detail_dynamic_separator_address, "cell 1 0,wrap");
        detail_dynamic_panel_address.add(detail_dynamic_addbutton_address,"span,wrap");
        detail_dynamic_addbutton_address.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addAddress(CONTACT_TAB_DEFAULT_STREET_TEXT, 
                        CONTACT_TAB_DEFAULT_CODE_TEXT, 
                        CONTACT_TAB_DEFAULT_CITY_TEXT, 
                        CONTACT_TAB_DEFAULT_COUNTRY_TEXT, "Default",CONTACT_DEFAULT_ID);
            }
        });
        
        
        //Dynamic Panel Telefonnummer
        detail_dynamic_panel_phone = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_phone.setBackground(Color.white);
        detail_dynamic_label_phone = new JLabel(CONTACT_TAB_PHONE_LABEL);
        detail_dynamic_separator_phone = new JSeparator();
        detail_dynamic_imageicon_phone = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"add16x16.png"));
        detail_dynamic_addbutton_phone = new JButton("      Hinzufügen");
        detail_dynamic_addbutton_phone.setIcon(detail_dynamic_imageicon_phone);
        detail_dynamic_panel_phone.add(detail_dynamic_label_phone, "cell 0 0");
        detail_dynamic_panel_phone.add(detail_dynamic_separator_phone, "cell 1 0,wrap");
        detail_dynamic_panel_phone.add(detail_dynamic_addbutton_phone,"span,wrap");
        detail_dynamic_addbutton_phone.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addPhone(CONTACT_TAB_DEFAULT_PHONE_TEXT, "Default",CONTACT_DEFAULT_ID);

                System.out.println("ADD");
            }
        });       
        
        //Dynamic Panel Gruppen
        detail_dynamic_panel_group = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_group_choose = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_dynamic_panel_group.setBackground(Color.white);
        detail_dynamic_panel_group_choose.setBackground(Color.white);
        detail_dynamic_label_group = new JLabel(CONTACT_TAB_GROUP_LABEL);
        detail_dynamic_separator_group = new JSeparator();
        detail_dynamic_imageicon_group = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"add16x16.png"));
        detail_dynamic_addbutton_group = new JButton("      Hinzufügen");
        String[] groups = {"PRIVATE", "BUSINESS", "OTHER"};
        detail_dynamic_combobox_group = new JComboBox(groups);
        detail_dynamic_combobox_group.addItem("Test");
        detail_dynamic_addbutton_group.setIcon(detail_dynamic_imageicon_group);
        detail_dynamic_panel_group.add(detail_dynamic_label_group, "cell 0 0");
        detail_dynamic_panel_group.add(detail_dynamic_separator_group, "cell 1 0,wrap");
        detail_dynamic_panel_group_choose.add(detail_dynamic_combobox_group, "cell 0 0");
        detail_dynamic_panel_group_choose.add(detail_dynamic_addbutton_group,"cell 1 0");
        detail_dynamic_panel_group.add(detail_dynamic_panel_group_choose,"span,wrap");
        detail_dynamic_addbutton_group.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                addGroup(((String)detail_dynamic_combobox_group.getSelectedItem()),CONTACT_DEFAULT_ID);
                setSaveButtonState(true);
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
        detail_main_panel.add(detail_dynamic_panel_group, "wrap");
        
        
        //Scroll Pane
        detail_scrollpane = new JScrollPane();
        detail_scrollpane.setMinimumSize(new Dimension(CONTACT_TAB_GROUPDETAIL_MIN_WIDTH, CONTACT_TAB_GROUPDETAIL_MIN_HEIGHT));
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

    
    
    /***************************************************************************
     * View -> Controller
     * Methoden werden direkt von Listener der Grafikelementen angesprochen
     **************************************************************************/ 
    /**
     * Neuer Kontakt generieren
     * @param ae ActionEvent
     */
    private void addButtonActionPerformed(ActionEvent ae) {
        controller.addContact();
    }
   
    
    /**
     * Kontakt Löschen
     * @param ae ActionEvent
     */
    private void removeButtonActionPerformed(ActionEvent ae) {
        controller.removeContact();
        
    }

    
    /**
     * Kontakt Speichern
     * @param ae ActionEvent
     */
    private void saveButtonActionPerformed(ActionEvent ae) {
        controller.saveContact();
    }
    
    
    /**
     * E-Mail an erste E-Mail Adresse von Kontakt schreiben
     * @param ae ActionEvent
     */
    private void messageButtonActionPerformed(ActionEvent ae) {
        JTextField email;
        String emailaddress;
        if(email_text != null) {
            email = email_text.get(0);
            emailaddress = email.getText();
            controller.sendMessage(emailaddress);
        }
    }
    
    /**
     * Such-Textfeld selektiert
     * @param fe FocusEvent
     */
    private void searchTextFocusGained(FocusEvent fe) {
        controller.searchContactFocusGained();
    }
    
    /**
     * Such-Textfeld deselektiert
     * @param fe FocusEvent
     */
    private void searchTextFocusLost(FocusEvent fe) {
        controller.searchContactFocusLost();
    }
    
    /**
     * Such-Textfeld Eintrag geaendert
     * @param fe DocumentEvent
     */
    private void searchTextChange(DocumentEvent de) {
        controller.searchContact();
    }
    
    
    /**
     * Neuer Kontakt laden
     * @param lse ListSelectionEvent
     */
    private void contactListValueChanged(ListSelectionEvent lse) {
        /* Nur einmaliger Event erlauben */
        if(lse.getValueIsAdjusting() == false)
            controller.getContact();
        
    }
    
    
    /**
     * Kontakt wurde in Liste ausgewählt
     * @param e FocusEvent
     */
    private void contactnameTextFocusGained(FocusEvent e) {
        controller.nameContactFocusGained();
    }
    
    
    /**
     * Kontakt wurde abgewählt
     * @param e FocusEvent
     */
    private void contactnameTextFocusLost(FocusEvent e) {
        controller.nameContactFocusLost();
    }
    
    
    /**
     * Kontakt Name wurde geändert
     * @param e DocumentEvent
     */
    private void contactnameTextChange(DocumentEvent e) {
        controller.nameContactChange();   
    }
    
    
    /**
     * Kontakt Vorname wurde selektiert
     * @param e FocusEvent
     */
    private void contactprenameTextFocusGained(FocusEvent e) {
        controller.prenameContactFocusGained();
    }
    
    
    /**
     * Kontakt Vorname wurde deselektiert
     * @param e FocusEvent
     */
    private void contactprenameTextFocusLost(FocusEvent e) {
        controller.prenameContactFocusLost();
    }
    
    
    /**
     * Kontakt Vorname wurde geändert
     * @param e DocumentEvent
     */
    private void contactprenameTextChange(DocumentEvent e) {
        controller.prenameContactChange();   
    }
    
    
    /**
     * E-Mail senden Button einer selektierten Adresse gedrückt
     * @param e ActionEvent
     */
    private void contactsendEmailAcrionEvent(ActionEvent e) {
        JButton button = (JButton)e.getSource();
        int index = email_send_button.indexOf(button);
        JTextField text = email_text.get(index);
        controller.sendMessage(text.getText());
    }
    
    
    /**
     * E-Mail Adresse angewählt
     * @param e FocusEvent
     */
    private void emailTextFocusGained(FocusEvent e) {
        controller.emailContactFocusGained(e.getSource());
    }
    
    
    /**
     * E-Mail Adresse abgewählt
     * @param e FocusEvent
     */
    private void emailTextFocusLost(FocusEvent e) {
        controller.emailContactFocusLost(e.getSource());
    }
    
    
    /**
     * E-Mail Adresse geändert
     * @param e Object
     */
    private void emailTextChange(Object obj) {
        controller.emailContactChange(obj);
    }
    
    
    /**
     * Telefonnummer angewählt
     * @param e FocusEvent
     */
    private void phoneTextFocusGained(FocusEvent e) {
        controller.phoneContactFocusGained(e.getSource());
    }
    
    
    /**
     * Telefonnummer abgewählt
     * @param e FocusEvent
     */
    private void phoneTextFocusLost(FocusEvent e) {
        controller.phoneContactFocusLost(e.getSource());
    }
    
    
    /**
     * Telefonnummer geändert
     * @param e Object
     */
    private void phoneTextChange(Object obj) {
        controller.phoneContactChange(obj);
    }
    
    
    /**
     * Strasse einer Adresse angewählt
     * @param e FocusEvent
     */
    private void addressStreetTextFocusGained(FocusEvent e) {
        controller.addressStreetContactFocusGained(e.getSource());
    }

    
    /**
     * Strasse einer Adresse angewählt
     * @param e FocusEvent
     */
    private void addressStreetTextFocusLost(FocusEvent e) {
        controller.addressStreetContactFocusLost(e.getSource());
    }
    
    
    /**
     * Postleizahl einer Adresse angewählt
     * @param e FocusEvent
     */
    private void addressCodeTextFocusGained(FocusEvent e) {
        controller.addressCodeContactFocusGained(e.getSource());
    }

    
    /**
     * Postleizahl einer Adresse angewählt
     * @param e FocusEvent
     */
    private void addressCodeTextFocusLost(FocusEvent e) {
        controller.addressCodeContactFocusLost(e.getSource());
    }
    
    
    /**
     * Stadt einer Adresse angewählt
     * @param e FocusEvent
     */
    private void addressCityTextFocusGained(FocusEvent e) {
        controller.addressCityContactFocusGained(e.getSource());
    }
    
    
    /**
     * Stadt einer Adresse abgewählt
     * @param e FocusEvent
     */
    private void addressCityTextFocusLost(FocusEvent e) {
        controller.addressCityContactFocusLost(e.getSource());
    }
    
    
    /**
     * Land einer Adresse angewählt
     * @param e FocusEvent
     */
    private void addressCountryTextFocusGained(FocusEvent e) {
        controller.addressCountryContactFocusGained(e.getSource());
    }
    
    
    /**
     * Land einer Adresse abgewählt
     * @param e FocusEvent
     */
    private void addressCountryTextFocusLost(FocusEvent e) {
        controller.addressCountryContactFocusLost(e.getSource());
    }
    
    
    /**
     * Adresse hat geändert
     * @param obj Object
     */
    private void addressTextChange(Object obj) {
        controller.addressContactChange(obj);
    }
    
    
    
    /***************************************************************************
     * Controller -> View
     * setter und getter Methoden, die dem Controller das Steuern ermoeglichen
     **************************************************************************/
    /**
     * Index des selektierten Kontakts der Uebersichtsliste
     * @return Index: -1 wenn nichts selektiert ist 
     */
    public int getSelectedContactIndex() {
        return this.separatorlist.getSelectedIndex();
    }
    
    
    /**
     * Kontakt mit dem angegebenen Index selektieren
     * @param index Gruppen-Index
     */
    public void setSelectedContactIndex(int index) {
        this.separatorlist.setSelectedIndex(index);
    }
    
    
    /**
     * ID eines Kontakts in der Uebersichtsliste
     * @param index Index der Gruppe in der Liste
     * @return Gruppen ID
     */
    public int getContactIdOfIndex(int index) {
        ListMember member = this.separatorlist.getListMemberOfIndex(index);
        return member.getID();
    }
    
    
    /**
     * Name eines Kontakts in der Uebersichtsliste
     * @param index Index der Gruppe in der Liste
     * @return Gruppen-Name
     */
    public String getContactNameOfIndex(int index) {
        ListMember member = this.separatorlist.getListMemberOfIndex(index);
        return member.getText();
    }
    
    
    /**
     * Kontakt-Vorname temporaer aendern
     * @param contact_prename Kontakt-Vorname
     */
    public void setContactPrename(String contact_prename) {
        this.detail_static_prename_textfield.setText(contact_prename);
    }
    
    
    /**
     * Kontakt-Vorname ändern aktivieren/deaktivieren
     * @param state Enable Status
     */
    public void setContactPrenameTextState(boolean state) {
        this.detail_static_prename_textfield.setEnabled(state);
    }
    
    
    /**
     * Kontakt-Nachname temporaer aendern
     * @param contact_lastname Kontakt-Nachname
     */
    public void setContactLastname(String contact_lastname) {
        this.detail_static_name_textfield.setText(contact_lastname);
    }
    
    
    /**
     * Kontakt-Nachname ändern aktivieren/deaktivieren
     * @param state Enable Status
     */
    public void setContactLastnameTextState(boolean state) {
        this.detail_static_name_textfield.setEnabled(state);
    }
    
    
    /**
     * Kontakt Uebersichtsliste anpassen
     * @param group_id_index Index oder ID der zu aendernden Gruppe
     * @param group_name Gruppen Name
     * @param mode Art der Aenderung
     */
    public void setContactList(int group_id_index, String group_name, String mode) {
        
        ListMember member;
        switch(mode) {
            case CONTACT_ADD_CONTACT:
                member = this.separatorlist.addListMember(group_name, group_id_index);
                setSelectedContactIndex(this.separatorlist.getListMemberIndex(member)); //TODO unschoen!!!
                break;
            case CONTACT_REMOVE_GROUP_WITH_ID:
                this.separatorlist.removeListMember(group_id_index);
                break;
            case CONTACT_REMOVE_GROUP_WITH_INDEX:
                this.separatorlist.removeListMemberOfIndex(group_id_index);
                break;
        }
    }
    
    
    /**
     * Event Listener fuer die naechsten 2 Aktionen ausschalten
     * @param state 
     */
    public void setContactListSilent(boolean state) {
        this.separatorlist.setListenerSilent(state);
    }
    
    
    /**
     * Liste Löschen
     */
    public void setContactListEmpty() {
        int contact_quantity;
        
        contact_quantity = getContactQuantity();
        
        /* Alle Elemente der Liste zuerst loeschen */
        for(int i = 0; i < contact_quantity; i++){    
            setContactList(1, "", CONTACT_REMOVE_GROUP_WITH_INDEX);
        }
    }
    
    
    /**
     * Alle Email-Adressen loeschen
     */
    public void setEmailEmpty() {
        if(email_panel.size()>0) {
            for(JPanel panel : email_panel) {
                detail_dynamic_panel_email.remove(panel);
            }  
            email_panel.removeAll(email_panel);
            email_text.removeAll(email_text);
            email_remove_button.removeAll(email_remove_button);
            email_send_button.removeAll(email_send_button);
            email_combo.removeAll(email_combo);
            email_id.removeAll(email_id);
            remove_emails.removeAll(remove_emails);
            detail_dynamic_panel_email.revalidate();
        } 
    }
    
    
    /**
     * Alle Adressen-Adressen loeschen
     */
    public void setAddressEmpty() {
        if(address_panel.size()>0) {
            for(JPanel panel : address_panel) {
                detail_dynamic_panel_address.remove(panel);
            }  
            address_panel.removeAll(address_panel);
            address_street.removeAll(address_street);
            address_city.removeAll(address_city);
            address_code.removeAll(address_code);
            address_country.removeAll(address_country);
            address_remove_button.removeAll(address_remove_button);
            address_combo.removeAll(address_combo);
            address_id.removeAll(address_id);
            detail_dynamic_panel_email.revalidate();
        } 
    }
    
    
    /**
     * Alle Adressen-Adressen loeschen
     */
    public void setGroupEmpty() {
        if(group_panel.size()>0) {
            for(JPanel panel : group_panel) {
                detail_dynamic_panel_group.remove(panel);
            }  
            group_panel.removeAll(group_panel);
            group_text.removeAll(group_text);
            group_id.removeAll(group_id);
            group_remove_button.removeAll(group_remove_button);
            remove_groups.removeAll(remove_groups);
            detail_dynamic_panel_group.revalidate();
        } 
    }
    
    
    /**
     * Alle Adressen-Adressen loeschen
     */
    public void setPhoneEmpty() {
        if(phone_panel.size()>0) {
            for(JPanel panel : phone_panel) {
                detail_dynamic_panel_phone.remove(panel);
            }  
            phone_panel.removeAll(phone_panel);
            phone_text.removeAll(phone_text);
            phone_combo.removeAll(phone_combo);
            phone_id.removeAll(phone_id);
            phone_remove_button.removeAll(phone_remove_button);
            detail_dynamic_panel_email.revalidate();
        } 
    }
    
    
    /**
     * Kontrolle ob ein Kontakt vorhanden ist anhand der Kontakt-ID
     * @param contact_id Kontakt-ID
     * @return Kontakt vorhanden: true; Kontakt nicht vorhanden: false
     */
    public boolean getContactState(int contact_id) {
        if(this.separatorlist.containsListMember(contact_id) == null)
            return false;
        else
            return true;
    }
    
    
    /**
     * Groesse der Kontakt-Ubersichtsliste 
     */
    public int getContactListSize() {
        return this.separatorlist.getListSize();
    }
    
    
    /**
     * Anzahl vorhanden Kontakte
     */
    public int getContactQuantity() {
        return this.separatorlist.getListMemberSize();
    }
    
    
    /**
     * Save-Button aktiviern oder deaktivieren
     * @param state true: Speichern moeglich; false: Speichern nicht moeglich
     */
    public void setSaveButtonState(boolean state) {
        this.save_button.setEnabled(state);
    }
    
    
    /**
     * Message-Button aktiviern oder deaktivieren
     * @param state
     */
    public void setMessageButtonState(boolean state) {
        message_button.setEnabled(state);
    }
    
    
    /**
     * Loeschen-Button aktiviern oder deaktivieren
     * @param state
     */
    public void setRemoveButtonState(boolean state) {
        remove_button.setEnabled(state);
    }
    
    
    /**
     * Hinzufuegen Email-Button aktiviern oder deaktivieren
     * @param state
     */   
    public void setAddEmailButtonState(boolean state) {
        detail_dynamic_addbutton_email.setEnabled(state);
    }
    
    
    /**
     * Hinzufuegen Address-Button aktiviern oder deaktivieren
     * @param state
     */       
    public void setAddAddressButtonState(boolean state) {
        detail_dynamic_addbutton_address.setEnabled(state);
    }
    
    
    /**
     * Hinzufuegen Telefonnummer-Button aktiviern oder deaktivieren
     * @param state
     */   
    public void setAddPhoneButtonState(boolean state) {
        detail_dynamic_addbutton_phone.setEnabled(state);
    }
    
    
    /**
     * Hinzufuegen Gruppen-Button aktiviern oder deaktivieren
     * @param state
     */  
    public void setAddGroupButtonState(boolean state) {
        detail_dynamic_addbutton_group.setEnabled(state);
    }
    
    
    /**
     * Auswahl Gruppen aktiviern oder deaktivieren
     * @param state
     */  
    public void setAddGroupComboState(boolean state) {
        detail_dynamic_combobox_group.setEnabled(state);
    }
    
    
    /**
     * Hinzufuegen Gruppennamen zur Combobox
     * @param text Gruppennahmen
     */  
    public void setAddGroupComboItem(String text) {
        detail_dynamic_combobox_group.addItem(text);
    }
    
    
    /**
     * Anzahl Gruppen zurückgeben
     * @reutrn Gruppenanzahl
     */  
    public int getGroupQuantity() {
        return all_groups.size();
    }
    
    
    /**
     * Nachname von aktuellem Kontakt zurückgeben
     * @return Nachname
     */  
    public String getContactName() {
        return detail_static_name_textfield.getText();
    }
    
    
    /**
     * Gruppen-Namen selektieren
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     */
    public void setContactNameSelection(int first, int last) {
        detail_static_name_textfield.select(first, last);
    }
    
    
    /**
     * Vornachname von aktuellem Kontakt zurückgeben
     * @return Vornachname
     */   
    public String getContactPrename() {
        return detail_static_prename_textfield.getText();
    }
    
    
    /**
     * Gruppen-VorNamen selektieren
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     */
    public void setContactPrenameSelection(int first, int last) {
        detail_static_prename_textfield.select(first, last);
    }
    
    
    /**
     * Auslesen aller E-Mail Adressen und in einem String Array zurückgeben
     * @return Emailadressen
     */  
    public String[] getEmailAddresses() {
        String[] emails = new String[email_text.size()];
        int i = 0;
        
        if(email_text.size() > 0) {
            for(JTextField text : email_text) {
                emails[i] = text.getText();
                i++;
            }
        } else
            emails = null; 
        return emails;
    }
    
    
    /**
     * Auslesen aller E-Mail Typen und in einem String Array zurückgeben
     * @return Emailtypen
     */  
    public String[] getEmailTypes() {
       String[] types = new String[email_combo.size()];
       int i = 0;
        
        if(email_combo.size() > 0) {
            for(JComboBox combo : email_combo) {
                types[i]=((String)combo.getSelectedItem());
                i++;
            }
        } else
            types = null;
        
        return types;
    }
    
    
    /**
     * Auslesen aller E-Mail ids und in einem String Array zurückgeben
     * @return Emailids
     */  
     public Integer[] getEmailIDs() {  
        if(email_id.size() > 0)
            return (Integer[])email_id.toArray(new Integer[email_id.size()]);
        else
            return null;
    }
    
     
    /**
     * Auslesen aller Telefonnummern und in einem String Array zurückgeben
     * @return Telefonnummern
     */  
     public String[] getPhoneNumbers() {
        String[] phone = new String[phone_text.size()];//new ArrayList<>();
        int i = 0;
        
        if(phone_text.size() > 0) {
            for(JTextField text : phone_text) {
                phone[i] = text.getText();
                i++;
            }
        } else
            phone = null;
        
        return phone;
    }
    
     
    /**
     * Auslesen aller Telefonnummerntypen und in einem String Array zurückgeben
     * @return Telefontypen
     */   
     public String[] getPhoneTypes() {
       String[] types = new String[phone_combo.size()];//new ArrayList<>();
       int i = 0;
        
        if(phone_combo.size() > 0) {
            for(JComboBox combo : phone_combo) {
                types[i]=((String)combo.getSelectedItem());
                i++;
            }
        } else
            types = null;
        
        return types;
    }   
      
      
    /**
     * Auslesen aller Telefonnummernids und in einem String Array zurückgeben
     * @return Telefonnummerids
     */  
    public Integer[] getPhoneIDs() {

       if(phone_id.size() > 0)
          return (Integer[])phone_id.toArray(new Integer[phone_id.size()]);
       else
          return null;
        
    }
     
    
    /**
     * Auslesen aller Strassen und in einem String Array zurückgeben
     * @return Strassen
     */  
     public String[] getAddressStreets() {
        String[] street = new String[address_street.size()];//new ArrayList<>();
        int i = 0;
        
        if(address_street.size() > 0) {
            for(JTextField text : address_street) {
                street[i] = text.getText();
                i++;
            }
        } else
            street = null;
        
        return street;
    }
   
     
    /**
     * Auslesen aller Postleizahlen und in einem String Array zurückgeben
     * @return Postleizahlen
     */  
    public String[] getAddressCodes() {
      String[] code = new String[address_code.size()];//new ArrayList<>();
      int i = 0;

      if(address_code.size() > 0) {
          for(JTextField text : address_code) {
              code[i] = text.getText();
              i++;
              //emails.add(text.getText());
          }
      } else
          code = null;

      return code;
    }
     
    
    /**
     * Auslesen aller Städte und in einem String Array zurückgeben
     * @return Städte
     */  
    public String[] getAddressCitys() {
        String[] city = new String[address_city.size()];//new ArrayList<>();
        int i = 0;
        
        if(address_city.size() > 0) {
            for(JTextField text : address_city) {
                city[i] = text.getText();
                i++;
                //emails.add(text.getText());
            }
        } else
            city = null;
        
        return city;
    }
     
    
    /**
     * Auslesen aller Länder und in einem String Array zurückgeben
     * @return Länder
     */  
    public String[] getAddressCountrys() {
        String[] country = new String[address_country.size()];//new ArrayList<>();
        int i = 0;
        
        if(address_country.size() > 0) {
            for(JTextField text : address_country) {
                country[i] = text.getText();
                i++;
                //emails.add(text.getText());
            }
        } else
            country = null;
        
        return country;
    }
      
         
    /**
     * Auslesen aller Adresstypen und in einem String Array zurückgeben
     * @return Adresstypen
     */  
    public String[] getAddressTypes() {
       String[] types = new String[address_combo.size()];//new ArrayList<>();
       int i = 0;
        
        if(address_combo.size() > 0) {
            for(JComboBox combo : address_combo) {
                types[i]=((String)combo.getSelectedItem());
                i++;
            }
        } else
            types = null;
        
        return types;
    }
  
    /**
     * Auslesen aller Adressenids und in einem String Array zurückgeben
     * @return AderessenIDs
     */   
    public Integer[] getAddressIDs() {
           
        if(address_id.size() > 0)
            return (Integer[])address_id.toArray(new Integer[address_id.size()]);
        else
            return null;
        
    }
     
    
    /**
     * Auslesen aller Gruppen und in einem String Array zurückgeben
     * @return Gruppen
     */  
    public String[] getGroups() {
       String[] groups = new String[group_text.size()];
       int i = 0;
       int j = 0;
       boolean test = false;
        if(group_text.size() > 0) {
             for(JTextField group : group_text) {
                
                for(Integer deletegroup : remove_groups){ 
                 
                    if(j==deletegroup)
                        test=true;
                }
              j++;
              if(test==false)
              {
                  groups[i] = group.getText();    
                  i++;
              }
              else
                  test=false;
            }
        } else
            groups = null;
        
        return groups;
    }
        
    
    /**
     * Alle gelösten E-Mails in einem Integer Array zurückgeben
     * @return gelösche Emails
     */      
    public Integer[] getRemovedEmails() {
        Integer removes[]= new Integer[remove_emails.size()]; 
        int i=0;
        if(remove_emails.size() > 0) {
            for(Integer remove : remove_emails) {
                removes[i]=remove;
                i++;
            }
        } 
        else
            removes = null;
        
        return removes;    
    }
       
    
    /**
     * Suche-Text
     * @return Such-Text
     */
    public String getSearchText() {
        return this.search_textfield.getText();
    }
    
    
    /**
     * Suche-Text setzen
     * @param text Suche-Text
     */
    public void setSearchText(String text) {
        this.search_textfield.setText(text);
    }
    
    
    /**
     * Suche selektieren
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     */
    public void setSearchSelection(int first, int last) {
        search_textfield.select(first, last);
    }
    
    
    /**
     * Ausgewählte E-Mail Adresse
     * @param obj Objekt JTextField in welchem E-Mail gespeichert ist
     * @return E-Mail
     */
    public String getContactEmail(Object obj) {
        return ((JTextField)obj).getText();
    }
    
    
    /**
     * Selektieren E-Mail Adresse
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     * @param obj Objekt JTextField in welchem E-Mail gespeichert ist
     */ 
    public void setContactEmailSelection(int first, int last, Object obj) {
        ((JTextField)obj).select(first, last);
    }
    
    
    /**
     * Ausgewählte Telefonnummer
     * @param obj Objekt JTextField in welchem Telefonnummer gespeichert ist
     * @return Telefonnummer
     */
    public String getContactPhone(Object obj) {
        return ((JTextField)obj).getText();
    }
    
    
    /**
     * Selektieren Telefonnummer
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     * @param obj Objekt JTextField in welchem Telefonnumer gespeichert ist
     */ 
    public void setContactPhoneSelection(int first, int last, Object obj) {
        ((JTextField)obj).select(first, last);
    }
    
    
    /**
     * Ausgewählte Strasse
     * @param obj Objekt JTextField in welchem Strasse gespeichert ist
     * @return Strasse
     */ 
    public String getContactAddressStreet(Object obj) {
        return ((JTextField)obj).getText();
    }
    
    
    /**
     * Selektieren Strasse
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     * @param obj Objekt JTextField in welchem Strasse gespeichert ist
     */ 
    public void setContactAddressStreetSelection(int first, int last, Object obj) {
        ((JTextField)obj).select(first, last);
    }
    
    
    /**
     * Ausgewählte Postleizahl
     * @param obj Objekt JTextField in welchem Postleizahl gespeichert ist
     * @return Postleizahl
     */ 
    public String getContactAddressCode(Object obj) {
        return ((JTextField)obj).getText();
    }
    
    
    /**
     * Selektieren Postleizahl
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     * @param obj Objekt JTextField in welchem Postleizahl gespeichert ist
     */  
    public void setContactAddressCodeSelection(int first, int last, Object obj) {
        ((JTextField)obj).select(first, last);
    }
    
    
    /**
     * Ausgewählte Stadt
     * @param obj Objekt JTextField in welchem Stadt gespeichert ist
     * @return Stadt
     */
    public String getContactAddressCity(Object obj) {
        return ((JTextField)obj).getText();
    }
    
    
    /**
     * Selektieren Stadt
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     * @param obj Objekt JTextField in welchem Stadt gespeichert ist
     */   
    public void setContactAddressCitySelection(int first, int last, Object obj) {
        ((JTextField)obj).select(first, last);
    }
   
    
    /**
     * Ausgewähltes Land
     * @param obj Objekt JTextField in welchem Land gespeichert ist
     * @return Land
     */
    public String getContactAddressCountry(Object obj) {
        return ((JTextField)obj).getText();
    }
    
    
    /**
     * Selektieren Land
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     * @param obj Objekt JTextField in welchem Land gespeichert ist
     */ 
    public void setContactAddressCountrySelection(int first, int last, Object obj) {
        ((JTextField)obj).select(first, last);
    }
    
    
    /**
     * Gruppe hinzufügen
     * @param group Name der gruppe 
     * @param id Id der Gruppe
     */   
    private void addGroup(String group,int id) {
        /*Layout und Komponente speichern*/
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_group.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_group.getComponents();
        /*Dynamische Komponente Initialisieren*/
        JPanel group_new = new JPanel(new MigLayout("wrap 4"));
        group_new.setBackground(Color.white);
        JTextField group_name = new JTextField(group);
        ImageIcon remove_image = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"remove16x16.png"));
        JButton remove_group = new JButton("Löschen");
        remove_group.setIcon(remove_image);
        group_name.setEditable(false);
        
        group_name.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                selectGroup(fe);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                deselectGroup(fe);
            }
        });
        
        remove_group.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setSaveButtonState(true);
                removeGroup(ae);
            }
        });
  
        detail_dynamic_panel_group.removeAll();
        /*Alle Komponente hinzufügen am Schluss die neu hinzugefügten*/
        for(Component c : all_components) {
            group_new.add(group_name, " span 2");
            detail_dynamic_panel_group.add(group_new, "span 2,growx,wrap");
            detail_dynamic_panel_group.add(c, constraint_map.get(c));             
        }

        /* Daten in Liste speichern*/
        group_panel.add(group_new);
        group_text.add(group_name);
        group_remove_button.add(remove_group);
        group_id.add(id);
 
        detail_dynamic_panel_group.revalidate();
    }   
    
    
    /**
     * Telefonnummer hinzufügen
     * @param phone Telefonnummer 
     * @param type Telefonnummertyp
     * @param id TelefonnummerID
     */       
    private void addPhone(String phone, String type,int id) {
        /*Layout und Komponente speichern*/
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_phone.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_phone.getComponents();
        /*Dynamische Komponente Initialisieren*/
        String[] phone_types = {"PRIVATE", "BUSINESS", "OTHER"};
        JPanel phone_new = new JPanel(new MigLayout("wrap 4"));
        phone_new.setBackground(Color.white);
        JComboBox phone_type = new JComboBox(phone_types);
        phone_type.setSelectedItem(type);
        JTextField phone_nummer = new JTextField(phone);
        ImageIcon remove_image = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"remove16x16.png"));
        JButton remove_phone = new JButton("Löschen");
        remove_phone.setIcon(remove_image);
        phone_nummer.setMinimumSize(new Dimension(100, 0));

        phone_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setSaveButtonState(true);
            }
        });
        
        phone_nummer.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                phoneTextFocusGained(fe);
                selectPhone(fe);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                phoneTextFocusLost(fe);
                deselectPhone(fe);
            }
        });
        phone_nummer.getDocument().putProperty("phone", phone_nummer); //Property fuer die Referenz-Zungaenglichkeit
        DocumentListener documentlistener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                phoneTextChange(de.getDocument().getProperty("phone"));
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                phoneTextChange(de.getDocument().getProperty("phone"));
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                phoneTextChange(de.getDocument().getProperty("phone"));
            }
        };
        phone_nummer.getDocument().addDocumentListener(documentlistener);
        
        remove_phone.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                removePhone(ae);
                setSaveButtonState(true);
            }
        });

        detail_dynamic_panel_phone.removeAll();
        /*Alle Komponente hinzufügen am Schluss die neu hinzugefügten*/
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
        phone_id.add(id);
      
        detail_dynamic_panel_phone.revalidate();
    }
    
    
    /**
     * Telefonnummer löschen
     * @param ae
     */   
    private void removePhone(ActionEvent ae) {
        JButton remove = (JButton)ae.getSource();
        int index = phone_remove_button.indexOf(remove);
        JPanel panel = phone_panel.get(index);
        /* Neu hinzugefügte Nummer gleich wieder aus Liste löschen */        
        if(phone_id.get(index)==0)
        {
            panel.removeAll();
            phone_combo.remove(phone_combo.get(index));
            phone_id.remove(phone_id.get(index));
            phone_text.remove(phone_text.get(index));
            phone_remove_button.remove(phone_remove_button.get(index));
            detail_dynamic_panel_phone.remove(panel);
            phone_panel.remove(phone_panel.get(index));
        }
        else
        {
          remove_phones.add(phone_id.get(index));          
          detail_dynamic_panel_phone.remove(panel);
        }
        detail_dynamic_panel_phone.revalidate();
        
    }
    
     
    /**
     * Telefonnummer Angewählt -> Löschenbutton Anzeigen
     * @param ae 
     */      
    private void selectPhone(FocusEvent ae) {
        JTextField text = (JTextField)ae.getSource();
        int index = phone_text.indexOf(text);
        JButton remove = phone_remove_button.get(index);
        JPanel panel = phone_panel.get(index);
        panel.add(remove);
        remove.setVisible(true);
        panel.revalidate();    
    }
    
    
    /**
     * Telefonnummer Abgewählt -> Sendenbutton Anzeigen
     * @param ae 
     */    
    private void deselectPhone(FocusEvent fe) {
        JTextField text = (JTextField)fe.getSource();
        int index = phone_text.indexOf(text);
        try{
            JButton remove = phone_remove_button.get(index);
            JPanel panel = phone_panel.get(index);
            remove.setVisible(false);
            panel.remove(remove);
            panel.revalidate();
        } catch (ArrayIndexOutOfBoundsException ex){
            
        }
        
    }
    
    
    /**
     * Gruppe löschen
     * @param ae
     */    
    private void removeGroup(ActionEvent ae) {
        JButton remove = (JButton)ae.getSource();
        Integer index = group_remove_button.indexOf(remove);
        JPanel panel = group_panel.get(index);
        JTextField text = (JTextField)group_text.get(index);
        panel.remove(text);
        group_text.remove(index);
        group_remove_button.remove(index);
        group_id.remove(index);
        remove_groups.add(index);
        detail_dynamic_panel_group.remove(panel);
        detail_dynamic_panel_group.revalidate();
        group_panel.remove(index);   
    }
    

    /**
     * Gruppe Angewählt -> Löschenbutton Anzeigen
     * @param ae 
     */        
    private void selectGroup(FocusEvent hallo) {
        JTextField text = (JTextField)hallo.getSource();
        int index = group_text.indexOf(text);
        JButton remove = group_remove_button.get(index);
        JPanel panel = group_panel.get(index);
        
        panel.add(remove);
        remove.setVisible(true);
        panel.revalidate();    
    }
    
    
    /**
     * Gruppe Abgewählt -> Sendenbutton Anzeigen
     * @param ae 
     */  
    private void deselectGroup(FocusEvent fe) {
        JTextField text = (JTextField)fe.getSource();
        int index = group_text.indexOf(text);
        JButton remove = group_remove_button.get(index);
        JPanel panel = group_panel.get(index);
        remove.setVisible(false);
        panel.remove(remove);
        panel.revalidate();  
    }
   
    
    /**
     * Adresse hinzufügen
     * @param street Strasse
     * @param code Postleizahl
     * @param city Stadt
     * @param country Land
     * @param type Adressentyp
     * @param id AdresseID
     */  
    private void addAddress(String street, String code, String city, String country ,String type, int id){
        /*Layout und Komponente speichern*/
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_address.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_address.getComponents();
        /*Dynamische Komponente Initialisieren*/
        String[] adress_types = {"PRIVATE","BUSINESS", "OTHER"};
        JPanel address_new = new JPanel(new MigLayout("","rel[]rel[]rel[]min",""));
        address_new.setBackground(Color.white);
        JComboBox address_type = new JComboBox(adress_types);
        address_type.setSelectedItem(type);
        JTextField street_address = new JTextField(street);
        JTextField code_address = new JTextField(code);
        JTextField city_address = new JTextField(city);
        JTextField country_address = new JTextField(country);
        ImageIcon remove_image = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"remove16x16.png"));
        JButton remove_address = new JButton("Löschen");
        remove_address.setIcon(remove_image);
        
        street_address.setMinimumSize(new Dimension(200, 0));
        code_address.setMinimumSize(new Dimension(30, 0));
        city_address.setMinimumSize(new Dimension(166, 0));
        country_address.setMinimumSize(new Dimension(100, 0));
        
        DocumentListener documentlistener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                addressTextChange(de.getDocument().getProperty("address"));
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                addressTextChange(de.getDocument().getProperty("address"));
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                addressTextChange(de.getDocument().getProperty("address"));
            }
        };
        
        address_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setSaveButtonState(true);
            }
        });
        
        street_address.getDocument().putProperty("address", street_address);
        street_address.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                addressStreetTextFocusGained(fe);
                selectAddress(fe,1);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                addressStreetTextFocusLost(fe);
                deselectAddress(fe,1);
            }
        });
        street_address.getDocument().addDocumentListener(documentlistener);
    
        code_address.getDocument().putProperty("address", code_address);
        code_address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                addressCodeTextFocusGained(fe);
                selectAddress(fe,2);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                addressCodeTextFocusLost(fe);
                deselectAddress(fe,2);
            }
        });
        code_address.getDocument().addDocumentListener(documentlistener);
         
        city_address.getDocument().putProperty("address", city_address);
        city_address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                addressCityTextFocusGained(fe);
                selectAddress(fe,3);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                addressCityTextFocusLost(fe);
                deselectAddress(fe,3);
            }
        });
        city_address.getDocument().addDocumentListener(documentlistener);
        
        country_address.getDocument().putProperty("address", country_address);
        country_address.addFocusListener(new FocusListener() {
            @Override
            public void focusGained(FocusEvent fe) {
                addressCountryTextFocusGained(fe);
                selectAddress(fe,4);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                addressCountryTextFocusLost(fe);
                deselectAddress(fe,4);
            }
        });
        country_address.getDocument().addDocumentListener(documentlistener);
           
        remove_address.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                removeAddress(ae);
                setSaveButtonState(true);
            }
        });
        
        /*Alle Komponente hinzufügen am Schluss die neu hinzugefügten*/
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

        /* Daten in Liste speichern*/
        address_panel.add(address_new);
        address_combo.add(address_type);
        address_street.add(street_address);
        address_code.add(code_address);
        address_city.add(city_address);
        address_country.add(country_address);
        address_remove_button.add(remove_address);
        address_id.add(id);
        
        detail_dynamic_panel_address.revalidate();           
     }
    
        
    /**
     * Adresse löschen
     * @param ae
     */       
    private void removeAddress(ActionEvent ae) {
        JButton remove = (JButton)ae.getSource();
        int index = address_remove_button.indexOf(remove);
        JPanel panel = address_panel.get(index);
        
        if(address_id.get(index)==0)
        {
            panel.removeAll();
            address_combo.remove(address_combo.get(index));
            address_id.remove(address_id.get(index));
            address_city.remove(address_city.get(index));
            address_code.remove(address_code.get(index));
            address_country.remove(address_country.get(index));
            address_street.remove(address_street.get(index));
            address_remove_button.remove(address_remove_button.get(index));
            detail_dynamic_panel_address.remove(panel);
            address_panel.remove(address_panel.get(index));
        }
        else{
            remove_addresses.add(address_id.get(index));
            detail_dynamic_panel_address.remove(panel);
        }
        detail_dynamic_panel_address.revalidate();  
    }
    
     
    /**
     * Adresse Angewählt -> Löschenbutton Anzeigen
     * @param ae 
     */        
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
    

    /**
     * Adresse Abgewählt -> Sendenbutton Anzeigen
     * @param ae 
     */  
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
        
        try{
            JButton remove = address_remove_button.get(index);
            JPanel panel = address_panel.get(index);   
            remove.setVisible(false);
            panel.remove(remove);       
            panel.revalidate();
        } catch (ArrayIndexOutOfBoundsException ex) {
          
        } 
    }
    
    
    /**
     * Emailadresse hinzufügen
     * @param email Emailadresse
     * @param type Emailadressentyp
     * @param id EmailadresseID
     */    
    private void addEmail(String email, String type, int id) {
        /*Layout und Komponente speichern*/
        Map<Component, Object> constraint_map = ((MigLayout)detail_dynamic_panel_email.getLayout()).getConstraintMap();
        Component[] all_components = detail_dynamic_panel_email.getComponents();
        /*Dynamische Komponente Initialisieren*/
        String[] email_types = {"PRIVATE", "BUSINESS", "OTHER"};
        JPanel email_new = new JPanel(new MigLayout("wrap 4"));
        email_new.setBackground(Color.white);
        JComboBox email_type = new JComboBox(email_types);
        email_type.setSelectedItem(type);
        JTextField email_adress = new JTextField(email);
        ImageIcon remove_image = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"remove16x16.png"));
        JButton remove_email = new JButton("Löschen");
        remove_email.setIcon(remove_image);
        ImageIcon send_image = new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"messages16x16.png"));
        JButton send_email = new JButton("Senden");
        send_email.setIcon(send_image);
        email_adress.setMinimumSize(new Dimension(100, 0));
        
        email_type.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                setSaveButtonState(true);
            }
        });
        
        email_adress.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                emailTextFocusGained(fe);
                selectEmail(fe);
           }

            @Override
            public void focusLost(FocusEvent fe) {
                emailTextFocusLost(fe);
                deselectEmail(fe);
            }
        });
        email_adress.getDocument().putProperty("address", email_adress); //Property fuer die Referenz-Zungaenglichkeit
        DocumentListener documentlistener = new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                emailTextChange(de.getDocument().getProperty("address"));
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                emailTextChange(de.getDocument().getProperty("address"));
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                emailTextChange(de.getDocument().getProperty("address"));
            }
        };
        email_adress.getDocument().addDocumentListener(documentlistener);
        
        remove_email.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                removeEmail(ae);
                setSaveButtonState(true);
            }
        });
        
        send_email.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                contactsendEmailAcrionEvent(ae);
            }
        });
        
        
        detail_dynamic_panel_email.removeAll();
        /*Alle Komponente hinzufügen am Schluss die neu hinzugefügten*/
        for(Component c : all_components) {
           
            if(c instanceof JButton) {
                email_new.add(email_type);
                email_new.add(email_adress, " span 2");
                email_new.add(send_email);
                detail_dynamic_panel_email.add(email_new, "span 2,growx,wrap");
           }
            
            detail_dynamic_panel_email.add(c, constraint_map.get(c));           
        }
        /* Daten in Liste speichern*/
        email_panel.add(email_new);
        email_combo.add(email_type);
        email_text.add(email_adress);
        email_remove_button.add(remove_email);
        email_send_button.add(send_email);
        email_id.add(id);
       
        detail_dynamic_panel_email.revalidate();
    }
    

    /**
     * Email löschen
     * @param ae 
     */                                
    private void removeEmail(ActionEvent aeremove) {
        JButton remove = (JButton)aeremove.getSource();
        int index = email_remove_button.indexOf(remove);
        JPanel panel = email_panel.get(index);
        /* Neu hinzugefügte Nummer gleich wieder aus Liste löschen */  
        if(email_id.get(index)==0)
        {
            panel.removeAll();
            email_combo.remove(email_combo.get(index));
            email_id.remove(email_id.get(index));
            email_text.remove(email_text.get(index));
            email_remove_button.remove(email_remove_button.get(index));
            email_send_button.remove(email_send_button.get(index));
            detail_dynamic_panel_email.remove(panel);
            email_panel.remove(email_panel.get(index));
        }
        else{
            remove_emails.add(email_id.get(index));
            detail_dynamic_panel_email.remove(panel);
        }
        detail_dynamic_panel_email.revalidate();
        panel.revalidate();
    }
    
     
    /**
     * Email Angewählt -> Löschenbutton Anzeigen
     * @param ae 
     */           
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
    
    
    /**
     * Email Abgewählt -> Sendenbutton Anzeigen
     * @param ae 
     */  
    private void deselectEmail(FocusEvent fedeselect) {
        JTextField text = (JTextField)fedeselect.getSource();
        int index = email_text.indexOf(text);
        try {
            JButton send = email_send_button.get(index);
            JButton remove = email_remove_button.get(index);
            JPanel panel = email_panel.get(index);
            remove.setVisible(false);
            panel.remove(remove);
            panel.add(send);
            panel.revalidate();
            detail_dynamic_label_email.revalidate();
        }
        catch (ArrayIndexOutOfBoundsException ex) {
          
        }        
    }
    
    
    /**
     * Speicherbutton aktiviern oder deaktivieren
     * @param state
     */  
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
     * Model -> View
     **************************************************************************/
    /**
     * Aenderungen aus dem Model in View uebernehmen
     * @param evt 
     */
    @Override
    public void modelPropertyChange(PropertyChangeEvent evt) {
        switch(evt.getPropertyName()) {
            case CONTACT_LIST_SELECT_EVENT:    
            case CONTACT_SEARCH_EVENT:
                if(evt.getNewValue() != null) {
                    setContactListEmpty();
                
                    /* Bei leerer Gruppen Ubersichtsliste, Details-Ansicht leer lassen */
                    if(((ArrayList<ContactDTO>)evt.getNewValue()).size() != 0) {
                        /* Neu Elemente hinzufuegen */
                        for(ContactDTO contact : (ArrayList<ContactDTO>)evt.getNewValue()) {
                            setContactListSilent(true);
                            setContactList(contact.user_id, contact.user_lastname+" "+contact.user_prename, CONTACT_ADD_CONTACT); 
                        }    
                        setContactListSilent(false);
                    } else {
                        /* Details-ansicht leer */
                        setMessageButtonState(false);
                        setSaveButtonState(false);
                        setRemoveButtonState(false);
                        setAddEmailButtonState(false);
                        setAddAddressButtonState(false);
                        setAddPhoneButtonState(false);
                        setAddGroupButtonState(false);
                        setAddGroupComboState(false);
                        setContactPrenameTextState(false);
                        setContactLastnameTextState(false);
                        setContactPrename(CONTACT_TAB_DEFAULT_EMPTYNAME_TEXT);
                        setContactLastname(CONTACT_TAB_DEFAULT_EMPTYNAME_TEXT);
                        setContactListEmpty();
                    }   
                }
                
                /* Falls kein Eintrag selektiert ist, den ersten selektieren */
                controller.selectContact();
                break;
                
                
            case CONTACT_SELECT_EVENT:
                if(evt.getNewValue() != null) {
      
                    /*Daten auf Default*/
                    setEmailEmpty();
                    setPhoneEmpty();
                    setGroupEmpty();
                    setAddressEmpty();
                    
                    /*Name mit Uerbsichtsliste abgleichen wenn zwingend (Aenderungen gemacht) */
                    if(((ContactDTO)evt.getNewValue()).user_lastname.equals(((ContactDTO)evt.getOldValue()).user_lastname)) {
                        if(!(getContactNameOfIndex(getSelectedContactIndex()).split(" "))[0].equals(getContactName()))
                            setContactLastname(((ContactDTO)evt.getNewValue()).user_lastname);
                       
                        setSaveButtonState(false);
                        
                    } else {
                        if(!(getContactNameOfIndex(getSelectedContactIndex()).split(" "))[0].equals(getContactName())) {
                            String[] lastname = getContactNameOfIndex(getSelectedContactIndex()).split(" ");
                            setContactLastname(lastname[0]);
                            setSaveButtonState(true);
                          
                        }
                    }
                    
                    if(((ContactDTO)evt.getNewValue()).user_prename.equals(((ContactDTO)evt.getOldValue()).user_prename)) {
                        if(!(getContactNameOfIndex(getSelectedContactIndex()).split(" "))[1].equals(getContactPrename()))
                            setContactPrename(((ContactDTO)evt.getNewValue()).user_prename);
                        if(((ContactDTO)evt.getNewValue()).user_lastname.equals(((ContactDTO)evt.getOldValue()).user_lastname))
                            setSaveButtonState(false);
                        
                    } else {
                        if(!(getContactNameOfIndex(getSelectedContactIndex()).split(" "))[1].equals(getContactPrename())) {
                            String[] prename = getContactNameOfIndex(getSelectedContactIndex()).split(" ");
                            setContactPrename(prename[1]);
                            setSaveButtonState(true);
                        
                        }
                    }
                    
                    /* Email Adressen */
                    for(ContactEmail email : ((ContactDTO)evt.getNewValue()).contact_email)
                        addEmail(email.email_adress,email.email_type,email.email_id);
                    
                    /* Adressen */
                    for(ContactAdress address : ((ContactDTO)evt.getNewValue()).contact_adress)
                        addAddress(address.adress_street, address.adress_code, address.adress_city, address.adress_country, address.adress_type,address.adress_id);
                    
                     /* Telefon */
                    for(ContactPhone phone : ((ContactDTO)evt.getNewValue()).contact_phone)
                        addPhone(phone.phone_number, phone.phone_type,phone.phone_id);
                    
                    controller.getContactGroups((ContactDTO)evt.getNewValue());
                    
                    /* Falls Gruppenmitglieder vorhanden sind, Senden ermoeglichen */
                    if(email_panel.size() > 0)
                        setMessageButtonState(true);
                    else
                        setMessageButtonState(false);
                } else {
                    setContactLastname(CONTACT_TAB_DEFAULT_NAME_TEXT);
                }
                break;
                
                
            case CONTACT_INSERT_EVENT:
                if(evt.getNewValue() != null) {
                    setContactList(CONTACT_DEFAULT_ID, "", CONTACT_REMOVE_GROUP_WITH_ID);
                }
               
                
            case CONTACT_UPDATE_EVENT:
                if(evt.getNewValue() != null) {
                    /* Gruppe zu Liste hinzufuegen oder aktualisieren */
                    ListMember member = separatorlist.addListMember(((ContactDTO)evt.getNewValue()).user_lastname+" "+((ContactDTO)evt.getNewValue()).user_prename, 
                            ((ContactDTO)evt.getNewValue()).user_id);
                    /* Neuer Eintrag selektieren */
                    separatorlist.setSelectedIndex(separatorlist.getListMemberIndex(member));
                    /* Gespeichert -> Speichern nicht mehr moeglich */
                    setSaveButtonState(false);
                }
                break;
             
                
            case CONTACT_DELETE_EVENT:
                if(evt.getNewValue() != null) {
                    /* kontakt aus Liste entfernen */
                    separatorlist.removeListMember(((ContactDTO)evt.getNewValue()).user_id);
                    
                    /*Falls keine Kontakte in der Datenbank sind, loeschen verhindern */
                    if(separatorlist.getListMemberSize() == 0) {
                        setAddEmailButtonState(false);
                        setAddAddressButtonState(false);
                        setAddPhoneButtonState(false);
                        setRemoveButtonState(false);
                        setAddGroupButtonState(false);
                        setAddGroupComboState(false);
                        setContactPrenameTextState(false);
                        setContactLastnameTextState(false);
                        setContactPrename(CONTACT_TAB_DEFAULT_EMPTYNAME_TEXT);
                        setContactLastname(CONTACT_TAB_DEFAULT_EMPTYNAME_TEXT);
                    }
                    
                    /* Falls kein Eintrag selektiert ist, den ersten selektieren */
                    controller.selectContact();
                }
                break;
              
                
            case CONTACT_SELECT_GROUPS_EVENT:
                break;
              
                
            case CONTACT_ADD_GROUP_EVENT:
                break;
              
                
            case CONTACT_DELETE_GROUP_EVENT:
                break;
            
                
            case CONTACT_ALL_GROUP_EVENT:
                 if(evt.getNewValue() != null) {
                    /* Gruppe aus Liste entfernen */
                     detail_dynamic_combobox_group.removeAllItems();
                     ArrayList<GroupDTO> group_list;
                     group_list=(ArrayList<GroupDTO>)evt.getNewValue();
                       
                     if(!group_list.isEmpty()) {
                         /*Alle gruppen in Combobox einfüllen*/
                        for(GroupDTO Group : group_list) {
                          detail_dynamic_combobox_group.addItem(Group.group_name);
                        }
                        all_groups=group_list;
                        
                        if(getContactQuantity() > 0) {
                            setAddGroupButtonState(true);
                            setAddGroupComboState(true);
                        }
                     } else {
                         setAddGroupButtonState(false);
                         setAddGroupComboItem("Keine Gruppen vorhanden");
                         setAddGroupComboState(false);
                     }
                 }
                break;
             
            case CONTACT_ONE_GROUP_EVENT:
                 if(evt.getNewValue() != null) {
                    /* Bestehende Gruppen zu Kontakt hinzufügen */
                    for(GroupDTO groups : ((ArrayList<GroupDTO>)evt.getNewValue()))
                        addGroup(groups.group_name,groups.group_id);
                 contact_groups=(ArrayList<GroupDTO>)evt.getNewValue();
                 }
                
                break;
                
                
            default:
                System.err.println("Unknows Event");
        }
    }
}
