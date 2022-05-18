package it.polito.tdp.rivers.model;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.db.RiversDAO;

public class Model {
	
	private RiversDAO dao;
	private List<River> fiumi;
	private Map<Integer,River> idMapRiver;
	private Map<River, Dati> datiFiumi;
	private List<Flow> flussiFiume;
	private Simulatore sim;
	
	
	public Model() {
		this.dao = new RiversDAO();
		this.idMapRiver = new HashMap<Integer, River>();
		this.fiumi = this.dao.getAllRivers(this.idMapRiver);
		this.datiFiumi = this.dao.getAllData(idMapRiver);
		this.flussiFiume = new ArrayList<>();
		this.sim = new Simulatore();
	}
	
	public List<River> getAllRivers(){
		return this.fiumi;
	}
	
	public Dati getDatiFiume(River r) {
		Dati d = this.datiFiumi.get(r);
		r.setFlowAvg(d.getAvgFlow());
		return d;
	}
	
	public void loadFlows(River r) {
		this.flussiFiume = this.dao.getRiverFlow(r);
		r.setFlows(flussiFiume);
	}
	public List<Flow> getFlows(){
		return this.flussiFiume;
	}
	
	public void inizializza(River r, double k) {
		this.sim.init(r, k);
	}
	public RisultatiSimulatore simulazione() {
		RisultatiSimulatore r =	this.sim.run();
		return r;
	}
	
	

	
}
