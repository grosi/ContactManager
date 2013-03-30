
package contactmanager.main;

import contactmanager.main.controller.MainController;
import javax.swing.SwingUtilities;

/**
 * @author Simon Grossenbacher
 * @author Kevin Gerber
 * @author Philippe Loeffel
 * @author Philipp Eder
 * @version 0.1
 * @since 27.03.2013
 */
public class ContactManager {
    
    public ContactManager() {
        new MainController();
    }
    
    
    /**
     * Hauptfunktion
     * @param args 
     */
    public static void main(String[] args) {

        SwingUtilities.invokeLater(new Runnable() {

            @Override
            public void run() {
                new ContactManager();
            }
        });
    
    }
}
