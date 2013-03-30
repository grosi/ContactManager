
package contactmanager.main;

import contactmanager.main.controller.PropertyChangeEventSink;
import contactmanager.main.view.MainFrame;
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
    
    /**
     * 
     */
    protected abstract void initComponents();
    
}


