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

        // Creates and instantiates variables necessary for creation of the diagram.
        TimesTable timesTable = new TimesTable();
        timesTable.setTimesTable(0);
        NumNodes numNodes = new NumNodes();
        numNodes.setNumNodes(1);
        DelayTimer delayTimer = new DelayTimer();
        delayTimer.setDelayTimer(250_000_000);

        Group lineGroup = new Group();
        AnimationTimer timer = new AnimationTimer() {
            private long lastUpdate = 0;
            @Override
            public void handle(long now) {
                if(numNodes.getNumNodes() <= 360
                        && now - lastUpdate >= delayTimer.getDelayTimer()) {
                    lineGroup.getChildren().clear();
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
                    numNodes.setNumNodes(numNodes.getNumNodes() + 1);
                    lastUpdate = now;
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

        Label delayLabel = new Label("Delay by (ms): " + 250);
        Slider delaySlider = new Slider(10, 500, 250);
        delaySlider.setShowTickLabels(true);
        delaySlider.setShowTickMarks(true);
        delaySlider.valueProperty().addListener(
                new ChangeListener<Number>() {
                    @Override
                    public void changed(ObservableValue<? extends Number> observable,
                                        Number oldValue, Number newValue) {
                        delayTimer.setDelayTimer(newValue.longValue() * (10^6));
                        delayLabel.setText("Delay by (ms): " + newValue.longValue());
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

        Label myFavsLabel = new Label("My Favorites: ");
        ComboBox myFavorites = new ComboBox();
        myFavorites.getItems().addAll("182,360", "127,183", "145,180", "307,168",
                "190,360", "252,70", "87,40", "242,360", "287,360", "52,360");
        myFavorites.setOnAction(event -> {
            lineGroup.getChildren().clear();
            String favSelect = (String) myFavorites.getValue();
            String delimiter = ",";
            String[] tokens = favSelect.split(delimiter);
            int favTimesTable = Integer.parseInt(tokens[0]);
            int favNumNodes = Integer.parseInt(tokens[1]);
            double degPerNode = 360 / favNumNodes;
            for(int i = 0; i < favNumNodes; i++) {
                double startDeg = Math.toRadians(i * degPerNode);
                double endDeg = Math.toRadians((favTimesTable * i) * degPerNode);
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
        });

        VBox vbox = new VBox();
        vbox.setPadding(new Insets(10));
        vbox.setSpacing(8);
        root.setLeft(vbox);
        vbox.getChildren().addAll(startBox, colorMenu, timesLabel, timesSlider,
                nodeLabel, nodeSlider, delayLabel, delaySlider,
                timesJumpLabel, timesJumpField, nodesJumpLabel, nodesJumpField,
                jumpBtn, myFavsLabel, myFavorites);

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
        public int getTimesTable() { return timesTable; }
        public void setTimesTable(int newTimesTable) { this.timesTable = newTimesTable; }
    }
    public class NumNodes {
        private int numNodes;
        public int getNumNodes() { return numNodes; }
        public void setNumNodes(int newNumNodes) { this.numNodes = newNumNodes; }
    }
    public class DelayTimer {
        private long delayTimer;
        public long getDelayTimer() { return delayTimer; }
        public void setDelayTimer(long newTimer) { this.delayTimer = newTimer; }
    }
}