/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package contactmanager.main.checklist;

import javax.swing.event.ChangeListener;

/**
 *
 * @author grosi
 */
public interface CheckListModel {
    
    public static class ListMember implements Comparable<ListMember>{
        private int id;
        private String text;
        private boolean state;
        
        public ListMember(String text, int id) {
            this.text = text;
            this.id = id;
            this.state = false;
        }
        
        public String getText() {
            return this.text;
        }
        
        public void setText(String text) {
            this.text = text;
        }
        
        public boolean getState() {
            return this.state;
        }
        
        public void setState(boolean state) {
            this.state = state;
        }
        
        public int getID() {
            return this.id;
        }

        @Override
        public int compareTo(ListMember t) {
            int comparetext = this.text.compareTo(t.getText());
            int compareid = t.getID();
            
            if(comparetext == 0) {
                if(this.id == compareid)
                    return 0;
                else if(this.id < compareid)
                    return -1;
                else
                    return 1;
            } else if(comparetext < 0) {
                return 1;         
            } else {
                return -1;
            }
        }
        
    } 
    
    
    public static class VisibleRange {
        private int delta;
        private ListMember start = null;
        private ListMember end = null;
        
        
        public VisibleRange(int delta) {
            this.delta = delta;
        }
        
        public int getDelta() {
            return this.delta;
        }
        
        public void setDelta(int delta) {
            this.delta = delta;
        }
        
        public ListMember getStart() {
            return this.start;
        }
        
        public void setStart(ListMember start) {
            this.start = start;
        }
        
        public ListMember getEnd() {
            return this.end;
        }
        
        public void setEnd(ListMember end) {
            this.end = end;
        }
    }
    
    
    public static class ListSeparator {
        private int index;
        private String text;
        
        public ListSeparator(int index) {
            this.index = index;
            this.text = null;
        }
        
        public ListSeparator(String text, int index) {
            this.index = index;
            this.text = text;
        }
        
        public ListSeparator(ListSeparator listseparator) {
            this(listseparator.text, listseparator.index);
        }
        
        public String getText() {
            return this.text;
        }
        
        public void setText(String text) {
            this.text = text;
        }
        
        public int getIndex() {
            return this.index;
        }
    }
    

    public boolean getListMemberState(int id) throws JCheckListException;
    public boolean getListMemberState(String text) throws JCheckListException;
    public void addListMember(String text, int id) throws JCheckListException;
    public int getListMemberCount();
    
    public boolean shiftVisibleRangeRight(int count);
    public boolean shiftVisibleRangeLeft(int count);
    
    public ListSeparator getListSeparator(int index);
    public void setListSeparator(ListSeparator listseparator);
    public int getListSeparatorCount();

   

    /**
     * Ein ChangeListener zum Model hinzufuegen
     * 
     * @param listener ChangeListener
     *     
     * @see #removeChangeListener
     */
    void addChangeListener(ChangeListener listener);

    /**
     * ChangeListener vom Model entfernen
     * 
     * @param listener der zu loeschende ChangeListener
     * 
     * @see #addChangeListener
     */
    void removeChangeListener(ChangeListener listener);
    
}
