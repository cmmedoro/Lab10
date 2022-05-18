package it.polito.tdp.rivers.model;

import java.time.LocalDate;

public class Dati {
	//classe per memorizzare i risultati di una query
	private River r;
	private LocalDate firstDay;
	private LocalDate lastDay;
	private int numMeasurements;
	private double avgFlow;
	public Dati(River r, LocalDate firstDay, LocalDate lastDay, int numMeasurements, double avgFlow) {
		super();
		this.r = r;
		this.firstDay = firstDay;
		this.lastDay = lastDay;
		this.numMeasurements = numMeasurements;
		this.avgFlow = avgFlow;
	}
	public River getR() {
		return r;
	}
	public void setR(River r) {
		this.r = r;
	}
	public LocalDate getFirstDay() {
		return firstDay;
	}
	public void setFirstDay(LocalDate firstDay) {
		this.firstDay = firstDay;
	}
	public LocalDate getLastDay() {
		return lastDay;
	}
	public void setLastDay(LocalDate lastDay) {
		this.lastDay = lastDay;
	}
	public int getNumMeasurements() {
		return numMeasurements;
	}
	public void setNumMeasurements(int numMeasurements) {
		this.numMeasurements = numMeasurements;
	}
	public double getAvgFlow() {
		return avgFlow;
	}
	public void setAvgFlow(double avgFlow) {
		this.avgFlow = avgFlow;
	}

}
