
package contactmanager.main.contacts;

import java.util.ArrayList;

/**
 * Transfer Ojekt fuer Kontakte
 * @author grosi
 * @version 0.1
 * @since 28.03.2013
 */
public class ContactDTO {
    
    /* Allgemeine Benutzerdaten */
    public int user_id;
    public int user_state;
    public String user_prename;
    public String user_lastname;
    
    /* Email-Adressen */
    public ArrayList<ContactEmail> contact_email;
    
    /* Adressen */
    public ArrayList<ContactAdress> contact_adress;
    
    /* Telefon-Nummer */
    public ArrayList<ContactPhone> contact_phone;

    
    /* Email Angaben */
    public class ContactEmail {
        public String email_adress;
        public String email_type; //Email-Art: Privat, Geschaeftlich, etc.
        public int email_id;
        public int email_priority;

        /**
         * Konstruktor
         */
        public ContactEmail(){}
        
        /**
         * Konstruktor zum anlegen neuer Email-Adressen
         * @param email_adress
         * @param email_type
         * @param email_id
         * @param email_priority
         */
        public ContactEmail(String email_adress, String email_type, int email_id, int email_priority) {
            this.email_adress = email_adress;
            this.email_type = email_type;
            this.email_id = email_id;
            this.email_priority = email_priority;  
        }
    }
    
    
    /**
     * Adress Angaben
     */
    public class ContactAdress {
        public String adress_street;
        public String adress_code;
        public String adress_city;
        public String adress_country;
        public String adress_type;
        public int adress_id;
        public int adress_priority;

        /**
         * Konstruktor
         */
        public ContactAdress(){}
        
        /**
         * Konstruktor zum anlegen neuer Adresse
         * @param adress_street
         * @param adress_code
         * @param adress_city
         * @param adress_country
         * @param adress_type
         * @param adress_id
         * @param adress_priority
         */
        public ContactAdress(String adress_street, String adress_code, String adress_city, String adress_country, String adress_type, int adress_id, int adress_priority) {
            this.adress_street = adress_street;
            this.adress_code = adress_code;
            this.adress_city = adress_city;
            this.adress_country = adress_country;
            this.adress_type = adress_type;
            this.adress_id = adress_id;
            this.adress_priority = adress_priority;
        } 
    }
    

    /**
     * Telefon Angaben
     */
    public class ContactPhone {
        public String phone_number;
        public String phone_type;
        public int phone_id;
        public int phone_priority;

        /**
         * Konstruktor
         */
        public ContactPhone(){}
        
        /**
         * Konstruktor zum anlegen neuer Telefon-Nummern
         * @param phone_number
         * @param phone_type
         * @param phone_id
         * @param phone_priority
         */
        public ContactPhone(String phone_number, String phone_type, int phone_id, int phone_priority) {
            this.phone_number = phone_number;
            this.phone_type = phone_type;
            this.phone_id = phone_id;
            this.phone_priority = phone_priority;
        }
    }
}
