import javafx.application.Application;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;
import javafx.stage.Stage;

import javax.swing.*;
import java.applet.Applet;
import java.awt.*;

public class Main extends Application {

    public Main() throws HeadlessException {
        super();
        //setLayout( new BorderLayout());
        //add(new MainForm().$$$getRootComponent$$$());
        //setVisible(true);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        StackPane root = new StackPane();
        WebView view = new WebView();
        WebEngine engine = view.getEngine();
        engine.load("http://google.com");
        root.getChildren().add(view);
        Scene scene = new Scene(root, 640, 480);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        JFrame f = new JFrame("Вакансии");
        f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        //f.add(new VDNH());
        f.add(new MainForm().$$$getRootComponent$$$());
        f.pack();
        f.setVisible(true);
        //launch(args);
    }
}
