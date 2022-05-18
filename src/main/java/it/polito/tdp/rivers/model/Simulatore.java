package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.List;
import java.util.PriorityQueue;

public class Simulatore {
	
	private River r;
	//parametri della simulazione
	private double k;
	private double capienzaTotale;
	private double occupazioneIniziale;
	private double fOutMin;
	private double probabilita = 0.5;
	//eventi sono i flussi
	private PriorityQueue<Flow> queue;
	private int giorni;
	private double fOut;
	private double fIn;
	private List<Double> capacita;
	private double Cmed;
	
	//inizializzo la simulazione
	public void init(River r, double k) {
		this.queue = new PriorityQueue<>();
		this.queue.addAll(r.getFlows());
		this.capienzaTotale = k * convertiFMed(r.getFlowAvg()) * 30;
		this.occupazioneIniziale = capienzaTotale/2;
		this.fOutMin = 0.8 * convertiFMed(r.getFlowAvg());
		this.giorni = 0;
		this.capacita = new ArrayList<>();
		this.Cmed = 0.0;
		System.out.println("Q: "+this.capienzaTotale+"");
	}
	public RisultatiSimulatore run() {
		while(!this.queue.isEmpty()) {
			Flow f = this.queue.poll(); //questo è il flusso in ingresso
			processFlow(f);
		}
		//fornisco i risultati della simulazione
		RisultatiSimulatore rs = new RisultatiSimulatore(giorni, getCMed());
		return rs;
	}
	private void processFlow(Flow f) {
		fOut = fOutMin; //dobbiamo garantire che il flusso in uscita sia almeno quanto il valore minimo
		//C'è il 5% di probabilità che fOut = 10*fOutMin
		if(Math.random() > 0.95) {
			fOut = 10 * fOutMin;
			System.out.println("10*fOutMin");
		}
		System.out.println("fOut: "+fOut);
		System.out.println("fIn: "+convertiFMed(f.getFlow()));
		//aggiungo alla quantità attuale del bacino fIn;
		this.occupazioneIniziale += convertiFMed(f.getFlow());
		//non posso avere C > Q:
		if(this.occupazioneIniziale > this.capienzaTotale) {
			//flusso in ingresso eccessivo deve essere scaricato in uscita
			double eccesso = this.occupazioneIniziale - this.capienzaTotale;
			this.occupazioneIniziale -= eccesso;
			//fOut += eccesso;
		}
		//C deve garantire flusso in uscita pari ad almeno fOutMin
		if(this.occupazioneIniziale < fOut) {
			giorni++;
			//bacino si svuota
			this.occupazioneIniziale = 0;
		}else {
			//altrimenti facciu uscire la quantita giornaliera
			this.occupazioneIniziale -= fOut;
		}
		System.out.println("C: "+this.occupazioneIniziale);
		capacita.add(this.occupazioneIniziale);
	}
	
	
	private double convertiFMed(double fmed) {
		fmed = fmed*60*60*24;
		return fmed;
	}
	
	private double getCMed() {
		double somma = 0.0;
		for(Double d : this.capacita) {
			somma += d;
		}
		return (somma/this.capacita.size());
	}

}
