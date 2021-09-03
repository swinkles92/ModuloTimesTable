import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class main extends Application {
    public static void main(String[] args) { launch(args); }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Modulo Times Tables");
        int width, height = 500;

        Button startBtn = new Button();
        startBtn.setText("Start");
        Button pauseBtn = new Button();
        pauseBtn.setText("Pause");
        ComboBox colorMenu = new ComboBox();
        colorMenu.getItems().addAll("Azure", "Chartreuse", "Coral", "Crimson", "Orchid",
                "Peach Puff", "Salmon", "Seashell", "Thistle", "Misty Rose");

        BorderPane root = new BorderPane();

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        root.setLeft(vbox);
        vbox.getChildren().addAll(startBtn, pauseBtn, colorMenu);

        Circle circle = new Circle(250,250,250);
        circle.setFill(Color.PEACHPUFF);

        double degI = 360 / 36;
        Group lineGroup = new Group();
        for(int i = 0; i < 36; i++) {
            double startDeg = Math.toRadians(i * degI);
            double endDeg = Math.toRadians((2 * i) * degI);
            Line line = new Line(250 * Math.cos(startDeg), 250 * Math.sin(startDeg),
                    250 * Math.cos(endDeg),
                    250 * Math.sin(endDeg));
            line.setStroke(Color.CRIMSON);
            lineGroup.getChildren().add(line);
        }

        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, lineGroup);
        root.setCenter(stack);
        /*colorMenu.setOnAction(event -> {
            colorAction(colorMenu.getValue().toString(), circle);
        });*/

        primaryStage.setScene(new Scene(root, 750, 750));
        primaryStage.show();
    }
    private void colorAction(String listItem, Circle circle) {
        switch(listItem) {
            case "Azure" : circle.setFill(Color.AZURE);
                    break;
            case "Chartreuse" : circle.setFill(Color.CHARTREUSE);
                    break;
            case "Coral" : circle.setFill(Color.CORAL);
                    break;
            case "Crimson" : circle.setFill(Color.CRIMSON);
                    break;
            case "Orchid" : circle.setFill(Color.ORCHID);
                    break;
            case "Peach Puff" : circle.setFill(Color.PEACHPUFF);
                    break;
            case "Salmon" : circle.setFill(Color.SALMON);
                    break;
            case "Seashell" : circle.setFill(Color.SEASHELL);
                    break;
            case "Thistle" : circle.setFill(Color.THISTLE);
                    break;
            case "Misty Rose" : circle.setFill(Color.MISTYROSE);
        }
    }
}
