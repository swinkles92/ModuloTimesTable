import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Insets;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
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
        final int SIZE = 500;
        BorderPane root = new BorderPane();

        ComboBox colorMenu = new ComboBox();
        colorMenu.getItems().addAll("Blue", "Crimson", "Goldenrod", "Green",
                "Indigo", "Maroon", "Orange", "Peru", "Salmon", "Teal");

        TimesTable timesTable = new TimesTable();
        timesTable.setTimesTable(0);
        NumNodes numNodes = new NumNodes();
        numNodes.setNumNodes(1);

        Group lineGroup = new Group();
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                double degPerNode = 360 / numNodes.getNumNodes();
                for(int i = 0; i < numNodes.getNumNodes(); i++) {
                    double startDeg = Math.toRadians(i * degPerNode);
                    double endDeg = Math.toRadians((timesTable.getTimesTable() * i) * degPerNode);
                    Line line = new Line(
                            (SIZE / 2) * Math.cos(startDeg),
                            (SIZE / 2) * Math.sin(startDeg),
                            (SIZE / 2) * Math.cos(endDeg),
                            (SIZE / 2) * Math.sin(endDeg));
                    String colorStr;
                    if(colorMenu.getValue() != null) {
                        colorStr = colorMenu.getValue().toString();
                    }
                    else colorStr = Color.BLACK.toString();
                    line.setStroke(Color.valueOf(colorStr));
                    lineGroup.getChildren().add(line);
                }
            }
        };

        HBox startBox = new HBox();
        startBox.setSpacing(5);
        Button startBtn = new Button();
        startBtn.setText("Start");
        startBtn.setOnAction(event -> {
            timer.start();
        });
        Button pauseBtn = new Button();
        pauseBtn.setText("Pause");
        pauseBtn.setOnAction(event -> {
            timer.stop();
        });
        startBox.getChildren().addAll(startBtn, pauseBtn);

        Label timesLabel = new Label("Times Table: " + timesTable.getTimesTable());
        Slider timesSlider = new Slider(0, 360, 0);
        timesSlider.setShowTickLabels(true);
        timesSlider.setShowTickMarks(true);
        timesSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                                        Number oldValue, Number newValue) {
                        timesTable.setTimesTable(newValue.intValue());
                        timesLabel.setText("Times Table: " + newValue.intValue());
                        lineGroup.getChildren().clear();
                    }
                }
        );

        Label nodeLabel = new Label("Number of Nodes: " + numNodes.getNumNodes());
        Slider nodeSlider = new Slider(1, 360, 0);
        nodeSlider.setShowTickLabels(true);
        nodeSlider.setShowTickMarks(true);
        nodeSlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                                        Number oldValue, Number newValue) {
                        numNodes.setNumNodes(newValue.intValue());
                        nodeLabel.setText("Number of Nodes: " + newValue.intValue());
                        lineGroup.getChildren().clear();
                    }
                }
        );

        Label timesJumpLabel = new Label("Jump to Times Table: ");
        TextField timesJumpField = new TextField();
        Label nodesJumpLabel = new Label("Jump to Number Nodes: ");
        TextField nodesJumpField = new TextField();
        Button jumpBtn = new Button();
        jumpBtn.setText("Jump To");
        jumpBtn.setOnAction(event -> {
            int newTable = Integer.parseInt(timesJumpField.getText());
            timesTable.setTimesTable(newTable);
            timesLabel.setText("Times Table: " + timesTable.getTimesTable());
            timesSlider.setValue(timesTable.getTimesTable());

            int newNodes = Integer.parseInt(nodesJumpField.getText());
            numNodes.setNumNodes(newNodes);
            nodeLabel.setText("Number of Nodes: " + numNodes.getNumNodes());
            nodeSlider.setValue(numNodes.getNumNodes());

            lineGroup.getChildren().clear();
            timer.start();
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        root.setLeft(vbox);
        vbox.getChildren().addAll(startBox, colorMenu, timesLabel, timesSlider,
                nodeLabel, nodeSlider, timesJumpLabel, timesJumpField,
                nodesJumpLabel, nodesJumpField, jumpBtn);

        Circle circle = new Circle(250,250,250);
        circle.setFill(Color.GHOSTWHITE);

        StackPane stack = new StackPane();
        stack.getChildren().addAll(circle, lineGroup);
        root.setCenter(stack);

        primaryStage.setScene(new Scene(root, 750, 750));
        primaryStage.show();
    }
    public class TimesTable {
        private int timesTable;
        public int getTimesTable() {
            return timesTable;
        }
        public void setTimesTable(int newTimesTable) {
            this.timesTable = newTimesTable;
        }
    }
    public class NumNodes {
        private int numNodes;
        public int getNumNodes() {
            return numNodes;
        }
        public void setNumNodes(int newNumNodes) {
            this.numNodes = newNumNodes;
        }
    }
}