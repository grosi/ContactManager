package contactmanager.main.contacts;

import contactmanager.main.AbstractView;
import contactmanager.main.graphic.GraphicDesign;
import contactmanager.main.graphic.JSeparatorList;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JButton;
import javax.swing.JScrollPane;
import javax.swing.JTextField;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public final class ContactsView extends AbstractView implements GraphicDesign, ContactsInterface {
    
    private ContactsController controller;
    
    private JTextField nameTextField = new JTextField("Test");
    private JTextField nameCloneTextField = new JTextField("     ");
    private JButton changeButton = new JButton("Test");
    private JScrollPane scrollpane = new JScrollPane();
    private JSeparatorList checklist = new JSeparatorList();// = new JCheckList();
    
   
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton3;
    private javax.swing.JButton jButton4;
    private javax.swing.JScrollPane contactspane;
    private javax.swing.JScrollPane detailspane;
    private javax.swing.JTextField searchtext;


    
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
       
        contactspane = new javax.swing.JScrollPane();
        detailspane = new javax.swing.JScrollPane();
        searchtext = new javax.swing.JTextField();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jButton3 = new javax.swing.JButton();
        jButton4 = new javax.swing.JButton();
        
        checklist = new JSeparatorList();

        
        contactspane.setViewportView(checklist);
        contactspane.setHorizontalScrollBarPolicy(javax.swing.ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
        contactspane.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
 
        

        searchtext.setText("jTextField1");

        jButton1.setText("Hinzufügen");
        jButton1.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                checklist.addListMember("Test", 99);
                checklist.addListMember("Simon", 98);
                checklist.addListMember("Kevin", 1);
                checklist.addListMember("Kevin33", 1);
                System.out.println("ADD");
            }
        });

        jButton2.setText("Löschen");
        jButton2.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                checklist.removeListMember(99);
            }
        });
        

        jButton3.setText("jButton3");

        jButton4.setText("jButton4");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(searchtext, javax.swing.GroupLayout.DEFAULT_SIZE, 310, Short.MAX_VALUE)
                    .addComponent(contactspane))
                .addGap(48, 48, 48)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(detailspane)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(0, 112, Short.MAX_VALUE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton2, javax.swing.GroupLayout.PREFERRED_SIZE, 32, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton3, javax.swing.GroupLayout.PREFERRED_SIZE, 40, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jButton4, javax.swing.GroupLayout.PREFERRED_SIZE, 31, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(26, 26, 26)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(searchtext, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton1)
                    .addComponent(jButton2)
                    .addComponent(jButton3)
                    .addComponent(jButton4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(contactspane, javax.swing.GroupLayout.DEFAULT_SIZE, 378, Short.MAX_VALUE)
                    .addComponent(detailspane))
                .addContainerGap())
        );
        
    }
    
    
    
    /***************************************************************************
     * View -> Controller
     **************************************************************************/
    
    
    private void addButtonActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    private void removeButtonActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
        
    }

    private void saveButtonActionPerformed(ActionEvent evt) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    private void messageButtonActionPerformed(ActionEvent evt) {
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
        
    }
    
}
