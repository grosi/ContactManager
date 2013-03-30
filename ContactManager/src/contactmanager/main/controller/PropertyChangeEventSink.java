
package contactmanager.main.controller;

import java.beans.PropertyChangeEvent;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public interface PropertyChangeEventSink {
    public void modelPropertyChange(final PropertyChangeEvent evt);    
}
