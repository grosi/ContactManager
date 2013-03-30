
package contactmanager.main;

import contactmanager.main.controller.PropertyChangeEventSink;
import javax.swing.JFrame;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public abstract class AbstractFrame extends JFrame implements PropertyChangeEventSink {

    public AbstractFrame() {}
}
