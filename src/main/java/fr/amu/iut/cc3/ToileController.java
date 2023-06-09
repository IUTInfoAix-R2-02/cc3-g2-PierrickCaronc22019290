package fr.amu.iut.cc3;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    private Map<String, Circle> points = new HashMap<>();
    private List<Line> lines = new ArrayList<>();

    public TextField comp1;
    public TextField comp2;
    public TextField comp3;
    public TextField comp4;
    public TextField comp5;
    public TextField comp6;
    public Pane toile;

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        comp1.setOnKeyPressed(event -> ActionComp1(event));
        comp2.setOnKeyPressed(event -> ActionComp2(event));
        comp3.setOnKeyPressed(event -> ActionComp3(event));
        comp4.setOnKeyPressed(event -> ActionComp4(event));
        comp5.setOnKeyPressed(event -> ActionComp5(event));
        comp6.setOnKeyPressed(event -> ActionComp6(event));
    }

    int getXRadarChart(double value, int axe) {
        return (int) (rayonCercleExterieur + Math.cos(Math.toRadians(angleDepart - (axe - 1) * angleEnDegre)) * rayonCercleExterieur
                * (value / noteMaximale));
    }

    int getYRadarChart(double value, int axe) {
        return (int) (rayonCercleExterieur - Math.sin(Math.toRadians(angleDepart - (axe - 1) * angleEnDegre)) * rayonCercleExterieur
                * (value / noteMaximale));
    }

    public void ActionComp1(KeyEvent event) {
        handleEnterKey(event, comp1, 1);
    }

    public void ActionComp2(KeyEvent event) {
        handleEnterKey(event, comp2, 2);
    }

    public void ActionComp3(KeyEvent event) {
        handleEnterKey(event, comp3, 3);
    }

    public void ActionComp4(KeyEvent event) {
        handleEnterKey(event, comp4, 4);
    }

    public void ActionComp5(KeyEvent event) {
        handleEnterKey(event, comp5, 5);
    }

    public void ActionComp6(KeyEvent event) {
        handleEnterKey(event, comp6, 6);
    }

    private void handleEnterKey(KeyEvent event, TextField field, int axe) {
        if (event.getCode() == KeyCode.ENTER) {
            String fieldValue = field.getText();
            try {
                double note = Double.parseDouble(fieldValue);
                if (note > noteMaximale) {
                    showError("La note ne doit pas dépasser " + noteMaximale);
                    return;
                }
                drawPoint(note, axe);
            } catch (NumberFormatException e) {
                showError("Valeur de note invalide !");
            }
        }
    }

    private void drawPoint(double note, int axe) {
        int x = getXRadarChart(note, axe);
        int y = getYRadarChart(note, axe);

        Circle point = new Circle(10, Color.BLACK);
        point.setCenterX(x);
        point.setCenterY(y);

        String key = "point_" + axe;
        Circle existingPoint = points.get(key);
        if (existingPoint != null) {
            toile.getChildren().remove(existingPoint);
        }
        points.put(key, point);
        toile.getChildren().add(point);
    }

    private void showError(String message) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Erreur");
        alert.setHeaderText(null);
        alert.setContentText(message);
        alert.showAndWait();
    }

    @FXML
    private void tracerAction() {
        // Tracer les lignes entre les points
        clearLines();
        for (int i = 1; i <= 6; i++) {
            String currentKey = "point_" + i;
            String nextKey = "point_" + (i == 6 ? 1 : i + 1);

            Circle currentPoint = points.get(currentKey);
            Circle nextPoint = points.get(nextKey);

            if (currentPoint != null && nextPoint != null) {
                Line line = new Line(currentPoint.getCenterX(), currentPoint.getCenterY(),
                        nextPoint.getCenterX(), nextPoint.getCenterY());
                line.setStroke(Color.BLUE);
                lines.add(line);
                toile.getChildren().add(line);
            }
        }
    }

    private void clearLines() {
        for (Line line : lines) {
            toile.getChildren().remove(line);
        }
        lines.clear();
    }

    @FXML
    private void viderAction() {
        // Effacer les valeurs saisies
        comp1.clear();
        comp2.clear();
        comp3.clear();
        comp4.clear();
        comp5.clear();
        comp6.clear();

        // Effacer les points dessinés
        for (Circle point : points.values()) {
            toile.getChildren().remove(point);
        }
        points.clear();

        // Effacer les lignes tracées
        clearLines();

        // Effacer les messages d'erreur
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Information");
        alert.setHeaderText(null);
        alert.setContentText("Données effacées !");
        alert.showAndWait();
    }
}
