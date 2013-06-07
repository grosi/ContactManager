
package contactmanager.main.groups;

import contactmanager.main.AbstractView;
import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.graphic.GraphicDesign;
import contactmanager.main.graphic.JSeparatorList;
import contactmanager.main.graphic.JSeparatorList.ListMember;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.ListSelectionModel;
import javax.swing.ScrollPaneConstants;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import net.miginfocom.swing.MigLayout;

/**
 * @author grosi
 * @version 0.1
 * @since 02.05.2013
 */
public final class GroupsView extends AbstractView implements GraphicDesign, GroupsInterface{
    
    private GroupsController controller;
    
    /* Linke Spalte */
    private JScrollPane groupoverview_scrollpane;
    private JSeparatorList groupoverview_separatorlist;
    
    /* Rechte Spalte */
    private JScrollPane detail_scrollpane;
    private JPanel detail_main_panel;
    private JPanel detail_static_panel;
    private JLabel detail_static_label;
    private JSeparator detail_static_separator;
    private JTextField detail_static_name_textfield;
    private JPanel detail_member_panel;
    private JScrollPane detail_member_scrollpane;
    private JSeparatorList detail_member_separatorlist;
    private JLabel detail_member_label;
    private JSeparator detail_member_separator;
    
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
    public GroupsView(GroupsController controller) {
        
        super();
        //super(controller.getMainController().getMainFrame());
        this.controller = controller;
        
        /* Alle Komponente Initialisieren */
        initComponents();

        /* Gruppen Tab zu Frame hinzufuegen */
        this.controller.addViewToFrame(GROUPS_TITLE, this);
        //mainFrame.addTab(GROUPS_TITLE, this);
    }
    
    
    
    /**
     * Alle Grafikkomponenten zeichnen und anordnen
     */
    @Override
    protected void initComponents() {
        
        /* Linke Spalte */
        //Suchfeld
        search_textfield = new JTextField(GROUP_TAB_DEFAULT_SEARCH_TEXT);
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
        groupoverview_separatorlist = new JSeparatorList();
        groupoverview_separatorlist.setSelectionMode(ListSelectionModel.SINGLE_SELECTION); /** @TODO */
        groupoverview_separatorlist.addListSelectionListener(new ListSelectionListener() {

            @Override
            public void valueChanged(ListSelectionEvent lse) {
                groupListValueChanged(lse);
            }
        });
        
        
        //Scroll Pane
        groupoverview_scrollpane = new JScrollPane();
        groupoverview_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        groupoverview_scrollpane.setMinimumSize(new Dimension(GROUP_TAB_GROUPLIST_MIN_WIDTH, GROUP_TAB_GROUPLIST_MIN_HEIGHT));
        groupoverview_scrollpane.setViewportView(groupoverview_separatorlist); //Separator Liste hinzufuegen
        

        /* Rechte Spalte */
        //Nachricht Schaltflaeche 
        message_button = new JButton();
        message_button.setIcon(new ImageIcon(IMAGES_FILEPATH+"messages32x32.png"));  
        message_button.setMnemonic(GROUP_TAB_MESSAGE_MNEMONIC);
        message_button.setToolTipText(GROUP_TAB_MESSAGE_TOOLTIP);
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
        save_button.setToolTipText(GROUP_TAB_SAVE_TOOLTIP);
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
        add_button.setToolTipText(GROUP_TAB_ADD_TOOLTIP);
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
        remove_button.setToolTipText(GROUP_TAB_REMOVE_TOOLTIP);
        remove_button.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                removeButtonActionPerformed(ae);
            }
        });
        
        
        //Static Detail Panel
        detail_static_panel = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][]")); //Zeilen Grenzen
        detail_static_panel.setBackground(Color.WHITE);
        detail_static_label = new JLabel(GROUP_TAB_GROUPOVERVIEW_LABEL);
        detail_static_separator = new JSeparator();
        detail_static_name_textfield = new JTextField();//GROUP_TAB_DEFAULT_NAME_TEXT);
        detail_static_name_textfield.addFocusListener(new FocusListener() {

            @Override
            public void focusGained(FocusEvent fe) {
                groupnameTextFocusGained(fe);
            }

            @Override
            public void focusLost(FocusEvent fe) {
                groupnameTextFocusLost(fe);
            }
        });
        
        detail_static_name_textfield.getDocument().addDocumentListener(new DocumentListener() {

            @Override
            public void insertUpdate(DocumentEvent de) {
                groupnameTextChange(de);
            }

            @Override
            public void removeUpdate(DocumentEvent de) {
                groupnameTextChange(de);
            }

            @Override
            public void changedUpdate(DocumentEvent de) {
                groupnameTextChange(de);
            }
        });
        detail_static_panel.add(detail_static_label, "cell 0 0");
        detail_static_panel.add(detail_static_separator, "cell 1 0");
        detail_static_panel.add(detail_static_name_textfield, "cell 0 1 2 1,growx");
        
        
        //Member Panel
        detail_member_panel = new JPanel(new MigLayout("", //Layout Grenzen
                "min[][grow,fill]min", //Spalten Grenzen
                "[][grow,fill]min")); //Zeilen Grenzen
        detail_member_panel.setBackground(Color.WHITE);
        detail_member_label = new JLabel(GROUP_TAB_GROUPMEMBER_LABEL);
        detail_member_separator = new JSeparator();
        detail_member_scrollpane = new JScrollPane();
        detail_member_separatorlist = new JSeparatorList();
        detail_member_scrollpane.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        detail_member_scrollpane.setViewportView(detail_member_separatorlist);
        detail_member_panel.add(detail_member_label, "cell 0 0");
        detail_member_panel.add(detail_member_separator, "cell 1 0");
        detail_member_panel.add(detail_member_scrollpane, "cell 0 1 2 1,growx");
        
        
        //Detail Haupt-Panel
        detail_main_panel = new JPanel(new MigLayout("", //Layout Grenzen
                "[grow,fill]", //Spalten Grenzen
                "[]min[grow,fill]")); //Zeilen Grenzen
        detail_main_panel.setBackground(Color.white);
        detail_main_panel.add(detail_static_panel, "cell 0 0");
        detail_main_panel.add(detail_member_panel, "cell 0 1");
        
        
        //Scroll Pane
        detail_scrollpane = new JScrollPane();
        detail_scrollpane.setMinimumSize(new Dimension(GROUP_TAB_GROUPDETAIL_MIN_WIDTH, GROUP_TAB_GROUPDETAIL_MIN_HEIGHT));
        detail_scrollpane.setViewportView(detail_main_panel);
        
        
        /* Alle Komponente zum Tab-Panel hinzufuegen */
        this.setLayout(new MigLayout("", //Layout Grenzen
                "rel[grow,fill]unrel[grow,fill]rel[]rel[]rel[]rel[]unrel", //Spalten Grenzen
                "unrel[]unrel[grow,fill]unrel")); //Zeilen Grenzen
        this.add(search_textfield, "growy");
        this.add(groupoverview_scrollpane, "cell 0 1");
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
        controller.addGroup();
    }
    
    private void removeButtonActionPerformed(ActionEvent ae) {

        int index = groupoverview_separatorlist.getSelectedIndex();
        
        if(index >=0 ) {
            GroupDTO group = new GroupDTO();
            ListMember listmember = groupoverview_separatorlist.getListMemberOfIndex(index);
            group.group_id = listmember.getID();
            group.group_name = listmember.getText();
            controller.removeGroup(group);
        }
        
        
        
    }

    private void saveButtonActionPerformed(ActionEvent ae) {
        int index = groupoverview_separatorlist.getSelectedIndex();
        GroupDTO group = new GroupDTO();
        
        /* Gruppe existiert */
        
            ListMember listmember = groupoverview_separatorlist.getListMemberOfIndex(index);
            group.group_name = listmember.getText();
            group.group_id = listmember.getID();
            controller.saveGroup(group);
        
    }

    private void messageButtonActionPerformed(ActionEvent ae) {
        List<ListMember> selected_items = groupoverview_separatorlist.getSelectedValuesList();
        int size = selected_items.size();
        GroupDTO group = new GroupDTO();
        
        if(size >= 1) {
            group.group_id = selected_items.get(0).getID();
            group.group_name = detail_static_name_textfield.getText();
            controller.sendMessage(group);
        }   
    }
    
    private void searchTextFocusGained(FocusEvent fe) {
        controller.focusGainedSearchText(search_textfield.getText());
    }
    
    private void searchTextFocusLost(FocusEvent fe) {
        controller.focusLostSearchText(search_textfield.getText());
    }
    
    //TODO
    private void searchTextChange(DocumentEvent de) {
        controller.searchGroup(search_textfield.getText());
    }
    
    private void groupListValueChanged(ListSelectionEvent lse) {
        int index = groupoverview_separatorlist.getSelectedIndex();
        
        /* Nur wenn eine Gruppe selektiert ist Daten von DB holen */
        System.out.println("INDEX: "+index);
        System.out.println("SIZE: "+groupoverview_separatorlist.getListSize());
        if(index > 0 && index != groupoverview_separatorlist.getListSize()) {
            ListMember group_info = groupoverview_separatorlist.getListMemberOfIndex(index);
            if(group_info.getID() != 0) {
                
                /* Ungespeicherte Eintraege loeschen */
                if(groupoverview_separatorlist.containsListMember(0) != null) {
                    groupoverview_separatorlist.removeListMember(0);
                }
                
                GroupDTO group = new GroupDTO();
                group.group_id = group_info.getID();
                group.group_name = group_info.getText();
                controller.getGroup(group);
            }
        }
    }
    
    private void groupnameTextFocusGained(FocusEvent fe) {
        controller.focusGainedGroupName(detail_static_name_textfield.getText());
    }
    
    private void groupnameTextFocusLost(FocusEvent fe) {
        controller.focusLostGroupName(detail_static_name_textfield.getText());
    }
    
    private void groupnameTextChange(DocumentEvent de) {
        int index = groupoverview_separatorlist.getSelectedIndex();
        ListMember group_info;
        
        /* Kontrolle ob ein Eintrag selektiert ist */
        if(index < 0) {
            controller.changeGroupName(null, null, 0);
        } else {
            group_info = groupoverview_separatorlist.getListMemberOfIndex(groupoverview_separatorlist.getSelectedIndex());
            controller.changeGroupName(detail_static_name_textfield.getText(), group_info.getText(), group_info.getID());
        }
            
        
    }
    
    
    /***************************************************************************
     * Controller -> View
     **************************************************************************/
    
    /**
     * Die Detail-Spalte auf Default zuruecksetzen
     */
    public void setDetailToDefault() {
        /*Nur zuruecksetzen wenn noch kein neuer KOntakt angelegt wurde */
        if(groupoverview_separatorlist.containsListMember(0) != null) {
            detail_static_name_textfield.setText(GROUP_TAB_DEFAULT_NAME_TEXT);
            int size = detail_member_separatorlist.getListMemberSize();      

            /* Alle Elemente der Liste loeschen */
            for(int i = 0; i < size; i++)    
                detail_member_separatorlist.removeListMemberOfIndex(1);
            enableSaveButton(false);
        }
    }
    
    /**
     * Gesamter Gruppen-Name selektieren
     */
    public void selectGroupName() {
        detail_static_name_textfield.select(0, detail_static_name_textfield.getText().length());
    }
    
    
    /**
     * Gesamter Gruppen-Name deselektieren
     */
    public void deselectGroupName() {
        detail_static_name_textfield.select(0, 0);
    }
    
    
    /**
     * Gesamte Suche selektieren
     */
    public void selectSearchText() {
        System.out.println("SELECT");
        search_textfield.select(0, search_textfield.getText().length());
    }
    
    
    /**
     * Gesamte Suche deselektieren
     */
    public void deselectSearchText() {
        System.out.println("DESELECT");
        search_textfield.select(0, 0);
        
        if(search_textfield.getText().equals("")) {
            search_textfield.setText(GROUP_TAB_DEFAULT_SEARCH_TEXT);
        }
    }
    
    
    /**
     * Save-Button aktiviern oder deaktivieren
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
    
    
    /**
     * Remove-Button aktiviern oder deaktivieren
     * @param state
     */
    public void enableRemoveButton(boolean state) {
        remove_button.setEnabled(state);
    }
    
    
    /**
     * Fuegt ein neue Gruppe zur Gruppenliste hinzu
     */
    public void addNewGroupToList() {
        ListMember member = groupoverview_separatorlist.addListMember(GROUP_TAB_DEFAULT_NAME_TEXT, 0);
        groupoverview_separatorlist.setSelectedIndex(groupoverview_separatorlist.getListMemberIndex(member));
    }
    
    
    //TODO llöst fehler aus!!!!!!!!!!! java.lang.IllegalStateException: Attempt to mutate in notification
    public void updateGroupToList(String group_name, int group_id) {
        ListMember member = groupoverview_separatorlist.addListMember(group_name, group_id);
        groupoverview_separatorlist.setSelectedIndex(groupoverview_separatorlist.getListMemberIndex(member));
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
            case GROUP_SEARCH_EVENT:
                int groupoverview_size = groupoverview_separatorlist.getListMemberSize();      
        
                /* Alle Elemente der Liste zuerst loeschen */
                for(int i = 0; i < groupoverview_size; i++){    
                    groupoverview_separatorlist.removeListMemberOfIndex(1);
                    System.out.println("REMOVE Group");
                }
                
                if(evt.getNewValue() != null) {
                    /* Neu Elemente hinzufuegen */
                    for(GroupDTO group : (ArrayList<GroupDTO>)evt.getNewValue()) {
                        groupoverview_separatorlist.addListMember(group.group_name, group.group_id);
                        System.out.println("ADD Group");
                    }
                }
                
                /* Falls kein Eintrag selektiert ist, den ersten selektieren */
                if(groupoverview_separatorlist.getSelectedIndex() < 0 && groupoverview_separatorlist.getListMemberSize() > 0)
                    groupoverview_separatorlist.setSelectedIndex(1);
             
                
                break;
                
            case GROUP_SELECT_EVENT:
                int groupmember_size = detail_member_separatorlist.getListMemberSize();
                
                /* Alle Elemente der Liste zuerst loeschen */
                for(int i = 0; i < groupmember_size; i++)    
                    detail_member_separatorlist.removeListMemberOfIndex(1);
                
                if(evt.getNewValue() != null) {
                    
                    detail_static_name_textfield.setText(((GroupDTO)evt.getNewValue()).group_name);
                    
                    /* Neu Elemente hinzufuegen */
                    for(ContactDTO groupmember : ((GroupDTO)evt.getNewValue()).group_contacts) {
                        detail_member_separatorlist.addListMember(groupmember.user_lastname+" "+groupmember.user_prename,
                                groupmember.user_id);
                    }
                    
                    /* Falls Gruppenmitglieder vorhanden sind, Senden ermoeglichen */
                    if(detail_member_separatorlist.getListMemberSize() > 0)
                        enableMessageButton(true);
                    else
                        enableMessageButton(false);
                } else {
                    detail_static_name_textfield.setText(GROUP_TAB_DEFAULT_NAME_TEXT);
                }
                break;
                
            case GROUP_INSERT_EVENT:
                if(evt.getNewValue() != null) {
                    groupoverview_separatorlist.removeListMember(0);
                }
            case GROUP_UPDATE_EVENT:
                
                if(evt.getNewValue() != null) {
                    /* Gruppe zu Liste hinzufuegen oder aktualisieren */
                    ListMember member = groupoverview_separatorlist.addListMember(((GroupDTO)evt.getNewValue()).group_name, 
                            ((GroupDTO)evt.getNewValue()).group_id);
                    /* Neuer Eintrag selektieren */
                    groupoverview_separatorlist.setSelectedIndex(groupoverview_separatorlist.getListMemberIndex(member));
                    
                    /* Gespeichert -> Speichern nicht mehr moeglich */
                    enableSaveButton(false);
                }
                break;
                
            case GROUP_DELETE_EVENT:
                
                if(evt.getNewValue() != null) {
                    /* Gruppe aus Liste entfernen */
                    groupoverview_separatorlist.removeListMember(((GroupDTO)evt.getNewValue()).group_id);
                    
                    /* Falls kein Eintrag selektiert ist, den ersten selektieren */
                    if(groupoverview_separatorlist.getSelectedIndex() < 0 && groupoverview_separatorlist.getListMemberSize() > 0)
                        groupoverview_separatorlist.setSelectedIndex(1);
                }
                
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
