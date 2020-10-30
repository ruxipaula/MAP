package View;

import Controller.Controller;
import Exceptions.MyException;
import Model.ADT.MyIDictionary;
import Model.ADT.MyIHeap;
import Model.ADT.MyIStack;
import Model.PrgState;
import Model.Stmt.IStmt;
import Model.Value.Value;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;

import java.io.BufferedReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

public class RunPrgController implements Initializable {
    private Controller controller;

    @FXML
    private TableView<Map.Entry<Integer, Value>> heapTableView;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Integer> heapAddrColumn;

    @FXML
    private TableColumn<Map.Entry<Integer, Value>, Value> heapValColumn;

    @FXML
    private TableView<Map.Entry<String, Value>> symTableView;

    @FXML
    private TableColumn<Map.Entry<String, Value>, String> symVarColumn;

    @FXML
    private TableColumn<Map.Entry<String, Value>, Value> symValColumn;

    @FXML
    private ListView<Value> outListView;

    @FXML
    private ListView<String> fileListView;

    @FXML
    private ListView<Integer> prgStateListView;

    @FXML
    private ListView<String> exeStackListView;

    @FXML
    private TextField nrOfPrgStatesTextField;

    @FXML
    private Button oneStepButton;

    void setController(Controller controller) {
        this.controller = controller;
        populateIdentifiers();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        heapAddrColumn.setCellValueFactory(p -> new SimpleIntegerProperty(p.getValue().getKey()).asObject());
        heapValColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        symVarColumn.setCellValueFactory(p -> new SimpleStringProperty(p.getValue().getKey() + ""));
        symValColumn.setCellValueFactory(p -> new SimpleObjectProperty<>(p.getValue().getValue()));

        prgStateListView.setOnMouseClicked(mouseEvent -> changePrgState(getCurrentPrgState()));

        oneStepButton.setOnAction(actionEvent -> oneStepExec());
    }

    private List<Integer> getPrgStateIds(List<PrgState> prgStateList) {
        return prgStateList.stream().map(PrgState::getId).collect(Collectors.toList());
    }

    private void populateIdentifiers() {
        List<PrgState> prgStates = controller.getRepo().getPrgList();
        prgStateListView.setItems(FXCollections.observableList(getPrgStateIds(prgStates)));

        nrOfPrgStatesTextField.setText("" + prgStates.size());
    }

    private void oneStepExec() {
        if(controller == null) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No program was selected!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        boolean prgStateLeft = controller.getRepo().getPrgList().isEmpty();
        if (prgStateLeft) {
            Alert alert = new Alert(Alert.AlertType.ERROR, "No more programs to execute!", ButtonType.OK);
            alert.showAndWait();
            return;
        }

        try {
            controller.oneStep();
        }catch(MyException exc) {
            Alert alert = new Alert(Alert.AlertType.ERROR, exc.getMessage(), ButtonType.OK);
            alert.showAndWait();
            return;
        }

        changePrgState(getCurrentPrgState());
        populateIdentifiers();
    }

    private void changePrgState(PrgState currentPrgState) {
        if (currentPrgState == null)
            return;
        else {
            populateExeStack(currentPrgState);
            populateSymTable(currentPrgState);
            populateFileTable(currentPrgState);
            populateOutTable(currentPrgState);
            populateHeapTable(currentPrgState);
        }
    }

    private void populateSymTable(PrgState state) {
        MyIDictionary<String, Value> symTable = state.getSymTable();
        List<Map.Entry<String, Value>> symList = new ArrayList<>(symTable.getContent().entrySet());
        symTableView.setItems(FXCollections.observableList(symList));
        symTableView.refresh();
    }

    private void populateHeapTable(PrgState state) {
        MyIHeap<Integer, Value> heapTable = state.getHeap();
        List<Map.Entry<Integer, Value>> heapList = new ArrayList<>(heapTable.getContent().entrySet());
        heapTableView.setItems(FXCollections.observableList(heapList));
        heapTableView.refresh();
    }

    private void populateFileTable(PrgState state) {
        MyIDictionary<String, BufferedReader> fileTable = state.getFileTable();
        List<String> fileList = new ArrayList<>();
        for (Map.Entry<String, BufferedReader> entry : fileTable.getContent().entrySet())
            fileList.add(entry.getKey());
        ObservableList<String> files = FXCollections.observableArrayList(fileList);
        fileListView.setItems(files);
        fileListView.refresh();
    }

    private void populateOutTable(PrgState state) {
        ObservableList<Value> outTable = FXCollections.observableArrayList(state.getOutList().getContent());
        outListView.setItems(outTable);
        outListView.refresh();
    }

    private void populateExeStack(PrgState state) {
        MyIStack<IStmt> exeStack = state.getStk();

        List<String> exeStackList = new ArrayList<>();
        for(IStmt s : exeStack.getStack()){
            exeStackList.add(0,s.toString());
        }
        exeStackListView.setItems(FXCollections.observableList(exeStackList));
        exeStackListView.refresh();
    }

    private PrgState getCurrentPrgState() {
        if(prgStateListView.getSelectionModel().getSelectedIndex() == -1)
            return null;

        int currentId = prgStateListView.getSelectionModel().getSelectedItem();
        return controller.getRepo().getPrgState(currentId);
    }

}
