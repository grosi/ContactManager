
package contactmanager.main.groups;

import contactmanager.main.AbstractView;
import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.graphic.JSeparatorList;
import contactmanager.main.graphic.JSeparatorList.ListMember;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.FocusEvent;
import java.awt.event.FocusListener;
import java.beans.PropertyChangeEvent;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JButton;
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
 * Gruppen-View
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class GroupsView extends AbstractView implements GroupsGraphicDesign, GroupsEvent{
    
    /* Controller */
    private GroupsController controller;
    
    /* GUI */
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
        this.controller = controller;
        
        /* Alle Komponente Initialisieren */
        initComponents();

        /* Gruppen Tab zu Frame hinzufuegen */
        this.controller.addViewToFrame(GROUPS_TITLE, this);
    }
    
    
    
    /***************************************************************************
     * Alle Grafikkomponenten zeichnen und anordnen
     **************************************************************************/
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
        message_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"messages32x32.png")));  
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
        save_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"save32x32.png")));
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
        add_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"add32x32.png")));
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
        remove_button.setIcon(new ImageIcon(getClass().getResource(IMAGES_FILEPATH+"remove32x32.png")));
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
     * Methoden werden direkt von Listener der Grafikelementen angesprochen
     **************************************************************************/
    /**
     * Hinzufuegen Button
     * @param ae 
     */
    private void addButtonActionPerformed(ActionEvent ae) {
        controller.addGroup();
    }
    
    
    /**
     * Loeschen Button
     * @param ae 
     */
    private void removeButtonActionPerformed(ActionEvent ae) {
        controller.removeGroup();  
    }

    
    /**
     * Speichern Button
     * @param ae 
     */
    private void saveButtonActionPerformed(ActionEvent ae) {
        controller.saveGroup();  
    }

    
    /**
     * Nachrichten Button
     * @param ae 
     */
    private void messageButtonActionPerformed(ActionEvent ae) {
            controller.sendMessage();   
    }
    
    
    /**
     * Such-Textfeld selektiert
     * @param fe 
     */
    private void searchTextFocusGained(FocusEvent fe) {
        controller.searchGroupFocusGained();
    }
    
    
    /**
     * Such-Textfeld deselektiert
     * @param fe 
     */
    private void searchTextFocusLost(FocusEvent fe) {
        controller.searchGroupFocusLost();
    }
    
    
    /**
     * Such-Textfeld Eintrag geaendert
     * @param fe 
     */
    private void searchTextChange(DocumentEvent de) {
        controller.searchGroup();
    }
    
    
    /**
     * Gruppenliste wurde veraendert
     * @param lse 
     */
    private void groupListValueChanged(ListSelectionEvent lse) {
        /* Nur einmaliger Event erlauben */
        if(lse.getValueIsAdjusting() == false)
            controller.getGroup();
    }
    
    
    /**
     * Gruppennamen-Textfeld selektiert
     * @param fe 
     */
    private void groupnameTextFocusGained(FocusEvent fe) {
        controller.nameGroupFocusGained();
    }
    
    
    /**
     * Gruppennamen-Textfeld deselektiert
     * @param fe 
     */
    private void groupnameTextFocusLost(FocusEvent fe) {
        controller.nameGroupFocusLost();
    }
    
    
    /**
     * Gruppennamen-Textfeld geaendert
     * @param de 
     */
    private void groupnameTextChange(DocumentEvent de) {
        controller.nameGroupChange();  
    }
    
    
    
    /***************************************************************************
     * Controller -> View
     * setter und getter Methoden, die dem Controller das Steuern ermoeglichen
     **************************************************************************/
    /**
     * Index der selektierten Gruppe der Uebersichtsliste
     * @return Index: -1 wenn nichts selektiert ist 
     */
    public int getSelectedGroupIndex() {
        return this.groupoverview_separatorlist.getSelectedIndex();
    }
    
    
    /**
     * Gruppe mit dem angegebenen Index selektieren
     * @param index Gruppen-Index
     */
    public void setSelectedGroupIndex(int index) {
        this.groupoverview_separatorlist.setSelectedIndex(index);
    }
    
    
    /**
     * ID einer Gruppe in der Uebersichtsliste
     * @param index Index der Gruppe in der Liste
     * @return Gruppen ID
     */
    public int getGroupIdOfIndex(int index) {
        ListMember member = this.groupoverview_separatorlist.getListMemberOfIndex(index);
        return member.getID();
    }
    
    
    /**
     * Name einer Gruppe in der Uebersichtsliste
     * @param index Index der Gruppe in der Liste
     * @return Gruppen-Name
     */
    public String getGroupNameOfIndex(int index) {
        ListMember member = this.groupoverview_separatorlist.getListMemberOfIndex(index);
        return member.getText();
    }
    
    
    /**
     * Aktueller Name der ausgewaehlten Gruppe
     * @return Gruppen-Name
     */
    public String getGroupName() {
        return this.detail_static_name_textfield.getText();
    }
    
    
    /**
     * Gruppen-Namen temporaer aendern
     * @param group_name Gruppen-Namen
     */
    public void setGroupName(String group_name) {
        this.detail_static_name_textfield.setText(group_name);
    }
    
    
    /**
     * Gruppen Uebersichtsliste anpassen
     * @param group_id_index Index oder ID der zu aendernden Gruppe
     * @param group_name Gruppen Name
     * @param mode Art der Aenderung
     */
    public void setGroupList(int group_id_index, String group_name, String mode) {
        ListMember member;
        switch(mode) {
            case GROUP_ADD_GROUP:
                member = this.groupoverview_separatorlist.addListMember(group_name, group_id_index);
                setSelectedGroupIndex(this.groupoverview_separatorlist.getListMemberIndex(member)); //TODO unschoen!!!
                break;
            case GROUP_REMOVE_GROUP_WITH_ID:
                this.groupoverview_separatorlist.removeListMember(group_id_index);
                break;
            case GROUP_REMOVE_GROUP_WITH_INDEX:
                this.groupoverview_separatorlist.removeListMemberOfIndex(group_id_index);
                break;
        }
    }
    
    public void setGroupListSilent(boolean state) {
        this.groupoverview_separatorlist.setListenerSilent(state);
    }
    
    
    /**
     * Gruppen Ubersichtliste leeren
     */
    public void setGroupListEmpty() {
        int group_quantity;
        
        group_quantity = getGroupQuantity();
        
        /* Alle Elemente der Liste zuerst loeschen */
        for(int i = 0; i < group_quantity; i++){    
            setGroupList(1, "", GROUP_REMOVE_GROUP_WITH_INDEX);
            //groupoverview_separatorlist.removeListMemberOfIndex(1);
        }
    }
    
    
    /**
     * Kontakt Ubersichtliste leeren
     */
    public void setContactListEmpty() {
        int contact_quantity;
        
        contact_quantity = detail_member_separatorlist.getListMemberSize();      

        /* Alle Elemente der Liste loeschen */
        for(int i = 0; i < contact_quantity; i++)    
            detail_member_separatorlist.removeListMemberOfIndex(1);
    }
    
    
    /**
     * Kontrolle ob eine Gruppe vorhanden ist anhand der Gruppen-ID
     * @param group_id Gruppen-ID
     * @return Gruppe vorhanden: true; Gruppe nicht vorhanden: false
     */
    public boolean getGroupState(int group_id) {
        if(this.groupoverview_separatorlist.containsListMember(group_id) == null)
            return false;
        else
            return true;
    }
    
    
    /**
     * Groesse der Gruppen-Ubersichtsliste 
     */
    public int getGroupListSize() {
        return this.groupoverview_separatorlist.getListSize();
    }
    
    
    /**
     * Anzahl vorhanden Gruppen
     */
    public int getGroupQuantity() {
        return this.groupoverview_separatorlist.getListMemberSize();
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
     * Gruppen-Namen selektieren
     * @param first Erster Buchstaben des Strings 
     * @param last Letzter Buchstaben des Strings
     */
    public void setGroupNameSelection(int first, int last) {
        detail_static_name_textfield.select(first, last);
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
     * @param state true: Nachricht senden moeglich; false: Nachricht senden nicht moeglich
     */
    public void setMessageButtonState(boolean state) {
        message_button.setEnabled(state);
    }
   
    
    /**
     * Loeschen-Button aktiviern oder deaktivieren
     * @param state true: Loeschen moeglich; false: Loeschen nicht moeglich
     */
    public void setRemoveButtonState(boolean state) {
        remove_button.setEnabled(state);
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
        switch(evt.getPropertyName()) {
            case GROUP_LIST_SELECT_EVENT:    
            case GROUP_SEARCH_EVENT:

                if(evt.getNewValue() != null) {
                    setGroupListEmpty();
    
                    /* Bei leerer Gruppen Ubersichtsliste, Details-Ansicht leer lassen */
                    if(((ArrayList<GroupDTO>)evt.getNewValue()).size() != 0) {
                        /* Neu Elemente hinzufuegen */
                        for(GroupDTO group : (ArrayList<GroupDTO>)evt.getNewValue()) {
                            setGroupListSilent(true);
                            setGroupList(group.group_id, group.group_name, GROUP_ADD_GROUP);
                        }
                        setGroupListSilent(false);
                        
                    } else {
                        /* Details-ansicht leer */
                        setMessageButtonState(false);
                        setSaveButtonState(false);
                        setRemoveButtonState(false);
                        setGroupName(GROUP_TAB_DEFAULT_NAME_TEXT);
                        setContactListEmpty();
                    }   
                }
                
                /* Falls kein Eintrag selektiert ist, den ersten selektieren */
                controller.selectGroup();
                break;
                
                
            case GROUP_SELECT_EVENT:

                if(evt.getNewValue() != null) {
                    setContactListEmpty();
                    
                    if(((GroupDTO)evt.getNewValue()).group_name.equals(((GroupDTO)evt.getOldValue()).group_name)) {
                        if(!getGroupNameOfIndex(getSelectedGroupIndex()).equals(getGroupName())) {
                            setGroupName(((GroupDTO)evt.getNewValue()).group_name);
                            setSaveButtonState(false);
                        } else {
                            setSaveButtonState(false);
                        }
                    } else {
                        if(!getGroupNameOfIndex(getSelectedGroupIndex()).equals(getGroupName())) {
                            setSaveButtonState(true);
                            setGroupName(getGroupNameOfIndex(getSelectedGroupIndex()));
                        }
                    }
                    
                    /* Neu Elemente hinzufuegen */
                    //TODO Methode wie setGroupList erstellen 
                    for(ContactDTO groupmember : ((GroupDTO)evt.getNewValue()).group_contacts) {
                        detail_member_separatorlist.addListMember(groupmember.user_lastname+" "+groupmember.user_prename,
                                groupmember.user_id);
                    }
                    
                    /* Falls Gruppenmitglieder vorhanden sind, Senden ermoeglichen */
                    //TODO
                    if(detail_member_separatorlist.getListMemberSize() > 0)
                        setMessageButtonState(true);
                    else
                        setMessageButtonState(false);
                } else {
                    setGroupName(GROUP_TAB_DEFAULT_NAME_TEXT);
                }
                break;
             
                
            case GROUP_INSERT_EVENT:
                if(evt.getNewValue() != null) {
                    setGroupList(GROUP_DEFAULT_ID, "", GROUP_REMOVE_GROUP_WITH_ID);
                }
                
                
            case GROUP_UPDATE_EVENT:
                
                if(evt.getNewValue() != null) {
                    /* Gruppe zu Liste hinzufuegen oder aktualisieren */
                    ListMember member = groupoverview_separatorlist.addListMember(((GroupDTO)evt.getNewValue()).group_name, 
                            ((GroupDTO)evt.getNewValue()).group_id);
                    /* Neuer Eintrag selektieren */
                    groupoverview_separatorlist.setSelectedIndex(groupoverview_separatorlist.getListMemberIndex(member));
                    
                    /* Gespeichert -> Speichern nicht mehr moeglich */
                    setSaveButtonState(false);
                }
                break;
                
                
            case GROUP_DELETE_EVENT:
                
                if(evt.getNewValue() != null) {
                    /* Gruppe aus Liste entfernen */
                    groupoverview_separatorlist.removeListMember(((GroupDTO)evt.getNewValue()).group_id);
                    
                    /*Falls keine Gruppen in der Datenbank sind, loeschen verhindern */
                    if(groupoverview_separatorlist.getListMemberSize() == 0)
                        setRemoveButtonState(false);
                    
                    /* Falls kein Eintrag selektiert ist, den ersten selektieren */
                    controller.selectGroup();
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
