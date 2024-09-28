module br.com.eduarda.simplestock {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.desktop;


    opens br.com.eduarda.simplestock to javafx.fxml;
    exports br.com.eduarda.simplestock;
    exports br.com.eduarda.simplestock.controlls;
    opens br.com.eduarda.simplestock.controlls to javafx.fxml;
}