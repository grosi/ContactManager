/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactmanager.main.checklist;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.SortedSet;
import java.util.TreeSet;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import javax.swing.event.EventListenerList;

/**
 *
 * @author grosi
 */
public class DefaultCheckListModel implements CheckListModel {
    
    /** The listeners waiting for model changes. */
    protected EventListenerList listenerList = new EventListenerList();

    TreeSet<ListMember> listmember_sorted = new TreeSet<>();
    HashMap<String,ListMember> listmember_searcheable = new HashMap<>();
    ArrayList<ListMember> listmember_visible = new ArrayList<>();
    
    
    VisibleRange visiblerange = new VisibleRange(50);
    
    
    @Override
    public boolean getListMemberState(int id) throws JCheckListException {
        
        ListMember listmember = listmember_searcheable.get(Integer.toString(id));
            
        if(listmember == null) 
            throw new JCheckListException("Wrong ID -> not found");
        
        return listmember.getState();
    }

    @Override
    public boolean getListMemberState(String text) throws JCheckListException {
        SortedSet<ListMember> listmember = listmember_sorted.subSet(
                new ListMember(text, 0), new ListMember(text+" ", 0));
        
        System.out.println(listmember);
        
        return true;
    }

    @Override
    public void addListMember(String text, int id) throws JCheckListException {
        ListMember listmember = new ListMember(text, id);
        
        boolean result = listmember_sorted.add(listmember);
        if(result == false) throw new JCheckListException("List member exists");
        
        result = listmember_searcheable.containsKey(Integer.toString(id));
        if(result == false) throw new JCheckListException("List member exists");
        listmember_searcheable.put(Integer.toString(id), listmember);
        
        
        
        if(listmember_sorted.size() > visiblerange.getDelta()) {
            if(listmember_sorted.headSet(listmember).contains(visiblerange.getStart()) &&
                    listmember_sorted.tailSet(listmember).contains(visiblerange.getEnd())) {
                visiblerange.setEnd(listmember_sorted.lower(visiblerange.getEnd()));
            }
            
        } else {
            visiblerange.setStart(listmember_sorted.first());
            visiblerange.setEnd(listmember_sorted.last());
        }
        
        
    }

    @Override
    public int getListMemberCount() {
        return listmember_sorted.size();
    }
    
    
    @Override
    public boolean shiftVisibleRangeRight(int count) {
        ListMember last_listmember = listmember_sorted.last();
        ListMember start_listmember = visiblerange.getStart();
        ListMember end_listmember = visiblerange.getEnd();
        
        for(int i = 0; i<count; i++) {
            start_listmember = listmember_sorted.higher(start_listmember);
            end_listmember = listmember_sorted.higher(end_listmember);
            
            if(end_listmember == last_listmember) {
                return false;
            }
        }
        
        visiblerange.setStart(start_listmember);
        visiblerange.setEnd(end_listmember);
        
        return true;
    }

    @Override
    public boolean shiftVisibleRangeLeft(int count) {
        ListMember first_listmember = listmember_sorted.first();
        ListMember start_listmember = visiblerange.getStart();
        ListMember end_listmember = visiblerange.getEnd();   
        
        for(int i = 0; i<count; i++) {
            start_listmember = listmember_sorted.lower(start_listmember);
            end_listmember = listmember_sorted.lower(end_listmember);
            
            if(end_listmember == first_listmember) {
                return false;
            }
        }
        
        visiblerange.setStart(start_listmember);
        visiblerange.setEnd(end_listmember);
        
        return true;
    }

    

    @Override
    public ListSeparator getListSeparator(int index) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void setListSeparator(ListSeparator listseparator) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int getListSeparatorCount() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
    
    
    /*
     * (non-Javadoc)
     * 
     * @see org.jvnet.flamingo.slider.FlexiRangeModel#addChangeListener(javax.swing.event.ChangeListener)
     */
    @Override
    public void addChangeListener(ChangeListener listener) {
        listenerList.add(ChangeListener.class, listener);
    }

    /*
     * (non-Javadoc)
     * 
     * @see org.jvnet.flamingo.slider.FlexiRangeModel#removeChangeListener(javax.swing.event.ChangeListener)
     */
    @Override
    public void removeChangeListener(ChangeListener listener) {
        listenerList.remove(ChangeListener.class, listener);
    }

    /**
     * Runs each <code>ChangeListener</code>'s <code>stateChanged</code>
     * method.
     */
    protected void fireStateChanged() {
        ChangeEvent event = new ChangeEvent(this);
        Object[] listeners = listenerList.getListenerList();
        for (int i = listeners.length - 2; i >= 0; i -= 2) {
            if (listeners[i] == ChangeListener.class) {
                    ((ChangeListener) listeners[i + 1]).stateChanged(event);
            }
        }
    }

    /**
     * Returns an array of all the change listeners registered on this
     * <code>DefaultBoundedRangeModel</code>.
     * 
     * @return all of this model's <code>ChangeListener</code>s or an empty
     *         array if no change listeners are currently registered
     * 
     * @see #addChangeListener
     * @see #removeChangeListener
     */
    public ChangeListener[] getChangeListeners() {
        return (ChangeListener[]) listenerList
            .getListeners(ChangeListener.class);
    }

    
    

   
}
