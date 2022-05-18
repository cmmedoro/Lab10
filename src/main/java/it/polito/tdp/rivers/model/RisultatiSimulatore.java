package it.polito.tdp.rivers.model;

public class RisultatiSimulatore {
	
	private int numGiorni;
	private double occupazioneMedia;
	public RisultatiSimulatore(int numGiorni, double occupazioneMedia) {
		super();
		this.numGiorni = numGiorni;
		this.occupazioneMedia = occupazioneMedia;
	}
	public int getNumGiorni() {
		return numGiorni;
	}
	public void setNumGiorni(int numGiorni) {
		this.numGiorni = numGiorni;
	}
	public double getOccupazioneMedia() {
		return occupazioneMedia;
	}
	public void setOccupazioneMedia(double occupazioneMedia) {
		this.occupazioneMedia = occupazioneMedia;
	}
	

}
