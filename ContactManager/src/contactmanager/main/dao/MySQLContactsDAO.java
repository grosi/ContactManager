
package contactmanager.main.dao;

import contactmanager.main.contacts.ContactDTO;
import contactmanager.main.contacts.ContactsDAO;
import contactmanager.main.groups.GroupDTO;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

/**
 * @author Kevin Gerber
 * @version 1.0
 * @since 02.06.2013
 */
public class MySQLContactsDAO implements ContactsDAO {
    
    public MySQLContactsDAO() {}
   
    @Override
    public ArrayList<ContactDTO> selectContactList() throws DAOException {
            ArrayList<ContactDTO> list = new ArrayList<>();
            ResultSet result;

            result = executeQuery("SELECT * FROM user ORDER BY prename ASC, lastname ASC");

            if (result == null)
                    throw new DAOException("No User in the Database.");

            while (nextLine(result)) {
                /* Benutzer anhaengen */
            	ContactDTO line = fillInContact(result);
            	getAllInformations(line);
            	list.add(line);
            }

            closeResult(result);
            
            return list;
    }

    @Override
    public ArrayList<ContactDTO> searchContactList(String search_pattern) throws DAOException {
        ArrayList<ContactDTO> list = new ArrayList<>();
        ResultSet result;

        result = executeQuery("SELECT * FROM user " +
        		"WHERE prename LIKE '%"+search_pattern+"%' OR lastname LIKE '%"+search_pattern+"%' " +
        		"ORDER BY prename ASC, lastname ASC");

        if (result == null)
                throw new DAOException("No User were found with the search pattern.");

        while (nextLine(result)) {
                /* Benutzer anhaengen */
        	ContactDTO line = fillInContact(result);
        	getAllInformations(line);
        	list.add(line);
        }
        
        closeResult(result);

        return list;
    }

    @Override
    public ContactDTO selectContact(int id) throws DAOException {
            ResultSet result;
            ContactDTO line;

            result = executeQuery("SELECT * FROM user WHERE user_id="+Integer.toString(id));
            
            if (!nextLine(result)) {
            	throw new DAOException("User does not exists!");
            }
        	
            line = fillInContact(result);
        	getAllInformations(line);
        	closeResult(result);
            
        	return line;
    }

    @Override
    public boolean insertContact(ContactDTO insertContact) throws DAOException {
    	int id;
    	
    	if (insertContact.user_id != 0)
    		throw new DAOException("User id is allready set");
    	
    	id = executeInsert("INSERT INTO user(prename, lastname, state)" +
                            "VALUE('"+insertContact.user_prename+"', '"
                            +insertContact.user_lastname+"'," +
                                            ""+insertContact.user_state+")");
    	
    	insertContact.user_id = id;
    	
    	return true;
    }

    @Override
    public boolean updateContact(ContactDTO updateContact) throws DAOException {
           	
    	if (updateContact.user_id <= 0)
            throw new DAOException("User id is missed");
    	
            executeUpdate("UPDATE user SET prename='"+updateContact.user_prename+"'," +
                            "lastname='"+updateContact.user_lastname+"'," +
                            "state="+updateContact.user_state+" " +
                            		"WHERE user_id="+Integer.toString(updateContact.user_id));
            
            return true;
    }

    @Override
    public boolean deleteContact(int id) throws DAOException {
    	if (id <= 0)
            throw new DAOException("User id is missed");
    	
    	/* Alle Anderene Felder auch loeschen */
            executeUpdate("DELETE user.*, user_address.*, user_phone.*, user_email.*, user2group.* " +
            		"FROM user " +
            		"LEFT JOIN user_address ON user.user_id=user_address.user_id " +
            		"LEFT JOIN user_phone ON user.user_id=user_phone.user_id " +
            		"LEFT JOIN user_email ON user.user_id=user_email.user_id " +
            		"LEFT JOIN user2group ON user.user_id=user2group.user_id " +
            		"WHERE user.user_id="+Integer.toString(id));
            
            return true;
    }
    
    //--------------------
	public void insertContactAddress(int user_id, ContactDTO.ContactAdress adress) throws DAOException {
		int key;
		
		key = executeInsert("INSERT INTO user_address(type, address, district, postal_code, city)" +
				"VALUES('"+adress.adress_type+"', '"+adress.adress_street+"', " +
						"'"+adress.adress_country+"', '"+adress.adress_code+"', " +
								"'"+adress.adress_city+"')");
		
		adress.adress_id = key;
	}
	public void updateContactAddress(ContactDTO.ContactAdress adress) throws DAOException {
		executeUpdate("UPDATE user_address SET type='"+adress.adress_type+"',  " +
				"address='"+adress.adress_street+"',  district='"+adress.adress_country+"',  " +
						"postal_code='"+adress.adress_code+"',  city='"+adress.adress_city+"' " +
								"WHERE adress_id="+Integer.toString(adress.adress_id));
	}
	public void deleteContactAddress(int address_id) throws DAOException {
		executeUpdate("DELETE FROM user_address " +
				"WHERE adress_id="+Integer.toString(address_id));
	}
	
	public void insertContactPhone(int user_id, ContactDTO.ContactPhone phone) throws DAOException {
		int key;
		
		key = executeInsert("INSERT INTO user_phone(type, number)" +
				"VALUES('"+phone.phone_type+"', '"+phone.phone_number+"')");
		
		phone.phone_id = key;
	}
	public void updateContactPhone(ContactDTO.ContactPhone phone) throws DAOException {
		executeUpdate("UPDATE user_phone SET type='"+phone.phone_type+"', " +
				"number='"+phone.phone_number+"' " +
						"WHERE phone_id="+Integer.toString(phone.phone_id));
	}
	public void deleteContactPhone(int phone_id) throws DAOException {
		executeUpdate("DELETE FROM user_phone " +
				"WHERE phone_id="+Integer.toString(phone_id));
	}
	
	public void insertContactEmail(int user_id, ContactDTO.ContactEmail email) throws DAOException {
		int key;
		
		key = executeInsert("INSERT INTO user_email(type, email, priority)" +
				"VALUES('"+email.email_type+"', '"+email.email_adress+"', " +
						""+Integer.toString(email.email_priority)+")");
		
		email.email_id = key;
	}
	public void updateContactEmail(ContactDTO.ContactEmail email) throws DAOException {
		executeUpdate("UPDATE user_email SET type='"+email.email_type+"', " +
				"email='"+email.email_adress+"'," +
						" priority="+Integer.toString(email.email_priority)+" " +
								"WHERE email_id="+Integer.toString(email.email_id));
	}
	public void deleteContactEmail(int email_id) throws DAOException {
		executeUpdate("DELETE FROM user_email " +
				"WHERE email_id="+Integer.toString(email_id));
	}
    //--------------------
    

    @Override
    public boolean addContactToGroup(int contact_id, int group_id) throws DAOException {
        deleteContactFromGroup(contact_id, group_id);
        
         executeUpdate("INSERT INTO user2group(user_id, group_id)" +
        			"VALUES("+Integer.toString(contact_id)+", "+Integer.toString(group_id)+")");

         return true;
    }

    @Override
    public boolean deleteContactFromGroup(int contact_id, int group_id) throws DAOException {
        executeUpdate("DELETE FROM user2group " +
        		"WHERE user_id="+Integer.toString(contact_id)+" " +
        				"AND group_id="+Integer.toString(group_id));
        
        return true;
    }

    @Override
    public ArrayList<GroupDTO> selectGroupsFromContact(int contact_id) throws DAOException {
    	ResultSet result = null;
    	ArrayList<GroupDTO> groups = new ArrayList<GroupDTO>();
        
        result = executeQuery("SELECT g.* FROM `group` AS g, `user2group` AS c " +
        		"WHERE c.user_id = "+Integer.toString(contact_id)+" AND g.group_id = c.group_id " +
        				"ORDER BY g.name ASC");
      
        while(nextLine(result)) {
        	GroupDTO group = new GroupDTO();
        	
        	try {
				group.group_id = result.getInt("group_id");
				group.group_name = result.getString("name");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Colum does not Exists!");
			}
        	
        	groups.add(group);
        }
        
        closeResult(result);
        
        return groups;
    }
   
    
    
    
    /***************************************************************************
     * Private Methoden fuer Datenbankzugriff
     **************************************************************************/
    private ResultSet executeQuery(String query) throws DAOException {
		Statement statement;
		ResultSet result;
		
		try {
			statement = MySQLDAOFactory.getInstance().getConnection().createStatement();
			result = statement.executeQuery(query);
			//statement.close();
		}
		catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Error in your SQL Query");
		}
		
		return result;
    }
    
    private void closeResult(ResultSet result) throws DAOException {
    	try {
			result.close();
		} catch (SQLException e) {
			e.printStackTrace();
			throw new DAOException("Result closeing error");
		}
    }
	
    private void executeUpdate(String query) throws DAOException {
            Statement statement;

            try {
                    statement = MySQLDAOFactory.getInstance().getConnection().createStatement();
                    statement.executeUpdate(query);
                    statement.close();
            }
            catch (SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Cannot change Database Values");
            }
    }
    
    private int executeInsert(String query) throws DAOException {
    	PreparedStatement statement;
        ResultSet generatedKeys;
        int key = 0;

        try {
                statement = MySQLDAOFactory.getInstance().getConnection().prepareStatement(
                		query, PreparedStatement.RETURN_GENERATED_KEYS);
                statement.executeUpdate();
                
                /* Key ermitteln */
                generatedKeys = statement.getGeneratedKeys();
                if(generatedKeys.next()){
                    key = generatedKeys.getInt(1);    
                }
         
                statement.close();
        }
        catch (SQLException e) {
                e.printStackTrace();
                throw new DAOException("Cannot insert datas into the database");
        }

        return key;
    }
	
    private boolean nextLine(ResultSet result) throws DAOException {
            try {
                    return result.next();
            }
            catch (SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Not a ResultSet Object");
            }
    }
	
    private ContactDTO fillInContact(ResultSet result) throws DAOException {
            ContactDTO contact = new ContactDTO();

            if (result == null)
                    throw new DAOException("User does not exists!");

            try {
                    contact.user_id = result.getInt("user_id");
                    contact.user_prename = result.getString("prename");
                    contact.user_lastname = result.getString("lastname");
                    contact.user_state = result.getInt("state");
            }
            catch (SQLException e) {
                    e.printStackTrace();
                    throw new DAOException("Colum does not Exists!");
            }

            return contact;
    }
    
    private void getAllInformations(ContactDTO contact) throws DAOException {
    	ResultSet result;
    	String id;
    	
    	id = Integer.toString(contact.user_id);
    	
    	/* Adressen */
        result = executeQuery("SELECT * FROM user_address WHERE user_id="+id+" ORDER BY type ASC");
        while (nextLine(result)) {
            /* Benutzer anhaengen */
        	ContactDTO.ContactAdress line = contact.new ContactAdress();
        	try {
				line.adress_id = result.getInt("address_id");
	        	line.adress_type = result.getString("type");
	        	line.adress_street = result.getString("address");
	        	line.adress_country = result.getString("district");
	        	line.adress_code = Integer.toString(result.getInt("postal_code"));
	        	line.adress_city = result.getString("city");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Colum does not Exists!");
			}
        	contact.contact_adress.add(line);
        }
        closeResult(result);
        
    	/* Telefonnummern */
        result = executeQuery("SELECT * FROM user_phone WHERE user_id="+id+" ORDER BY type ASC");
        while (nextLine(result)) {
            /* Benutzer anhaengen */
        	ContactDTO.ContactPhone line = contact.new ContactPhone();
        	try {
				line.phone_id = result.getInt("phone_id");
	        	line.phone_type = result.getString("type");
	        	line.phone_number = result.getString("number");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Colum does not Exists!");
			}
        	contact.contact_phone.add(line);
        }
        closeResult(result);
        
    	/* Email Adressen */
        result = executeQuery("SELECT * FROM user_email WHERE user_id="+id+" ORDER BY type ASC");
        while (nextLine(result)) {
            /* Benutzer anhaengen */
        	ContactDTO.ContactEmail line = contact.new ContactEmail();
        	try {
				line.email_id = result.getInt("email_id");
	        	line.email_type = result.getString("type");
	        	line.email_adress = result.getString("email");
	        	line.email_priority = result.getInt("priority");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new DAOException("Colum does not Exists!");
			}
        	contact.contact_email.add(line);
        }
        closeResult(result);
    }

}
