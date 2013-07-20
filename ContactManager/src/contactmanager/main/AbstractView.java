
package contactmanager.main;

import contactmanager.main.frame.MainFrame;
import java.awt.Cursor;
import javax.swing.JPanel;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public abstract class AbstractView extends JPanel implements PropertyChangeEventSink {
    
    protected final MainFrame mainFrame;
    
    public AbstractView(MainFrame mainFrame) {
        this.mainFrame = mainFrame;
    }
    
    public AbstractView() {
        this.mainFrame = null;
    }
    
    
    /**
     *  Methode fuer das Initialisiern der Grafikkomponenten 
     */
    protected abstract void initComponents();
    
    /**
     * Mauszeiger aendern
     * @param state true: Sanduhr; false: Standard
     */
    public void setMouseWaitCursor(boolean state) {
        if(state == true)
            this.setCursor(Cursor.getPredefinedCursor(Cursor.WAIT_CURSOR));
        else
            this.setCursor(Cursor.getPredefinedCursor(Cursor.DEFAULT_CURSOR));
    }
    
}


