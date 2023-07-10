
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.geometry.Insets;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class Main extends javafx.application.Application {

    @Override
    public void start(Stage primaryScene) throws IOException {
        Parent root = FXMLLoader.load(getClass().getResource("fisier_scene_builder.fxml"));
        //incarcam elementele create cu ajutorul sceneBuilder
        Scene scene = new Scene(root);

        GridPane gridPane = new GridPane();//creare panou
        final ProgressBar pb = new ProgressBar(0);//bara de progres
        final ProgressIndicator pi = new ProgressIndicator(0);//indicator

        pb.setOnMouseClicked(gridPane::fireEvent);
        pi.setOnKeyTyped(gridPane::fireEvent); 
        //la fiecare click sau apasare de tasta se incrementeaza bara de progres


        EventHandler<ActionEvent> event = new EventHandler<ActionEvent>() {
            public void handle(ActionEvent e) {
                countProgress(pb, pi);
            }
        };

        //Informatii formular
        Label subTitleLabel = new Label("Register here to be able to perform image rotation operations:");
        subTitleLabel.setFont(new Font(14));

        Text firstname = new Text("FirstName:");
        Text lastName = new Text("LastName:");
        Text email = new Text("Email:");

        TextField firstNameField = new TextField();
        TextField emailFiled = new TextField();
        TextField lastNameField = new TextField();
        
        //incrementam bara de progres + indicator atunci cand tastam 
        showProgress(pb, pi, firstNameField);
        showProgress(pb, pi, lastNameField);
        showProgress(pb, pi, emailFiled);

        //Label pentru categoria de varsta a utilizatorului
        Label countryLabel = new Label("Country:");
        
        //Contruire TreeView ce contine continente
        TreeItem<String> base = new TreeItem<String>("Continents");
        TreeItem root1 = new TreeItem("Europe");
        TreeItem root2 = new TreeItem("Asia");
        TreeItem root3 = new TreeItem("Africa");

        List<String> europeCountriesList = Arrays.asList("Romania", "Spain", "Bulgaria", "Sweden", "Hungary", "United Kingdom", "France",
                "Italy", "Belgium", "Greece", "Portugal", "Belarus", "Austria", "Serbia");
        for (String country : europeCountriesList) {
            root1.getChildren().add(new TreeItem(country));
        }
        
        List<String> asiaCountriesList = Arrays.asList("China", "India", "Indonesia", "Pakistan", "Bangladesh", "Japan", "Vietnam",
                "Iran", "Thailand", "Iraq", "Myanmar", "Afghanistan", "Malaysia", "Saudi Arabia");
        for (String country : asiaCountriesList) {
            root2.getChildren().add(new TreeItem(country));
        }
        
        List<String> africaCountriesList = Arrays.asList("Nigeria", "Egypt", "Dr Congo", "Angola", "Sudan", "Morocco", "Ghana",
                "Cameroon", "Niger", "Mali", "Chad");
        for (String country : africaCountriesList)
            root3.getChildren().add(new TreeItem(country));

        base.setExpanded(true);
        base.getChildren().addAll(root1, root2, root3);

        //Cream un Treview cu radacinile populate anterior
        TreeView view = new TreeView(base);
        view.setPrefHeight(100);
        view.getSelectionModel()
                .selectedItemProperty()
                .addListener((observable, oldValue, newValue) -> {
                    if (oldValue == null) {
                        countProgress(pb, pi);//afiam progresul 
                    }
                });
         //Buton ComboBox pentru specificare gen
        Label genreLabel = new Label("Genre:");
        ComboBox<String> comboBox = createGenreButton(event);
        
        //Buton ChoiceBox folost pentru alegerea varstei
        Label ageLabel = new Label("Age:");
        ChoiceBox<String> ageChoiceBox = new ChoiceBox<String>();
        ageChoiceBox.getItems().addAll("Teenager(<19 years old)", "Adult(>19 years old)");
        ageChoiceBox.setOnAction(event);

        Label accountLabel = new Label("Student?");
        ToggleGroup group3 = new ToggleGroup();    // creare grup Toggle
         
        //RadioButton folosit pentru alegerea optiunii
        RadioButton accountButton1 = new RadioButton("Yes");
        RadioButton accountButton2 = new RadioButton("No");
        createAccountButtons(event, group3, accountButton1, accountButton2);

        //adaugare hiperlink pentru o eventuala redirectionare
        Hyperlink hyperlink = new Hyperlink("Continue with Google account ?");
        hyperlink.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent event) {
                getHostServices().showDocument("https://google.ro");//redirectionare catre google
            }
        });

        //buton CheckBox pentru bifare
        CheckBox option = new CheckBox("Keep me logged in");
        option.setOnAction(event);

        //adaugare de imagine sugestiva
        ImageView imageView = addImage();

        //registerButton folosit pentru redirectionarea catre aplicatia propriu-zisa
        Button registerButton = new Button("Create new account");
        registerButton.setTextAlignment(TextAlignment.CENTER);
        registerButton.setOnAction(e -> primaryScene.setScene(scene)); 

        //Setare dimensiuni pentru layout
        addButtonsToGrid(gridPane, pb, pi, subTitleLabel, firstname, lastName, email, emailFiled, firstNameField,
                lastNameField, ageLabel, ageChoiceBox, genreLabel, countryLabel, imageView,
                accountButton1, accountButton2, comboBox, accountLabel, view, hyperlink, option, registerButton);

        Scene startScene = new Scene(gridPane); //creare panou cu pagina de inregistrare
        primaryScene.setTitle("Image Rotation"); //setare titlu ferastra
        primaryScene.setScene(startScene); //setare scena de inceput
        primaryScene.show(); //afisare scena
    }
    // buton pentru alegerea genului
    private ComboBox<String> createGenreButton(EventHandler<ActionEvent> event) {
        ComboBox<String> ageComboBox = new ComboBox<>();
        ageComboBox.getItems().add("Male");
        ageComboBox.getItems().add("Female");
        ageComboBox.setOnAction(event);//eveniment
        return ageComboBox;
    }

    private void showProgress(ProgressBar pb, ProgressIndicator pi, TextField textFiled) {
        textFiled.textProperty().addListener((observable, oldValue, newValue) ->
        {
            if (oldValue.isEmpty()) {
                countProgress(pb, pi);
            }
        });
    }

    private void addButtonsToGrid(GridPane gridPane, ProgressBar pb, ProgressIndicator pi, Label subTitleLabel,
                                  Text firstName, Text lastName, Text email, TextField firstNameField, TextField lastNameField,
                                  TextField emailField, Label age, ChoiceBox<String> ageChoiceBox, Label genreLabel,
                                  Label countryLabel, ImageView imageView, RadioButton accountButton1, RadioButton accountButton2,
                                  ComboBox<String> comboBox, Label accountLabel, TreeView view, Hyperlink hyperlink,
                                  CheckBox option, Button registerButton) {

        gridPane.setMinSize(200, 200);

        //Setare margini pentru layout
        gridPane.setPadding(new Insets(20, 20, 20, 20));

        //Setare spatiere intre layout si panou
        gridPane.setVgap(1);
        gridPane.setHgap(1);

        //Setare aliniere layout
        gridPane.setAlignment(Pos.BOTTOM_RIGHT);

        //Aranjare elemente in layout
        gridPane.add(subTitleLabel, 1, 0); //informatii formular

        gridPane.add(firstName, 0, 3); //label nume
        gridPane.add(firstNameField, 1, 3); //text field nume

        gridPane.add(lastName, 0, 5);  //label prenume
        gridPane.add(lastNameField, 1, 5); //text field prenume

        gridPane.add(email, 0, 6);// label email
        gridPane.add(emailField, 1, 6);// text field email

        gridPane.add(age, 0, 7);//label varsta
        gridPane.add(ageChoiceBox, 1, 7);
        
        gridPane.add(genreLabel, 0, 8);//label gen
        gridPane.add(comboBox, 1, 8);
        
        gridPane.add(accountLabel, 0, 9); //label pentru interogarea despre contul de student
        gridPane.add(accountButton1, 1, 9); //butonanele pozitionate pe panou corespunzator label ului
        gridPane.add(accountButton2, 1, 10); 

        gridPane.add(countryLabel, 0, 11); //label Country
        gridPane.add(view, 1, 11); //TreeView cu optiunile 

        gridPane.add(hyperlink, 1, 12); //hyperlink cont google
        gridPane.add(option, 1, 13); //checkbox exprimare optiune

        gridPane.add(pb, 2, 18); //bara de progres
        gridPane.add(pi, 2, 19);
        gridPane.add(imageView, 2, 17);//indicator de progres

        gridPane.add(registerButton, 1, 18); //buton inregistrare
    }

    private ImageView addImage() throws FileNotFoundException {
    	//functie ce citeste o imagine si o plaseaza corespunzator
        InputStream stream = new FileInputStream("src/image.png");
        Image image = new Image(stream);
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        return imageView;
    }

    private void createAccountButtons(EventHandler<ActionEvent> event, ToggleGroup group, RadioButton accountButton1,
                                      RadioButton accountButton2) {

        accountButton1.setToggleGroup(group);//crearea butoanelor
        accountButton2.setToggleGroup(group);
        
        accountButton1.setOnAction(event);//incrementam elementele vizuale odata cu inceperea operatiei de tastare
        accountButton2.setOnAction(event);
    }

    private void countProgress(ProgressBar pb, ProgressIndicator pi) {
        pb.setProgress(pb.getProgress() + 0.125);
        pi.setProgress(pi.getProgress() + 0.125);
    }

    public static void main(String[] args) {
        launch(args);
    }
}