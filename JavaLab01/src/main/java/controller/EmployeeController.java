package controller;

import interfaces.EmloyeeDAO;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import model.Employee;

import java.sql.*;
import java.util.List;
import java.util.Optional;

public class EmployeeController implements EmloyeeDAO {
    @FXML
    private MenuItem mi_exit;
    @FXML
    private TableView<Employee> tbl_employee;
    @FXML
    private TableColumn<Employee, Long> col_id;
    @FXML
    private TableColumn<Employee, String> col_name;
    @FXML
    private TableColumn<Employee, String> col_email;
    @FXML
    private TableColumn<Employee, String> col_salary;
    @FXML
    private TextField nameAdd;
    @FXML
    private TextField emailAdd;
    @FXML
    private TextField salaryAdd;
    @FXML
    private TextField idDel;
    @FXML
    private TextField idMod;
    @FXML
    private TextField nameMod;
    @FXML
    private TextField emailMod;
    @FXML
    private TextField salaryMod;
    @FXML
    private Text nullText;
    @FXML
    void addData(ActionEvent event) {

        System.out.println(salaryAdd.getText());
        if((nameAdd.getText()).equals("")||(emailAdd.getText()).equals("")||(salaryAdd.getText()=="")){
            nullText.setText("Pola: Imię, Email oraz Wynagrodzenie są wymagane");
        }
        else{
            save(new Employee(nameAdd.getText(),emailAdd.getText(),Double.parseDouble(salaryAdd.getText())));
            nullText.setText("");
        }

    }
    @FXML
    void delDel(ActionEvent event) {
        try{
            employeesFiltered.clear();
            dbconn();
            resultSet = statement.executeQuery("select * from employees where id="+Integer.parseInt(idDel.getText()));
            while(resultSet.next()){
                delete(new Employee(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getString("email"),resultSet.getDouble("salary")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @FXML
    void openAbout(ActionEvent event){
        try {
            FXMLLoader fxmlLoader = new FXMLLoader(getClass().getResource("/aboutView.fxml"));
            Parent root2 = fxmlLoader.load();
            Stage aboutStage = new Stage();
            aboutStage.setTitle("About");
            aboutStage.setScene(new Scene(root2,640,480));
            aboutStage.show();
        } catch (Exception e){
            e.printStackTrace();
        }
    }
    @FXML
    void clearFields() {
        nameMod.setText("");
        emailMod.setText("");
        salaryMod.setText("");
    }
    @FXML
    void modifyData(ActionEvent event) {
        modify(Long.parseLong(idMod.getText()),nameMod.getText(),emailMod.getText(),Double.parseDouble(salaryMod.getText()));
    }
    @FXML
    void findDataDel(ActionEvent event) {
        findOne(Integer.parseInt(idDel.getText()));
    }
    @FXML
    void findDataMod(ActionEvent event) {
        findOne(Integer.parseInt(idMod.getText()));
    }
    @FXML
    void resetView(ActionEvent event) {
        tbl_employee.setItems(employees);
    }
    @FXML
    void exitAction(ActionEvent event) {
        System.exit(0);
    }
    private Connection connector;
    private Statement statement;
    private ResultSet resultSet;

    private void addDataToTable(){
        // konfiguracja wartości z modelu do tabeli
        col_id.setCellValueFactory(new PropertyValueFactory<Employee, Long>("id"));
        col_name.setCellValueFactory(new PropertyValueFactory<Employee, String>("name"));
        col_email.setCellValueFactory(new PropertyValueFactory<Employee, String>("email"));
        col_salary.setCellValueFactory(new PropertyValueFactory<Employee, String>("salary"));
        tbl_employee.setItems(employees);
    }

    private void viewFiltered(){
        tbl_employee.setItems(employeesFiltered);
    }

    private ObservableList<Employee> employees = FXCollections.observableArrayList();
    private ObservableList<Employee> employeesFiltered = FXCollections.observableArrayList();


    public void initialize(){
        findAll();
        addDataToTable();
    }

    public void dbconn(){
        try{
            connector = DriverManager.getConnection("jdbc:mysql://localhost:3306/java?useLegacyDatetimeCode=false&serverTimezone=UTC","root","root");
            statement = connector.createStatement();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }

    @Override
    public Optional findOne(Integer id) {
        try{
            employeesFiltered.clear();
            dbconn();
            resultSet = statement.executeQuery("select * from employees where id="+id);
            while(resultSet.next()){
                nameMod.setText(resultSet.getString("name"));
                emailMod.setText(resultSet.getString("email"));
                salaryMod.setText(String.valueOf(resultSet.getDouble("salary")));
                employeesFiltered.add(new Employee(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getString("email"),resultSet.getDouble("salary")));
            }
            viewFiltered();
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return Optional.empty();
    }

    @Override
    public List findAll() {
        try{
            dbconn();
            resultSet = statement.executeQuery("select * from employees");
            while(resultSet.next()){
                employees.add(new Employee(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getString("email"),resultSet.getDouble("salary")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public Optional findByName(String name) {
        try{
            dbconn();
            resultSet = statement.executeQuery("select * from employees where name like"+name);
            while(resultSet.next()){
                employees.add(new Employee(resultSet.getLong("id"),resultSet.getString("name"),resultSet.getString("email"),resultSet.getDouble("salary")));
            }
        }catch (Exception ex){
            ex.printStackTrace();
        }
        return null;
    }

    @Override
    public void delete(Employee employee) {
        try{
            employees.clear();
            dbconn();
            long id = employee.getId();
            statement = connector.prepareStatement("DELETE FROM employees " +
                    "WHERE id=?");
            ((PreparedStatement) statement).setInt(1,(int)id);
            ((PreparedStatement) statement).execute();
            findAll();
            addDataToTable();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
    @Override
    public void save(Employee employee) {
            try{
                employees.clear();
                dbconn();
                String name = employee.getName();
                String email = employee.getEmail();
                double salary = employee.getSalary();
                statement = connector.prepareStatement("INSERT INTO employees " +
                        "VALUES (NULL,?,?,?)");
                ((PreparedStatement) statement).setString(1,name);
                ((PreparedStatement) statement).setString(2,email);
                ((PreparedStatement) statement).setDouble(3,salary);
                ((PreparedStatement) statement).execute();
                findAll();
                addDataToTable();
            }catch (Exception ex){
                ex.printStackTrace();
            }
    }

    @Override
    public void modify(Long id, String name, String email, Double salary) {
        try{
            employees.clear();
            dbconn();
            statement = connector.prepareStatement("UPDATE employees " +
                    "SET name=?, email=?, salary=?"+
                    "WHERE id=?");
            ((PreparedStatement) statement).setString(1,name);
            ((PreparedStatement) statement).setString(2,email);
            ((PreparedStatement) statement).setDouble(3,salary);
            ((PreparedStatement) statement).setLong(4,id);
            ((PreparedStatement) statement).execute();
            findAll();
            addDataToTable();
        }catch (Exception ex){
            ex.printStackTrace();
        }
    }
}
