
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
 * @author grosi
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
        this.setSelectionModel(createListSelectionModel());
        this.setCellRenderer(createListCellRenderer());
        //this.get
    }
    
    
    /**
     * Fuegt ein neues Listenelement hinzu oder aktualisiert ein bestehendes
     * @param text
     * @param id
     */
    public boolean addListMember(String text, int id) {
     
        ListMember newlistmember = new ListMember(text, id);
     
        /* Falls Eintrag schon vorhanden -> updaten */
        if(this.listmember.contains(newlistmember) == true) {
            int index = this.listmember.indexOf(newlistmember);
            newlistmember = this.listmember.get(index);
            newlistmember.setText(text);
            this.listmember.set(index, newlistmember);
            
            System.out.println("ListMember schon vorhanden -> Update"); /**@DEBUG */
            
            return true;
        /* Neuer Eintrag hinzufuegen */
        } else {
            return this.listmember.add(newlistmember);
        }  
    }
    
    
    /**
     * Loescht ein Listenelement anhand einer ID
     * @param id
     */
    public boolean removeListMember(int id) {
        
        ListMember removelistmember = new ListMember(id);
        
        /* Falls Eintrag vorhanden -> loeschen */
        if(this.listmember.contains(removelistmember) == true) {
            int index = this.listmember.indexOf(removelistmember);
            this.listmember.remove(index);
            System.out.println("Eintrag geloescht"); /**@DEBUG */
            return true;
        } else {
            System.out.println("Eintrag nicht vorhanden"); /**@DEBUG */
            return false;
        }
        
    }
    
    
     /**
     * Loescht ein Listenelemts anhand des Index
     * @param index Index eines Listenelementes
     */
    public void removeListMemberOfIndex(int index) {
        this.listmember.remove(index);
    }
    
    
    /**
     * ID eines Listenelementes 
     * @param index Index eines Listenelementes
     * @return ID des Listenelementes
     */
    public int getListMemberOfIndex(int index) {
        return ((ListMember)listmember_separator.get(index)).getID();  
    }
    
    
    /**
     * Gibt die aktuelle Anzahl an Listenelemente zurueck
     * @return
     */
    public int getListSize() {
        return listmember.size();
        //return listmember_separator.size();
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
     * Selection Model, das das Selektieren von Separatoren verhindert
     * @return Angepasstes Selection Model
     */
    private ListSelectionModel createListSelectionModel() {
        return new DefaultListSelectionModel() {
            @Override
            public void setSelectionInterval(int index0, int index1) {
                
                if(!(listmember_separator.get(index0) instanceof SeparatorList.Separator)) {
                    super.setSelectionInterval(index0, index0);
                }
                
            }
        };
    }

}
