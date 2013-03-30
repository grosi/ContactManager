package contactmanager.main;

import contactmanager.main.controller.PropertyChangeEventSink;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 27.03.2013
 */
public class AbstractController implements PropertyChangeListener {
    
    private ArrayList<PropertyChangeEventSink> registeredViews;
    private ArrayList<AbstractModel> registeredModels;
    
    public AbstractController() {
        registeredViews = new ArrayList<>();
        registeredModels = new ArrayList<>();
    }
    
    public void addModel(AbstractModel model) {
        registeredModels.add(model);
        model.addPropertyChangeListener(this);
    }
    
    public void removeModel(AbstractModel model) {
        registeredModels.remove(model);
        model.removePropertyChangeListener(this);
    }
    
    public void addView(PropertyChangeEventSink view) {
        registeredViews.add(view);
    }
    
    public void removeView(PropertyChangeEventSink view) {
        registeredViews.remove(view);
    }
   
    
    @Override
    public void propertyChange(PropertyChangeEvent evt) {
        for (PropertyChangeEventSink view: registeredViews) {
            view.modelPropertyChange(evt);
        }
    }
}
