package ToDo.src;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

public class EditPageController {

    @FXML
    private TextField titleField;

    @FXML
    private TextField detailField;

    @FXML
    private void saveEvent() {
        
        String title = titleField.getText();
        String detail = detailField.getText();


        EventDAO eventDAO = new EventDAO();
        Event newEvent = new Event();
        newEvent.setEventTitle(title);
        newEvent.setEventDetail(detail);
       

        eventDAO.addEvent(newEvent);

        System.out.println("Event Saved: " + newEvent.getEventTitle());
        System.out.println("Event Details: " + newEvent.getEventDetail());

        titleField.clear();
        detailField.clear();
    }
}
