
package contactmanager.main;

/**
 * @author Simon Grossenbacher
 * @version 0.1
 * @since 28.03.2013
 */
public interface SubController {

    public void updateData();
    public Object getDAO();
    public void addViewToFrame(String title, AbstractView view);
    
}
