package gui;

import automaton.Automaton;
import cell.states.BinaryState;
import cell.states.UniformStateFactory;
import game.GameOfLife;
import game.GameOfLifeHelper;
import game.Rule256;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import neighbor.MooreNeighborhood;
import neighbor.Neighborhood1D;

public class GraphicUserInterface extends Application {

    private static final int MAIN_APP_HEIGHT = 700;

    private static final int MAIN_APP_WIDTH = 1350;

    private static final int NEXT_STATE_BUTTON_HEIGHT = 50;

    private static final int NEXT_STATE_BUTTON_WIDTH = 200;

    private static int xCellCount = 10;

    private static int yCellCount = 10;

    private static final String GAME_OF_LIFE = "Gra w życie";

    private static final String AUTOMATON_1D = "Automaty jednowymiarowe";

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

    private TextField aliveRulesTextField = new TextField();

    private TextField deadRulesTextField = new TextField();

    private Spinner<Integer> automaton1DRulesSpinner = new Spinner<Integer>();

    private Button settingAcceptButton = new Button();

    private Button cleanBoardButton = new Button();

    public GraphicUserInterface(){
    }

    private void setUpLayout(Board board){
        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
        GridPane.setMargin(mainWindow, new Insets(10,10,10,10));
        setUpNextStateButton();
        setUpMenuPane();
        mainWindow.add(board.getBoardPane(), 1,0);
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
        setUpGameComboBox();
        setUpBoardWidth();
        setUpBoardHeight();
        setUpNeighborhood();
        menuPane.add(mapWrappingCheckBox, 0, 6);
        setUpRadiusSpinner();
        setUpAliveRulesTextField();
        setUpDeadRulesTextField();
        setUpAutomaton1DRulesSpinner();
        setUpSettingAcceptButton();
        mainWindow.add(menuPane, 0, 0);
    }

    private void setUpGameComboBox(){
        selectGameComboBox.getItems().add(GAME_OF_LIFE);
        selectGameComboBox.getItems().add(AUTOMATON_1D);
        selectGameComboBox.getSelectionModel().selectFirst();
        addSelectGameComboBoxListener();
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

        menuPane.add(radiusLabel, 0, 7);
        menuPane.add(radiusSpinner, 0, 8);
    }

    private void setUpAliveRulesTextField(){
        Label aliveRulesLabel = new Label("Zasady przeżywania komórki:");
        aliveRulesTextField.setText("2,3");
        menuPane.add(aliveRulesLabel, 0, 9);
        menuPane.add(aliveRulesTextField, 0, 10);
    }

    private void setUpDeadRulesTextField(){
        Label deadRulesLabel = new Label("Zasady umierania komórki:");
        deadRulesTextField.setText("3");
        menuPane.add(deadRulesLabel, 0, 11);
        menuPane.add(deadRulesTextField, 0, 12);
    }

    private void setUpAutomaton1DRulesSpinner(){
        Label automaton1DRulesLabel = new Label("Zasady gry jednowymiarowej:");
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 255, 128);
        automaton1DRulesSpinner.setValueFactory(valueFactory);
        automaton1DRulesSpinner.setVisible(false);
        menuPane.add(automaton1DRulesLabel, 0, 13);
        menuPane.add(automaton1DRulesSpinner, 0, 14);
    }

    private void setUpSettingAcceptButton() {
        settingAcceptButton.setText("Akceptuj ustawienia");
        settingAcceptButton.setPrefSize(NEXT_STATE_BUTTON_WIDTH, NEXT_STATE_BUTTON_HEIGHT);
        addSettingAcceptButtonListener();
        menuPane.add(settingAcceptButton, 0, 15);
    }

    private void addSelectGameComboBoxListener(){
        selectGameComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue observableValue, String oldValue, String currentValue) {
                switch (currentValue){
                    case GAME_OF_LIFE:
                        widthSlider.setVisible(true);
                        heightSlider.setVisible(true);
                        selectNeighborhoodComboBox.setVisible(true);
                        mapWrappingCheckBox.setVisible(true);
                        radiusSpinner.setVisible(true);
                        aliveRulesTextField.setVisible(true);
                        deadRulesTextField.setVisible(true);
                        automaton1DRulesSpinner.setVisible(false);
                        break;
                    case AUTOMATON_1D:
                        widthSlider.setVisible(true);
                        heightSlider.setVisible(false);
                        selectNeighborhoodComboBox.setVisible(false);
                        mapWrappingCheckBox.setVisible(false);
                        radiusSpinner.setVisible(false);
                        aliveRulesTextField.setVisible(false);
                        deadRulesTextField.setVisible(false);
                        automaton1DRulesSpinner.setVisible(true);
                        break;
                    default:
                        break;
                }

            }
        });
    }

    private void addNextStateButtonListener(){
        nextStateButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.update();
            }
        });
    }

    private void addSettingAcceptButtonListener(){
        settingAcceptButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                final String selectedValue = selectGameComboBox.getSelectionModel().getSelectedItem().toString();
                switch (selectedValue){
                    case GAME_OF_LIFE:
                        Automaton gameOfLife = createNewGameOfLife((int)widthSlider.getValue(), (int)heightSlider.getValue(),
                                mapWrappingCheckBox.isSelected(), radiusSpinner.getValue(),
                                aliveRulesTextField.getText(), deadRulesTextField.getText());
                        board = new Board(gameOfLife);
                        mainWindow.add(board.getBoardPane(), 1,0);
                        break;
                    case AUTOMATON_1D:
                        Automaton automaton1D = new Rule256(new UniformStateFactory(BinaryState.DEAD),
                                new Neighborhood1D((int)widthSlider.getValue()), (int)widthSlider.getValue(),
                                automaton1DRulesSpinner.getValue());
                        board = new Board(automaton1D);
                        mainWindow.add(board.getBoardPane(), 1,0);
                        break;
                    default:
                        break;
                }
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
            new MooreNeighborhood(radius, mapWrapping, xCellCount, yCellCount),
            xCellCount, yCellCount,
            GameOfLifeHelper.convertStringToCellRulesList(aliveRules),
            GameOfLifeHelper.convertStringToCellRulesList(deadRules));
    }
}
