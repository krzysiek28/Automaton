package gui;

import automaton.Automaton;
import cell.states.BinaryState;
import cell.states.StateFactory;
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
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import neighbor.CellNeighborhood;
import neighbor.MooreNeighborhood;
import neighbor.Neighborhood1D;
import neighbor.VonNeumanNeighborhood;

public class GraphicUserInterface extends Application {

    private static final int MAIN_APP_HEIGHT = 700;

    private static final int MAIN_APP_WIDTH = 1350;

    private static final int BUTTON_HEIGHT = 50;

    private static final int BUTTON_WIDTH = 200;

    private static final String GAME_OF_LIFE = "Gra w życie";

    private static final String AUTOMATON_1D = "Automaty jednowymiarowe";

    private static final String MOORE_NEIGHBORHOOD = "Moore Neighborhood";

    private static final String VON_NEUMAN_NEIGHBORHOOD = "Von Neuman Neighborhood";

    private static int xCellCount = 10;

    private static int yCellCount = 10;

    private GridPane mainWindow = new GridPane();

    private GridPane menuPane = new GridPane();

    private Board board;

    private ComboBox selectGameComboBox = new ComboBox();

    private VBox boardWidthVBox = new VBox();

    private VBox boardHeightVBox = new VBox();

    private VBox radiusVBox = new VBox();

    private VBox aliveRulesVBox = new VBox();

    private VBox deadRulesVBox = new VBox();

    private VBox automaton1DRulesVBox = new VBox();

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

    private Button clearBoardButton = new Button();

    private HBox structurePanelHBox = new HBox();

    private HBox bottomPanelHBox = new HBox();

    private Button addBlockButton = new Button();

    private Button addKiteButton = new Button();

    private Button addBoatButton = new Button();

    private Button addGunButton = new Button();

    public GraphicUserInterface(){
    }

    private void setUpLayout(Board board){
        mainWindow.setPrefSize(MAIN_APP_WIDTH, MAIN_APP_HEIGHT);
        GridPane.setMargin(mainWindow, new Insets(10,10,10,10));
        setUpNextStateButton();
        setUpMenuPane();
        mainWindow.add(structurePanelHBox,1,1);
        setUpStructurePanel();
        mainWindow.add(bottomPanelHBox, 1, 2);
        mainWindow.add(board.getBoardPane(), 1,0);
    }

    private void setUpNextStateButton(){
        nextStateButton.setText("Nowy stan");
        nextStateButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        addNextStateButtonListener();
        bottomPanelHBox.getChildren().add(nextStateButton);
//        mainWindow.add(nextStateButton, 1, 1);
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
        menuPane.add(mapWrappingCheckBox, 0, 4);
        setUpRadiusSpinner();
        setUpAliveRulesTextField();
        setUpDeadRulesTextField();
        setUpAutomaton1DRulesSpinner();
        setUpSettingAcceptButton();
        setUpClearBoardButton();
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
        boardWidthVBox.getChildren().addAll(widthLabel, widthSlider);
        menuPane.add(boardWidthVBox, 0, 1);
    }

    private void setUpBoardHeight(){
        Label heightLabel = new Label("Wysokość:");
        heightSlider = createAndSetUpSlider();
        boardHeightVBox.getChildren().addAll(heightLabel, heightSlider);
        menuPane.add(boardHeightVBox, 0, 2);
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
        selectNeighborhoodComboBox.getItems().add(MOORE_NEIGHBORHOOD);
        selectNeighborhoodComboBox.getItems().add(VON_NEUMAN_NEIGHBORHOOD);
        selectNeighborhoodComboBox.getSelectionModel().selectFirst();
        menuPane.add(selectNeighborhoodComboBox, 0, 3);
    }

    private void setUpRadiusSpinner(){
        Label radiusLabel = new Label("Promień:");
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 10, 1);
        radiusSpinner.setValueFactory(valueFactory);
        radiusVBox.getChildren().addAll(radiusLabel, radiusSpinner);
        menuPane.add(radiusVBox, 0, 5);
    }

    private void setUpAliveRulesTextField(){
        Label aliveRulesLabel = new Label("Zasady przeżywania komórki:");
        aliveRulesTextField.setText("2,3");
        aliveRulesVBox.getChildren().addAll(aliveRulesLabel, aliveRulesTextField);
        menuPane.add(aliveRulesVBox, 0, 6);
    }

    private void setUpDeadRulesTextField(){
        Label deadRulesLabel = new Label("Zasady ożywiania komórki:");
        deadRulesTextField.setText("3");
        deadRulesVBox.getChildren().addAll(deadRulesLabel, deadRulesTextField);
        menuPane.add(deadRulesVBox, 0, 7);
    }

    private void setUpAutomaton1DRulesSpinner(){
        Label automaton1DRulesLabel = new Label("Zasady gry jednowymiarowej:");
        SpinnerValueFactory<Integer> valueFactory =
                new SpinnerValueFactory.IntegerSpinnerValueFactory(1, 255, 128);
        automaton1DRulesSpinner.setValueFactory(valueFactory);
        automaton1DRulesVBox.getChildren().addAll(automaton1DRulesLabel, automaton1DRulesSpinner);
        automaton1DRulesVBox.setVisible(false);
        menuPane.add(automaton1DRulesVBox, 0, 2);
    }

    private void setUpSettingAcceptButton() {
        settingAcceptButton.setText("Akceptuj ustawienia");
        settingAcceptButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        addSettingAcceptButtonListener();
        menuPane.add(settingAcceptButton, 0, 9);
    }

    private void setUpClearBoardButton() {
        clearBoardButton.setText("Wyczyść planszę");
        clearBoardButton.setPrefSize(BUTTON_WIDTH, BUTTON_HEIGHT);
        addClearBoardButtonListener();
        bottomPanelHBox.getChildren().add(clearBoardButton);
//        menuPane.add(clearBoardButton, 0, 10);
    }

    private void setUpStructurePanel(){
        addBlockButton();
        addKiteButton();
        addBoatButton();
        addGunButton();
    }

    private void addBlockButton(){
        addBlockButton.setText("Klocek");
        addBlockButtonListener(addBlockButton);
        structurePanelHBox.getChildren().add(addBlockButton);
    }

    private void addKiteButton(){
        addKiteButton.setText("Latawiec");
        addKiteButtonListener(addKiteButton);
        structurePanelHBox.getChildren().add(addKiteButton);
    }

    private void addBoatButton(){
        addBoatButton.setText("Łódź");
        addBoatButtonListener(addBoatButton);
        structurePanelHBox.getChildren().add(addBoatButton);
    }

    private void addGunButton(){
        addGunButton.setText("Działo");
        addGunButtonListener(addGunButton);
        structurePanelHBox.getChildren().add(addGunButton);
    }

    private void addBlockButtonListener(Button addBlockButton) {
        addBlockButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.addStructure(Structures.BLOCK);
            }
        });
    }

    private void addKiteButtonListener(Button addKiteButton) {
        addKiteButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.addStructure(Structures.KITE);
            }
        });
    }

    private void addBoatButtonListener(Button addBoatButton) {
        addBoatButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.addStructure(Structures.BOAT);
            }
        });
    }

    private void addGunButtonListener(Button addGunButton) {
        addGunButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.addStructure(Structures.GUN);
            }
        });
    }

    private void addSelectGameComboBoxListener(){
        selectGameComboBox.valueProperty().addListener(new ChangeListener<String>() {
            @Override public void changed(ObservableValue observableValue, String oldValue, String currentValue) {
                switch (currentValue){
                    case GAME_OF_LIFE:
//                        settingAcceptButton.setDisable(false);
                        boardWidthVBox.setVisible(true);
                        boardHeightVBox.setVisible(true);
                        selectNeighborhoodComboBox.setVisible(true);
                        mapWrappingCheckBox.setVisible(true);
                        radiusVBox.setVisible(true);
                        aliveRulesVBox.setVisible(true);
                        deadRulesVBox.setVisible(true);
                        automaton1DRulesVBox.setVisible(false);
                        addBlockButton.setVisible(true);
                        addKiteButton.setVisible(true);
                        addBoatButton.setVisible(true);
                        addGunButton.setVisible(true);
                        break;
                    case AUTOMATON_1D:
//                        settingAcceptButton.setDisable(true);
                        boardWidthVBox.setVisible(true);
                        boardHeightVBox.setVisible(false);
                        selectNeighborhoodComboBox.setVisible(false);
                        mapWrappingCheckBox.setVisible(false);
                        radiusVBox.setVisible(false);
                        aliveRulesVBox.setVisible(false);
                        deadRulesVBox.setVisible(false);
                        automaton1DRulesVBox.setVisible(true);
                        addBlockButton.setVisible(false);
                        addKiteButton.setVisible(false);
                        addBoatButton.setVisible(false);
                        addGunButton.setVisible(false);
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
                        Automaton automaton1D = new Rule256(new StateFactory(BinaryState.DEAD),
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

    private void addClearBoardButtonListener(){
        clearBoardButton.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                board.clear();
            }
        });
    }

    public void start(Stage stage) {
        Automaton automaton = new GameOfLife(
                new StateFactory(BinaryState.DEAD),
                new MooreNeighborhood(1, false, xCellCount, yCellCount), xCellCount, yCellCount,
                GameOfLifeHelper.convertStringToCellRulesList("3,2"),
                GameOfLifeHelper.convertStringToCellRulesList("3"));
        board = new Board(automaton);

        setUpLayout(board);

        stage.setTitle("Automaty komórkowe");
        stage.setScene(new Scene(mainWindow));
        stage.show();
    }

    public Automaton createNewGameOfLife(int xCellCount, int yCellCount, boolean mapWrapping, int radius,
         String aliveRules, String deadRules){
        CellNeighborhood neighborhood = resolveNeighborhood2D(xCellCount, yCellCount, mapWrapping, radius);
        return new GameOfLife(
            new StateFactory(BinaryState.DEAD),
            neighborhood, xCellCount, yCellCount,
            GameOfLifeHelper.convertStringToCellRulesList(aliveRules),
            GameOfLifeHelper.convertStringToCellRulesList(deadRules));
    }

    private CellNeighborhood resolveNeighborhood2D(int xCellCount, int yCellCount, boolean mapWrapping, int radius) {
        final String selectedValue = selectNeighborhoodComboBox.getSelectionModel().getSelectedItem().toString();
        switch (selectedValue){
            case MOORE_NEIGHBORHOOD:
                return new MooreNeighborhood(radius, mapWrapping, xCellCount, yCellCount);
            case VON_NEUMAN_NEIGHBORHOOD:
                return new VonNeumanNeighborhood(radius, mapWrapping, xCellCount, yCellCount);
            default:
                throw new IllegalArgumentException("Cannot resolve neighborhood, selected token: " + selectedValue);
        }
    }
}
