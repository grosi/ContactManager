/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactmanager.main.checklist;

import javax.swing.JComponent;
import javax.swing.UIManager;

/**
 * Swing-Komponente, die eine Check-Liste zur Verfuegung stellt
 * @author grosi
 * @version 0.1
 * @since 25.04.2013
 */
public class JCheckList extends JComponent{
    
    protected CheckListModel model;
    
    public JCheckList() {
        
        this.model = new DefaultCheckListModel();
        
        this.updateUI();
        
        
        
    }
    
    /**
     * Gibt den Text eines Listenelements zurueck
     * @param index
     * @return
     */
    public int getText(int index) {
        return 0;
    }
    
    
    /**
     * Setzt desn Text eines Lsistenelements
     * @param text
     * @param index
     */
    public void setText(String text, int index) {
        
    }
    
    /**
     * Gibt den Status eines Listenelements zurueck
     * @param index
     * @return
     */
    public int getState(int index) {
        return 0;
    }
    
    /**
     * Setzte den Status eines Listenelements
     * @param state
     * @param index
     */
    public void setState(boolean state, int index) {
        
    }
    
    
    /**
     * Gibt den Index eines Listenelements zurueck
     * @param text
     * @return
     */
    public int getListMemberIndex(String text) {
        return 0;
    }
    
    
    /**
     * Fuegt ein neues Listenelement hinzu
     * @param text
     * @param state
     */
    public void addListMember(String text, boolean state) {
        
    }
    
    
    /**
     * Löescht ein Listenelement
     * @param index
     */
    public void removeListMember(int index) {
        
    }
    
    /**
     * Loescht ein Listenelement
     * @param index
     */
    public void removeListMember(String text) {
        
    }
    
    
    /**
     * Gibt die maximale Anzahl an Listenelemente zurueck
     * @return
     */
    public int getMaximumListMember() {
        return 0;
    }
    
    
    /**
     * Setzt die maximale Anzahl an Listenelementen
     * @param maxListMember
     */
    public void setMaximumListMember(int maxListMember) {
        
    }
    
    
    /**
     * Gibt die aktuelle Anzahl an Listenelemente zurueck
     * @return
     */
    public int getCurrentListMember() {
        return 0;
    }
    
    /**
    * The UI class ID string.
    */
    private static final String uiClassID = "CheckListUI";

   /**
    * Sets the new UI delegate.
    * 
    * @param ui New UI delegate.
    */
    public void setUI(CheckListUI ui) {
        super.setUI(ui);
    }

   /**
    * Resets the UI property to a value from the current look and feel.
    * 
    * @see JComponent#updateUI
    */
    @Override
    public void updateUI() {
        if (UIManager.get(getUIClassID()) != null) {
                setUI((CheckListUI) UIManager.getUI(this));
        } else {
                setUI(new BasicCheckListUI());
        }
    }

   /**
    * Returns the UI object which implements the L&F for this component.
    * 
    * @return UI object which implements the L&F for this component.
    * @see #setUI
    */
    public CheckListUI getUI() {
        return (CheckListUI) ui;
    }

   /**
    * Returns the name of the UI class that implements the L&F for this
    * component.
    * 
    * @return The name of the UI class that implements the L&F for this
    *         component.
    * @see JComponent#getUIClassID
    * @see UIDefaults#getUI
    */
    @Override
    public String getUIClassID() {
        return uiClassID;
    }
    
}
