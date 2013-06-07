
package contactmanager.main;

import contactmanager.main.frame.MainFrame;
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
    
}


