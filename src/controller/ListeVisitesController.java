package controller;

import dao.VisiteDAO;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.TableCell;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import model.Visite;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class ListeVisitesController {

    @FXML
    private TableView<Visite> tableVisites;

    @FXML
    private TableColumn<Visite, String> colDate, colMotif, colVeto;

    @FXML
    private Label animalNameLabel;

    private String nomAnimal;

    public void setNomAnimal(String nomAnimal) {
        this.nomAnimal = nomAnimal;

        // Set the subtitle with the animal's name
        animalNameLabel.setText("pour " + nomAnimal);

        // Load the visits for this animal
        tableVisites.setItems(FXCollections.observableArrayList(
                VisiteDAO.getVisitesPourAnimal(nomAnimal)
        ));
    }

    @FXML
    public void initialize() {
        colDate.setCellValueFactory(cell -> cell.getValue().dateProperty());
        colMotif.setCellValueFactory(cell -> cell.getValue().motifProperty());
        colVeto.setCellValueFactory(cell -> cell.getValue().nomVetoProperty());

        // Format the date to a more readable format
        colDate.setCellFactory(column -> new TableCell<Visite, String>() {
            @Override
            protected void updateItem(String item, boolean empty) {
                super.updateItem(item, empty);

                if (item == null || empty) {
                    setText(null);
                } else {
                    try {
                        // Assume the date is in format "dd/MM/yyyy"
                        setText(item);
                    } catch (Exception e) {
                        setText(item);
                    }
                }
            }
        });
    }

}