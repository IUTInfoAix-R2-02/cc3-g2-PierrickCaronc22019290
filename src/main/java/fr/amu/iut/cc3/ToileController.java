package fr.amu.iut.cc3;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.net.URL;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

public class ToileController implements Initializable {

    private static int rayonCercleExterieur = 200;
    private static int angleEnDegre = 60;
    private static int angleDepart = 90;
    private static int noteMaximale = 20;

    private Map<String, Circle> points = new HashMap<>();

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
                    System.out.println("La note ne doit pas dépasser " + noteMaximale);
                    return;
                }
                drawPoint(note, axe);
            } catch (NumberFormatException e) {
                System.out.println("Valeur de note invalide !");
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
    @FXML
    private void tracerAction() {
        // Logique pour l'action du bouton "Tracer"
        System.out.println("Action du bouton Tracer");
    }

    @FXML
    private void viderAction() {
        // Logique pour l'action du bouton "Vider"
        System.out.println("Action du bouton Vider");
    }

}
