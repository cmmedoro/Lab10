/**
 * Sample Skeleton for 'Scene.fxml' Controller Class
 */

package it.polito.tdp.rivers;

import java.net.URL;
import java.util.ResourceBundle;

import it.polito.tdp.rivers.model.Dati;
import it.polito.tdp.rivers.model.Model;
import it.polito.tdp.rivers.model.RisultatiSimulatore;
import it.polito.tdp.rivers.model.River;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class FXMLController {
	
	private Model model;

    @FXML // ResourceBundle that was given to the FXMLLoader
    private ResourceBundle resources;

    @FXML // URL location of the FXML file that was given to the FXMLLoader
    private URL location;

    @FXML // fx:id="boxRiver"
    private ComboBox<River> boxRiver; // Value injected by FXMLLoader

    @FXML // fx:id="txtStartDate"
    private TextField txtStartDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtEndDate"
    private TextField txtEndDate; // Value injected by FXMLLoader

    @FXML // fx:id="txtNumMeasurements"
    private TextField txtNumMeasurements; // Value injected by FXMLLoader

    @FXML // fx:id="txtFMed"
    private TextField txtFMed; // Value injected by FXMLLoader

    @FXML // fx:id="txtK"
    private TextField txtK; // Value injected by FXMLLoader

    @FXML // fx:id="btnSimula"
    private Button btnSimula; // Value injected by FXMLLoader

    @FXML // fx:id="txtResult"
    private TextArea txtResult; // Value injected by FXMLLoader
    
    @FXML
    void loadData(ActionEvent event) {
    	this.txtStartDate.clear();
    	this.txtEndDate.clear();
    	this.txtNumMeasurements.clear();
    	this.txtFMed.clear();
    	//prendo il fiume dalla comboBox
    	River r = this.boxRiver.getValue();
    	if(r == null) {
    		this.txtResult.setText("Devi selezionare un fiume!");
    		return;
    	}
    	//se sono qui vuol dire che posso riempire automaticamente gli altri campi
    	Dati ris = this.model.getDatiFiume(r);
    	this.model.loadFlows(r);
    	if(ris == null) {
    		this.txtResult.setText("Dati per questo fiume non trovati!");
    		return;
    	}else {
    		//Se sono qui vuol dire che è tutto OK
    		this.txtStartDate.setText(ris.getFirstDay().toString());
    		this.txtEndDate.setText(ris.getLastDay().toString());
    		this.txtNumMeasurements.setText(Integer.toString(ris.getNumMeasurements()));
    		this.txtFMed.setText(Double.toString(ris.getAvgFlow()));
    		return;
    	}

    }

    @FXML
    void simulation(ActionEvent event) {
    	this.txtResult.clear();
    	River r = this.boxRiver.getValue();
    	if(r == null) {
    		this.txtResult.setText("Devi selezionare un fiume!");
    		return;
    	}
    	double k;
    	try {
    		k = Double.parseDouble(this.txtK.getText());
    	}catch(NumberFormatException e) {
    		this.txtResult.setText("Non hai inserito un valore numerico per k!");
    		return;
    	}
    	if(k <= 0) {
    		this.txtResult.setText("Devi inserire un valore positivo, maggiore di 0");
    		return;
    	}
    	//se sono qua posso effettuare la simulazione
    	this.model.inizializza(r, k);
    	RisultatiSimulatore rs = this.model.simulazione();
    	if(rs==null) {
    		this.txtResult.setText("Problema: la simulazione non ha prodotto alcun risultato");
    		return;
    	}
    	else {
    		this.txtResult.appendText("Numero di giorni in cui non è stata garantita la capienza minima: "+rs.getNumGiorni()+"\n");
    		this.txtResult.appendText("Capacità media del bacino: "+rs.getOccupazioneMedia());
    	}
    }

    @FXML // This method is called by the FXMLLoader when initialization is complete
    void initialize() {
        assert boxRiver != null : "fx:id=\"boxRiver\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtStartDate != null : "fx:id=\"txtStartDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtEndDate != null : "fx:id=\"txtEndDate\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtNumMeasurements != null : "fx:id=\"txtNumMeasurements\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtFMed != null : "fx:id=\"txtFMed\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtK != null : "fx:id=\"txtK\" was not injected: check your FXML file 'Scene.fxml'.";
        assert btnSimula != null : "fx:id=\"btnSimula\" was not injected: check your FXML file 'Scene.fxml'.";
        assert txtResult != null : "fx:id=\"txtResult\" was not injected: check your FXML file 'Scene.fxml'.";
    }
    
    public void setModel(Model model) {
    	this.model = model;
    	this.boxRiver.getItems().clear();
    	this.boxRiver.getItems().addAll(this.model.getAllRivers());
    }
}
