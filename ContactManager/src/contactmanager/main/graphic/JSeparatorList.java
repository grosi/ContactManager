
package contactmanager.main.graphic;

import ca.odell.glazedlists.BasicEventList;
import ca.odell.glazedlists.EventList;
import ca.odell.glazedlists.FilterList;
import ca.odell.glazedlists.SeparatorList;
import ca.odell.glazedlists.SortedList;
import ca.odell.glazedlists.TextFilterator;
import ca.odell.glazedlists.matchers.Matcher;
import ca.odell.glazedlists.swing.DefaultEventListModel;
import java.awt.Component;
import java.awt.Font;
import java.util.Comparator;
import java.util.List;
import javax.swing.BorderFactory;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListSelectionModel;
import javax.swing.ListCellRenderer;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.ListSelectionModel;

/**
 * Swing-Komponente, die eine Check-Liste zur Verfuegung stellt
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 25.04.2013
 * @TODO remove Methoden zum leeren der Liste hinzufuegen
 * @TODO Sortiermoeglichkeiten anpassbar machen -> Sortierkriterrium
 */
public class JSeparatorList extends JList{

    private EventList<ListMember> listmember;
    private SortedList<ListMember> listmember_sorted;
    private SeparatorList listmember_separator;
    private FilterList<ListMember> listmember_filtered; /** @TODO */
    
    private String filtertext = null; /** @TODO /*
    
    
    /**
     *  Liste, die die Eintraege alphabetisch sortiert und unterteilt
     */
    public JSeparatorList() {
        super();
        
        listmember = new BasicEventList<>();
        listmember_sorted = new SortedList(listmember, listMemberComparator());
        listmember_separator = new SeparatorList(listmember_sorted, listSeparatorComparator(), 0, 1000);
        listmember_filtered = new FilterList(listmember, new ListMemberMatcher());

        this.setModel(new DefaultEventListModel(listmember_separator));
        //this.setSelectionModel(createListSelectionModel());
        this.setSelectionModel(new SeparatorListSelectionModel());
        this.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        this.setCellRenderer(createListCellRenderer());
    }
    
    
    /**
     * Fuegt ein neues Listenelement hinzu oder aktualisiert ein bestehendes
     * @param text
     * @param id
     */
    public ListMember addListMember(String text, int id) {
  
        /* Falls Eintrag schon vorhanden -> updaten */
        ListMember member_exists = containsListMember(id);
        if(member_exists != null) {
            int index = this.listmember.indexOf(member_exists);
            member_exists.setText(text);
            //((SeparatorListSelectionModel)this.getSelectionModel()).setSilent(true);
            this.listmember.set(index, member_exists);
            return member_exists;
        /* Neuer Eintrag hinzufuegen */
        } else {
            ListMember newlistmember = new ListMember(text, id);
            //((SeparatorListSelectionModel)this.getSelectionModel()).setSilent(true);
            this.listmember.add(newlistmember);
            return newlistmember;
        }  
    }
    
    
    /**
     * Loescht ein Listenelement anhand einer ID
     * @param id
     */
    public ListMember removeListMember(int id) {
        
        /* Falls Eintrag vorhanden -> loeschen */
        ListMember member_exists = containsListMember(id);
        if(member_exists != null) {
            //((SeparatorListSelectionModel)this.getSelectionModel()).setSilent(true);
            this.listmember.remove(member_exists);
            return member_exists;
        } else {
            return null;
        }
        
    }
    
    
    /**
    * Loescht ein Listenelemts anhand des Index
    * @param index Index eines Listenelementes
    */
    public ListMember removeListMemberOfIndex(int index) {
        Object index_object = this.listmember_separator.get(index);
        int member_index = this.listmember.indexOf(index_object);
        ListMember member = this.listmember.get(member_index);
        ((SeparatorListSelectionModel)this.getSelectionModel()).setSilent(true);
        this.listmember.remove(member);
        return member;
    }
    
    
    /**
     * ID eines Listenelementes 
     * @param index Index eines Listenelementes
     * @return ID des Listenelementes
     */
    public ListMember getListMemberOfIndex(int index) {
        Object member = listmember_separator.get(index);
        int listmember_index=listmember.indexOf(member);
        return listmember.get(listmember_index);
    }
    
    
    /**
     * Index in der Liste eines Listeneintrags
     * @param member Referenz auf Listeneintrag
     * @return 
     */
    public int getListMemberIndex(ListMember member) {
        
        return listmember_separator.indexOf(member);
    }
   
    
    /**
     * Gibt die aktuelle Groesse der Liste zurueck
     * @return
     */
    public int getListSize() {
        return listmember_separator.size();
    }
    
    
    /**
     * Aktuelle Anzahl der Listeneintraege ohne Separatoren
     * @return 
     */
    public int getListMemberSize() {
        return listmember.size();
    }
    
    
    /**
     * Prueft ob ein Listeneintrag mit einer bestimmt ID bereits existiert
     * @param id zu kontrollierende ID
     * @return Referenz auf den Listeneintrag, wenn vorhanden. Ansonsten null
     */
    public ListMember containsListMember(int id) {
        for(ListMember member : listmember) {
            if(member.getID() == id)
                return member;
        }
        return null;
    }
    
     
    /**
     * Event Listener fuer die naechsten 2 Aktionen ausschalten
     * @param state 
     */
    public void setListenerSilent(boolean state) {
        ((SeparatorListSelectionModel)this.getSelectionModel()).setSilent(state);
    }
    
    
    /**
     *  Spezifikation des Listeninhalts
     */
    public static class ListMember {
        /** einmalige ID */
        private int id = 0;
        
        /** sichtbarer Text */
        private String text = null;
        
        public ListMember(String text, int id) {
            this.text = text;
            this.id = id;
        }
        
        public ListMember(int id) {
            this.id = id;
        }
        
        public String getText() {
            return this.text;
        }
        
        public void setText(String text) {
            this.text = text;
        }
        
        public int getID() {
            return this.id;
        }    
        
        @Override
        public boolean equals(Object o) {
            boolean same = false;
            
            if(o != null && o instanceof ListMember) {
                same = this.id == ((ListMember) o).getID();
            }
            
            return same;
        }

        @Override
        public int hashCode() {
            int hash = 3;
            hash = 29 * hash + this.id;
            return hash;
        }
    } 
    
    
    /**
     *  Vergleicht Listeneintraege nach Text
     */
    private Comparator listMemberComparator() {
        return new Comparator<ListMember>() {

            @Override
            public int compare(ListMember a, ListMember b) {
                String listmember_a = a.getText();
                String listmember_b = b.getText();
            
                return listmember_a.compareTo(listmember_b);
            }
        };
    }
    
    
    /**
     * Vergleicht Listeneintraege nach dem ersten Buchstaben
     */
    private Comparator listSeparatorComparator() {
        return new Comparator<ListMember>() {

            @Override
            public int compare(ListMember a, ListMember b) {
                String listmember_a = a.getText().substring(0, 1).toUpperCase();
                String listmember_b = b.getText().substring(0, 1).toUpperCase();
            
                return listmember_a.compareTo(listmember_b);
            }
        };
    }
      
    
    /**
     *  Filter fuer Listeneintraege
     * @TODO evtl. entfernen
     */
    public class ListMemberTextFilterator implements TextFilterator<ListMember> {

        @Override
        public void getFilterStrings(List<String> list, ListMember e) {
            list.add(e.getText());
            list.add(Integer.toString(e.getID()));
        }
        
    }
    
    
    /**
     * Matcher fuer Filterung
     * @TODO evtl. entfernen
     */
    public class ListMemberMatcher implements Matcher<ListMember> {

        @Override
        public boolean matches(ListMember e) {
            
            if(filtertext == null)
                return false;
            
            if(e.getText().compareTo(filtertext) == 0 || Integer.toString(e.getID()).compareTo(filtertext) == 0) {
                return true;
            }
            else {
                return false;
            }  
        }
        
    } 

    
    /**
     * List Renderer der nach Inhalt und Separator unterscheidet
     * @return Angepasster Default-Renderer
     */
    private ListCellRenderer createListCellRenderer() {
        return new DefaultListCellRenderer() {
            
            @Override
            public Component getListCellRendererComponent(JList list, Object value, int index, boolean isSelected, boolean cellHasFocus) {
                
                JLabel label = (JLabel) super.getListCellRendererComponent(
                        list, value, index, isSelected, cellHasFocus);

                /* Separator: Schrift Bold */
                if (value instanceof SeparatorList.Separator) {
                    SeparatorList.Separator separator = (SeparatorList.Separator) value;
                    ListMember listmember = (ListMember) separator.getGroup().get(0);
                    label.setText(listmember.getText().substring(0,1));
                    label.setFont(label.getFont().deriveFont(Font.BOLD));
                    label.setBorder(BorderFactory.createEmptyBorder(0,5,0,0));
                /* Listen-Inhalt: Schrift normal */ 
                } else {
                    ListMember listmember = (ListMember) value;
                    label.setFont(label.getFont().deriveFont(Font.PLAIN));
                    label.setBorder(BorderFactory.createEmptyBorder(0,15,0,0));
                    label.setText(listmember.getText());
                }

                return label;
            }
        };
    }  
   
    
    /**
     * Eigenes Selection Model fuer JList
     */
    private class SeparatorListSelectionModel extends DefaultListSelectionModel {
        private boolean silent = false;
        private int silent_cnt = 1; //Workaround: verhindert, dass manchmal ungewollte Event durchrutschen

        
        /**
         * Dient zum Unterdruecken von Events
         * @param state true: keine Events zulassen
         */
        public void setSilent(boolean state){
            silent = state;
            silent_cnt = 1;
        }
        
        /**
         * Das Selektieren von Separatoren verhindern
         * @param index0
         * @param index1 
         */
        @Override
        public void setSelectionInterval(int index0, int index1) {

            if(!(listmember_separator.get(index0) instanceof SeparatorList.Separator)) {
                super.setSelectionInterval(index0, index0);
            } else {
                if(getAnchorSelectionIndex() < index0) {
                    super.setSelectionInterval(index0+1, index0+1);
                } else {
                    super.setSelectionInterval(index0-1, index0-1);
                }
            }   
        }
        
        @Override
        protected void fireValueChanged(int firstIndex, int lastIndex, boolean isAdjusting){
            if(silent){
                if(this.getValueIsAdjusting() == false && silent_cnt == 0) {
                    setSilent(false);
                }
                silent_cnt--;
            } else {
                super.fireValueChanged(firstIndex, lastIndex, isAdjusting);
            }
                
        }
        
        @Override
        protected void fireValueChanged(int firstIndex, int lastIndex){
            if(silent){
                if(this.getValueIsAdjusting() == false && silent_cnt == 0) {
                    setSilent(false);
                }
                silent_cnt--;
            } else {
                super.fireValueChanged(firstIndex, lastIndex);
            }
        }
        
        @Override
        protected void fireValueChanged(boolean isAdjusting) {
            super.fireValueChanged(isAdjusting);
        }
    }
}
