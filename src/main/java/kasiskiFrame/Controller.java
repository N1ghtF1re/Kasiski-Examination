package kasiskiFrame;

import javafx.concurrent.Task;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.TextArea;
import javafx.stage.FileChooser;
import men.brakh.kasiski.Kasiski;

import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ResourceBundle;

public class Controller {

    @FXML
    private ResourceBundle resources;

    @FXML
    private URL location;

    @FXML
    private ProgressBar pbMain;

    @FXML
    private Button btnSelectFile;

    @FXML
    private TextArea taMain;

    @FXML
    private Button btnDone;

    @FXML
    private Label lblDone;

    @FXML
    void btnSelectFileClick(ActionEvent event) throws IOException {
        lblDone.setText("");
        FileChooser fileChooser = new FileChooser();
        File selectedFile = fileChooser.showOpenDialog(null);
        if (selectedFile != null) {
            taMain.setText(new String(Files.readAllBytes(Paths.get(selectedFile.getAbsolutePath()))));
            System.out.println(taMain.getText());
        }

    }



    @FXML
    void btnDoneClick(ActionEvent event) {
        Task task = new Task<Void>() {
            int key;
            @Override public Void call() {
                Kasiski kasiski = new Kasiski(taMain.getText(), 3);

                key = kasiski.progressiveTest(pbMain);
                return null;
            }
            @Override
            protected void succeeded() {
                pbMain.progressProperty().setValue(1);

                lblDone.setText("Cryptotext analysis was performed. Estimated key length = " + key);
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    @FXML
    void initialize() {

    }
}