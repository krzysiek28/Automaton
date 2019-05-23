package gui;

import automaton.Automaton;
import cell.states.BinaryState;
import cell.states.UniformStateFactory;
import game.GameOfLife;
import game.GameOfLifeHelper;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import neighbor.MooreNeighborhood;

public class GraphicUserInterface extends Application {

    private static int MAIN_APP_HEIGHT = 700;

    private static int MAIN_APP_WIDTH = 1350;

    private static int NEXT_STATE_BUTTON_HEIGHT = 50;

    private static int NEXT_STATE_BUTTON_WIDTH = 200;

    private static int xCellCount = 10;

    private static int yCellCount = 10;

    private GridPane mainWindow = new GridPane();

    private GridPane menuPane = new GridPane();

    private Board board;

    private ComboBox selectGameComboBox = new ComboBox();

    private Slider widthSlider;

    private Slider heightSlider;

    private ComboBox selectNeighborhoodComboBox = new ComboBox();

    private Button nextStateButton = new Button();

    private CheckBox mapWrappingCheckBox = new CheckBox("Zawijanie sąsiedztwa");

    private Spinner<Integer> radiusSpinner = new Spinner<Integer>();

    public GraphicUserInterface(){
    }

    private void setUpLayout(Board board){
        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
        GridPane.setMargin(mainWindow, new Insets(10,10,10,10));
        setUpNextStateButton();
        setUpMenuPane();
        mainWindow.add(board.initBoard(), 1,0);
    }

    private void setUpNextStateButton(){
        nextStateButton.setText("Nowy stan");
        nextStateButton.setPrefSize(NEXT_STATE_BUTTON_WIDTH, NEXT_STATE_BUTTON_HEIGHT);
        addNextStateButtonListener();
        mainWindow.add(nextStateButton, 1, 1);
    }

    private void setUpMenuPane(){
        GridPane.setMargin(menuPane, new Insets(15,10,15,10));
        menuPane.setPrefWidth(300);
        menuPane.setAlignment(Pos.TOP_CENTER);
//        menuPane.setGridLinesVisible(true);
        menuPane.setVgap(10);
        //select game x2 comboBox
        setUpGameComboBox();
        //set board size x2 sliders?
        setUpBoardWidth();
        setUpBoardHeight();
        //select neighborhood x1 comboBox
        setUpNeighborhood();
        //map wrapping x1 checkbox
        menuPane.add(mapWrappingCheckBox, 0, 6);
        //set radius x1 text -> int
        setUpRadiusSpinner();
        //add to new sections new Separator();
        //set alive rules x1 text
        //set dead rules x1 text
        //set rules v3 text -> int
        //accept rules
        mainWindow.add(menuPane, 0, 0);
    }

    private void setUpGameComboBox(){
        selectGameComboBox.getItems().add("Gra w życie");
        selectGameComboBox.getItems().add("Automaty jednowymiarowe");
        selectGameComboBox.getSelectionModel().selectFirst();
        menuPane.add(selectGameComboBox, 0, 0);
    }

    private void setUpBoardWidth(){
        Label widthLabel = new Label("Szerokość:");
        widthSlider = createAndSetUpSlider();
        menuPane.add(widthLabel, 0, 1);
        menuPane.add(widthSlider, 0, 2);
    }

    private void setUpBoardHeight(){
        Label heightLabel = new Label("Wysokość:");
        heightSlider = createAndSetUpSlider();
        menuPane.add(heightLabel, 0, 3);
        menuPane.add(heightSlider, 0, 4);
    }

    private Slider createAndSetUpSlider() {
        Slider slider = new Slider();
        slider.setMin(10);
        slider.setMax(120);
        slider.setValue(40);
        slider.setShowTickLabels(true);
        slider.setShowTickMarks(true);
        return slider;
    }

    private void setUpNeighborhood() {
        selectNeighborhoodComboBox.getItems().add("Moore Neighborhood");
        selectNeighborhoodComboBox.getItems().add("Von Neuman Neighborhood");
        selectNeighborhoodComboBox.getSelectionModel().selectFirst();
        menuPane.add(selectNeighborhoodComboBox, 0, 5);
    }

    private void setUpRadiusSpinner(){
        Label radiusLabel = new Label("Promień:");
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        radiusSpinner.setValueFactory(valueFactory);

        menuPane.add(radiusLabel, 0, 6);
        menuPane.add(radiusSpinner, 0, 7);
    }

    private void addNextStateButtonListener(){
        nextStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.update();
            }
        });
    }

    public void start(Stage stage) {
        Automaton automaton = createNewGameOfLife(xCellCount, yCellCount, mapWrappingCheckBox.isSelected(), 1, "3,2", "2");
        board = new Board(automaton);

        setUpLayout(board);

        stage.setTitle("Automaty komórkowe");
        stage.setScene(new Scene(mainWindow));
        stage.show();
    }

    public Automaton createNewGameOfLife(int xCellCount, int yCellCount, boolean mapWrapping, int radius,
         String aliveRules, String deadRules){
        return new GameOfLife(
            new UniformStateFactory(BinaryState.DEAD),
            new MooreNeighborhood(1, false, xCellCount, yCellCount),
            xCellCount, yCellCount,
            GameOfLifeHelper.convertStringToCellRulesList("3,2"),
            GameOfLifeHelper.convertStringToCellRulesList("2"));
    }
}
