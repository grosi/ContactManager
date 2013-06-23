package contactmanager.main.contacts;

import contactmanager.main.contacts.ContactDTO.*;
import contactmanager.main.dao.DAOException;
import contactmanager.main.groups.GroupDTO;
import java.util.ArrayList;

/**
 * Schnittschtelle des User DAO
 * @author Kevin Gerber
 * @version 0.1
 * @since 28.03.2013
 */
public interface ContactsDAO {
    
    /* Kontaklisten */
    public ArrayList<ContactDTO> selectContactList() throws DAOException;
    public ArrayList<ContactDTO> searchContactList(String search_pattern) throws DAOException;
    
    /* Schnittstelle der einzelnen Kontakte */
    public ContactDTO selectContact(int contact_id) throws DAOException;
    public int insertContact(ContactDTO insert_contact) throws DAOException;
    public boolean updateContact(ContactDTO update_contact) throws DAOException;
    public boolean deleteContact(int contact_id) throws DAOException;
    
    /* Schnittstelle Email */
    public void deleteContactEmail(int email_id) throws DAOException;
    public void updateContactEmail(ContactDTO.ContactEmail email) throws DAOException;
    public void insertContactEmail(int user_id, ContactDTO.ContactEmail email) throws DAOException;
    
    /* Schnittstelle Adresse */
    public void insertContactAddress(int user_id, ContactDTO.ContactAdress adress) throws DAOException;
    public void updateContactAddress(ContactDTO.ContactAdress adress) throws DAOException;
    public void deleteContactAddress(int address_id) throws DAOException;
    
    /* Schnittstelle Telefon */
    public void insertContactPhone(int user_id, ContactDTO.ContactPhone phone) throws DAOException;
    public void updateContactPhone(ContactDTO.ContactPhone phone) throws DAOException;
    public void deleteContactPhone(int phone_id) throws DAOException;

    /* Schnittstelle der Gruppenzugehoerigkeit */
    public ArrayList<GroupDTO> selectGroupsFromContact(int contact_id) throws DAOException;
    public ArrayList<GroupDTO> selectGroupsForContacts() throws DAOException;
    public boolean addContactToGroup(int contact_id, int group_id) throws DAOException;
    public boolean deleteContactFromGroup(int contact_id, int group_id) throws DAOException;
}
