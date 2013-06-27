package contactmanager.main.contacts;

import contactmanager.main.AbstractController;
import contactmanager.main.AbstractView;
import contactmanager.main.frame.MainController;
import contactmanager.main.SubController;


/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class ContactsController extends AbstractController implements ContactsEvent, SubController {
    
    private MainController main_controller;

    /* Modelle */
    private ContactsModel contacts_model;
    
    /* Views */
    private ContactsView contacts_view;
    
    public ContactsController(MainController mainController) {
        super();
        
        this.main_controller = mainController;
        
        /* Modelle eintragen (diejenigen die Views aktualisieren sollen*/
        contacts_model = new ContactsModel(this);
        addModel(contacts_model);
        
        /* Views eintragen -> werden durch Modelle aktualisiert */
        contacts_view = new ContactsView(this);
        addView(contacts_view);
    }
    
    
 
    
    /***************************************************************************
     * MainController-> SubController Methoden
     **************************************************************************/
    /**
     * Aktuelle Datensaetze von der Datenbank holen
     */
    @Override
    public void updateData() {
        contacts_model.getContactList();
        contacts_model.allGroups();
        System.out.println("UPDATE CONTACTS");
    }
    
    /***************************************************************************
     * Model/View -> MainController Methoden
     **************************************************************************/
    
    /**
     * View zu Frame hinzufuegen
     * @param title
     * @param view 
     * @todo evtl in AbstractController einbauen!
     */
    public void addViewToFrame(String title, AbstractView view) {
        this.main_controller.addTabToMainFrame(title, view);
    }
    
    /**
     * Spezifische DAO erstellen
     * @return GroupsDAO
     * @todo evtl in AbstractController einbauen
     */
    @Override
    public Object getDAO() {
        return this.main_controller.getDAOFactory().getContactsDAO();
    }
    
    
    /***************************************************************************
     * View -> Controller Methoden
     **************************************************************************/
    
    public void getContact() {
        int index;
        int list_size;
        ContactDTO contact;
        int contact_id;
        String[] contact_listname;
        
        index = contacts_view.getSelectedContactIndex(); //Selektierte Gruppe in der Ubersichtsliste
        list_size = contacts_view.getContactListSize(); //Groesse der Uerbsichtsliste
        
        /* Kontrolle ob die Selektion noch moeglich ist */
        if(index > 0 && index != list_size) {
            contact_id = contacts_view.getContactIdOfIndex(index);
            contact_listname = contacts_view.getContactNameOfIndex(index).split(" ");
            
            /* Nur gespeicherte Gruppen abfragen */
            if(contact_id != CONTACT_DEFAULT_ID) {
                contact = getContactDTO();
                contact.user_id = contact_id;
                contact.user_lastname = contact_listname[0];
                contact.user_prename = contact_listname[1];
                
                /* Ungespeicherte Gruppen loeschen */
                if(contacts_view.getContactState(CONTACT_DEFAULT_ID) == true)
                    contacts_view.setContactList(CONTACT_DEFAULT_ID, contact_listname[0], ContactsView.CONTACT_REMOVE_GROUP_WITH_ID);
                
                contacts_view.setMouseWaitCursor(true);
                contacts_model.getContact(contact);
                contacts_view.setMouseWaitCursor(false);
            } else {
                contacts_view.setMessageButtonState(false);
            }
            main_controller.setStatusBar("Kontakt '"+contact_listname[0]+" "+contact_listname[1]+"' angewählt" );
        }
    }
    
    
    /**
     * Erste Gruppe in Ubersichtsliste selektieren
     */
    public void selectContact() {
        int group_quantity;
        int contact_id;
        String[] contact_listname;
        ContactDTO contact;
 
        group_quantity = contacts_view.getContactQuantity();

        /* Falls die Liste nicht leer ist, erster Eintrag selektieren */
        if(group_quantity > 0) {
            contacts_view.setSelectedContactIndex(1);
            contact_id = contacts_view.getContactIdOfIndex(1);
            contact_listname = contacts_view.getContactNameOfIndex(1).split(" ");
            contact = getContactDTO();
            contact.user_id = contact_id;
            contact.user_lastname = contact_listname[0];
            contact.user_prename = contact_listname[1];
            contacts_model.getContact(contact);
        }
    }
    
    
    public void addContact() {
        /* Platzhalter in Gruppen-Ubersichtliste */
        contacts_view.setContactListSilent(true);
        contacts_view.setContactList(CONTACT_DEFAULT_ID, ContactsView.CONTACT_TAB_DEFAULT_NAME_TEXT, ContactsView.CONTACT_ADD_CONTACT);
        contacts_view.setContactListSilent(false);
        
        /* Detailsansicht zuruecksetzen */
        contacts_view.setContactLastname(ContactsView.CONTACT_TAB_DEFAULT_NAME_TEXT);
        contacts_view.setContactPrename(ContactsView.CONTACT_TAB_DEFAULT_PRENAME_TEXT);
         /*Daten auf Default*/
        contacts_view.setEmailEmpty();
        contacts_view.setPhoneEmpty();
        contacts_view.setAddressEmpty();
        contacts_view.setGroupEmpty();
   
        contacts_view.setRemoveButtonState(true);
        contacts_view.setAddEmailButtonState(true);
        contacts_view.setAddAddressButtonState(true);
        contacts_view.setAddPhoneButtonState(true);
        contacts_view.setContactPrenameTextState(true);
        contacts_view.setContactLastnameTextState(true);
        contacts_view.setSaveButtonState(false);
    }
    
       
    /**
     * Kontakt löschen
     */
    public void removeContact() {
        ContactDTO contact;
        int index;
        int contact_id;
        String[] contact_listname;
        
        /* Selektierter Kontakt */
        index = contacts_view.getSelectedContactIndex();
        
        /* Kein Eintrag in Uebersicht selektiert */
        if(index == -1)
            return;
        
        contact_id = contacts_view.getContactIdOfIndex(index);
        contact_listname = contacts_view.getContactNameOfIndex(index).split(" ");
        
        
        /* Ungespeicherter Kontakt */
        if(contact_id == CONTACT_DEFAULT_ID) {
            contacts_view.setContactList(contact_id, contact_listname[0], ContactsView.CONTACT_REMOVE_GROUP_WITH_ID);
            selectContact();
            
        /* Gespeicherter Kontakt */    
        } else {
            contact = getContactDTO();
            contact.user_id = contact_id;
            contacts_view.setMouseWaitCursor(true);
            contacts_model.removeContact(contact);
            contacts_view.setMouseWaitCursor(false);
            
            main_controller.setStatusBar("Kontakt '"+contact_listname[0]+" "+contact_listname[1]+"' gelöscht" );
        }
        
        
        
    
    }
    
    /**
     * Kontakt speichern
     */
    public void saveContact()  {
        ContactDTO contact;
        int contact_id;
        String contact_name[];
        int index;
        String[] email_addresses;
        String[] email_types;
        Integer[] email_ids;
        String[] phone_numbers;
        String[] phone_types;
        Integer[] phone_ids;
        String[] address_streets;
        String[] address_codes;
        String[] address_citys;
        String[] address_countrys;
        String[] address_types;
        Integer[] address_ids;
        String[] groups;
                
        /* Selektierte Gruppe */
        index = contacts_view.getSelectedContactIndex();
        
        /* Kein Eintrag in Uebersicht selektiert */
        if(index == -1)
            return;
        
        /* Daten zusammentragen */
        contact_id = contacts_view.getContactIdOfIndex(index);
        contact_name = contacts_view.getContactNameOfIndex(index).split(" ");
        contact = getContactDTO();
        contact.user_id = contact_id;
        contact.user_lastname = contact_name[0];
        contact.user_prename = contact_name[1];
        /* Email */
        email_addresses = contacts_view.getEmailAddresses();
        email_types = contacts_view.getEmailTypes();
        email_ids = contacts_view.getEmailIDs();
        if(email_addresses != null) {
            for(int i = 0; i < email_addresses.length; i++) {
                contact.contact_email.add(contact.new ContactEmail(email_addresses[i], email_types[i], email_ids[i], i+1));
            }
        }
        /* Telefon */
        phone_numbers = contacts_view.getPhoneNumbers();
        phone_types = contacts_view.getPhoneTypes();
        phone_ids = contacts_view.getPhoneIDs();
        if(phone_numbers != null) {
            for(int i = 0; i < phone_numbers.length; i++) {
                contact.contact_phone.add(contact.new ContactPhone(phone_numbers[i], phone_types[i], phone_ids[i], i+1));
            }
        }
        /* Adresse */
        address_streets = contacts_view.getAddressStreets();
        address_codes = contacts_view.getAddressCodes();
        address_citys = contacts_view.getAddressCitys();
        address_countrys = contacts_view.getAddressCountrys();
        address_types = contacts_view.getAddressTypes();
        address_ids = contacts_view.getAddressIDs();
        if(address_streets != null) {
            for(int i = 0; i < address_streets.length; i++) {
                contact.contact_adress.add(contact.new ContactAdress(address_streets[i], address_codes[i], address_citys[i], address_countrys[i], address_types[i], address_ids[i], i+1));
            }       
        }
       
        
        /* Gruppe existiert noch nicht in Datenbank */
        contacts_view.setMouseWaitCursor(true);
        if(contact_id == CONTACT_DEFAULT_ID) 
            contacts_model.addContact(contact);
        /* Gespeicherte Gruppe */    
        else 
            contacts_model.saveContact(contact);
        contacts_view.setMouseWaitCursor(false);
        
        contacts_model.deleteMail(contacts_view.remove_emails);
        contacts_view.remove_emails.removeAll(contacts_view.remove_emails);        
        contacts_model.deleteAddress(contacts_view.remove_addresses);
        contacts_view.remove_addresses.removeAll(contacts_view.remove_addresses);
        contacts_model.deletePhone(contacts_view.remove_phones);
        contacts_view.remove_phones.removeAll(contacts_view.remove_phones);
        String[] group_name;
       
        /*Gruppe*/
        group_name=contacts_view.getGroups();
        contacts_model.deletetGroups(group_name,contact,contacts_view.contact_groups);
        contacts_model.addedGroups(group_name,contact,contacts_view.contact_groups, contacts_view.all_groups);
        
        main_controller.setStatusBar("Kontakt '"+contact_name[0]+" "+contact_name[1]+"' gespeichert" );
        
    }
    
    
    
    
        /**
     * Suche angewaehlt
     */
    public void searchContactFocusGained() {
        String text;
        
        text = contacts_view.getSearchText();
        
        /* Falls noch keine Anpassungen vorherrschen, alles markieren */
        if(text.equals(ContactsView.CONTACT_TAB_DEFAULT_SEARCH_TEXT) == true)
            contacts_view.setSearchSelection(0, text.length());
    }
    
    
    /**
     * Suche abgewaehlt
     */
    public void searchContactFocusLost() {
        
        contacts_view.setSearchSelection(0, 0); //Nichts Selektieren
        
        /* Wenn die Eingabemaske leer ist, Standardtext einblenden */
        if(contacts_view.getSearchText().equals(""))
            contacts_view.setSearchText(ContactsView.CONTACT_TAB_DEFAULT_SEARCH_TEXT);
    }
    
    /**
     * Nach Gruppen suchen
     */
    public void searchContact() {
        String text;
        
        text = contacts_view.getSearchText(); //Suchmuster
        
        /* Nur suchen, wenn nicht der Standardtext steht */
        if(text.equals(ContactsView.CONTACT_TAB_DEFAULT_SEARCH_TEXT) == false) {
            contacts_view.setMouseWaitCursor(true);
            contacts_model.searchContact(text);
            contacts_view.setMouseWaitCursor(false);
        }
    }
    
    
    public void sendMessage(String text) {
        
        if(getEmailClientState() == true) {
        // Kontrolle ob die Selektion noch moeglich ist 
             sendEmail(text);  
       }    
     
    }
    
        /**
     * Email-Moeglichkeit kontrollieren
     */
    public boolean getEmailClientState() {
        return main_controller.getEmailClientStatus();
    }
    
    /**
     * Nachricht an entsprechende Empfaenger schreiben
     * @param email E-Mail Adressen
     */
    public void sendEmail(String email) {
        main_controller.sendEmail(email);
    }
    
    
    /**
     * Kontakt-Name
     */
    public void nameContactFocusGained() {
        String contact_name;
        
        contact_name = contacts_view.getContactName();
        
        if(contact_name.equals(ContactsView.CONTACT_TAB_DEFAULT_NAME_TEXT) == true) 
            contacts_view.setContactNameSelection(0, contact_name.length());
    }
    
    public void nameContactFocusLost() {
        contacts_view.setContactNameSelection(0,0);
    }
    
    public void nameContactChange() {
        int index;
        int list_size;
        int contact_id;
        String[] contact_name_old;
        String contact_name_new;
    
        /* Informationen holen */
        index = contacts_view.getSelectedContactIndex();
        list_size = contacts_view.getContactListSize();
       
        /* Gruppenliste nur aendern wenn die Pausabilitaeten erfuellt sind */
        if(index > 0 && index != list_size) {
                contact_name_new = contacts_view.getContactName();
                contact_name_old = contacts_view.getContactNameOfIndex(index).split(" ");
                contact_id = contacts_view.getContactIdOfIndex(index);
                
                if(contact_name_new.equals("") == false && contact_name_new.equals(contact_name_old[0]) == false) {//&& 
                    contacts_view.setContactList(contact_id, contact_name_new+" "+contact_name_old[1], ContactsView.CONTACT_ADD_CONTACT);
                    contacts_view.setSaveButtonState(true);
                }
        }   
    }
    
    
    /**
     * Kontakt-Vorname
     */
    public void prenameContactFocusGained() {
        String contact_prename;
        
        contact_prename = contacts_view.getContactPrename();
        
        if(contact_prename.equals(ContactsView.CONTACT_TAB_DEFAULT_PRENAME_TEXT) == true) 
            contacts_view.setContactPrenameSelection(0, contact_prename.length());
    }

    public void prenameContactFocusLost() {
        contacts_view.setContactPrenameSelection(0,0);
    }
        
    public void prenameContactChange() {
        int index;
        int list_size;
        int contact_id;
        String[] contact_name_old;
        String contact_name_new;
        
        /* Informationen holen */
        index = contacts_view.getSelectedContactIndex();
        list_size = contacts_view.getContactListSize();
       
        /* Gruppenliste nur aendern wenn die Pausabilitaeten erfuellt sind */
        if(index > 0 && index != list_size) {
                contact_name_new = contacts_view.getContactPrename();
                contact_name_old = contacts_view.getContactNameOfIndex(index).split(" ");
                contact_id = contacts_view.getContactIdOfIndex(index);
                
                if(contact_name_new.equals("") == false && contact_name_new.equals(contact_name_old[1]) == false) {//&& 
                    contacts_view.setContactList(contact_id, contact_name_old[0]+" "+contact_name_new, ContactsView.CONTACT_ADD_CONTACT);
                    contacts_view.setSaveButtonState(true);
                }
        }   
    }
    
    /**
     * Email-Adresse angewaehlt
     * @param obj Referenz auf die Email-Adresse
     */
    public void emailContactFocusGained(Object obj) {
        String contact_email;
        
        /* Alles Makieren wenn noch keine EMail-Adresse eingegeben wurde */
        contact_email = contacts_view.getContactEmail(obj);
        if(contact_email.equals(ContactsView.CONTACT_TAB_DEFAULT_EMAIL_TEXT) == true) 
            contacts_view.setContactEmailSelection(0, contact_email.length(),obj);
    }
    
    /**
     * Email-Adresse abgewaehlt
     * @param obj Referenz auf die Email-Adresse
     */
    public void emailContactFocusLost(Object obj) {
        contacts_view.setContactEmailSelection(0,0,obj);
    }
    
    /**
     * Email-Adresse geaendert
     * @param obj Referenz auf die Email-Adresse
     */
    public void emailContactChange(Object obj) {
        contacts_view.setSaveButtonState(true);
    }
    
    /**
     * Telefon angewaehlt
     * @param obj Referenz auf die Telefon-Nummer
     */
    public void phoneContactFocusGained(Object obj) {
        String contact_phone;
        
        /* Alles Makieren wenn noch keine EMail-Adresse eingegeben wurde */
        contact_phone = contacts_view.getContactPhone(obj);
        if(contact_phone.equals(ContactsView.CONTACT_TAB_DEFAULT_PHONE_TEXT) == true) 
            contacts_view.setContactPhoneSelection(0, contact_phone.length(),obj);
    }
    
    /**
     * Telefon abgewaehlt
     * @param obj Referenz auf die Telefon-Nummer
     */
    public void phoneContactFocusLost(Object obj) {
        contacts_view.setContactPhoneSelection(0,0,obj);
    }
    
    /**
     * Telefon geaendert
     * @param obj Referenz auf die Telefon-Nummer
     */
    public void phoneContactChange(Object obj) {
        contacts_view.setSaveButtonState(true);
    }
    
    /**
     * Strasse angewaehlt
     * @param obj Referenz auf die Strasse
     */
    public void addressStreetContactFocusGained(Object obj) {
        String contact_street;
        
        /* Alles Makieren wenn noch keine EMail-Adresse eingegeben wurde */
        contact_street = contacts_view.getContactAddressStreet(obj);
        if(contact_street.equals(ContactsView.CONTACT_TAB_DEFAULT_STREET_TEXT) == true) 
            contacts_view.setContactAddressStreetSelection(0, contact_street.length(),obj);
    }
    
    /**
     * Strasse abgewaehlt
     * @param obj Referenz auf die Strasse
     */
    public void addressStreetContactFocusLost(Object obj) {
        contacts_view.setContactAddressStreetSelection(0,0,obj);
    }
    
    /**
     * PLZ angewaehlt
     * @param obj Referenz auf die PLZ
     */
    public void addressCodeContactFocusGained(Object obj) {
        String contact_code;
        
        /* Alles Makieren wenn noch keine EMail-Adresse eingegeben wurde */
        contact_code = contacts_view.getContactAddressCode(obj);
        if(contact_code.equals(ContactsView.CONTACT_TAB_DEFAULT_CODE_TEXT) == true) 
            contacts_view.setContactAddressCodeSelection(0, contact_code.length(),obj);
    }
    
    /**
     * PLZ abgewaehlt
     * @param obj Referenz auf die PLZ
     */
    public void addressCodeContactFocusLost(Object obj) {
        contacts_view.setContactAddressCodeSelection(0,0,obj);
    }
    
    /**
     * Stadt angewaehlt
     * @param obj Referenz auf die Stadt
     */
    public void addressCityContactFocusGained(Object obj) {
        String contact_city;
        
        /* Alles Makieren wenn noch keine EMail-Adresse eingegeben wurde */
        contact_city = contacts_view.getContactAddressCity(obj);
        if(contact_city.equals(ContactsView.CONTACT_TAB_DEFAULT_CITY_TEXT) == true) 
            contacts_view.setContactAddressCitySelection(0, contact_city.length(),obj);
    }
    
    /**
     * Stadt abgewaehlt
     * @param obj Referenz auf die Stadt
     */
    public void addressCityContactFocusLost(Object obj) {
        contacts_view.setContactAddressCitySelection(0,0,obj);
    }
    
    /**
     * Land angewaehlt
     * @param obj Referenz auf das Land
     */
    public void addressCountryContactFocusGained(Object obj) {
        String contact_country;
        
        /* Alles Makieren wenn noch keine EMail-Adresse eingegeben wurde */
        contact_country = contacts_view.getContactAddressCountry(obj);
        if(contact_country.equals(ContactsView.CONTACT_TAB_DEFAULT_COUNTRY_TEXT) == true) 
            contacts_view.setContactAddressCountrySelection(0, contact_country.length(),obj);
    }
    
    /**
     * Land abgewaehlt
     * @param obj Referenz auf das Land
     */
    public void addressCountryContactFocusLost(Object obj) {
        contacts_view.setContactAddressCountrySelection(0,0,obj);
    }
    
    /**
     * Adresse geaendert
     * @param obj Referenz auf die Addresse
     */
    public void addressContactChange(Object obj) {
        contacts_view.setSaveButtonState(true);
    }
    
    
    
    /**
     * Kontakt-Vorname
     */
    public void getAllGroups() {
        contacts_model.allGroups();
    }
    
        void getContactGroups(ContactDTO contactDTO) {
        contacts_model.getContactGroups(contactDTO);
    }
    
    /***************************************************************************
     * Controller Methoden
     **************************************************************************/
    /**
     * Erzeugt ein Transfer Objekt
     * @return Transfer Objekt
     */
    private ContactDTO getContactDTO() {
        return new ContactDTO();
    }


}
