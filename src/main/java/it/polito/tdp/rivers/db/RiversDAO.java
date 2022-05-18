package it.polito.tdp.rivers.db;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import it.polito.tdp.rivers.model.Dati;
import it.polito.tdp.rivers.model.Flow;
import it.polito.tdp.rivers.model.River;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class RiversDAO {

	public List<River> getAllRivers(Map<Integer, River> idMap) {
		
		final String sql = "SELECT id, name FROM river";

		List<River> rivers = new LinkedList<River>();

		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				River r = idMap.get(res.getInt("id"));
				if(r == null) {
					rivers.add(new River(res.getInt("id"), res.getString("name")));
					idMap.put(res.getInt("id"), new River(res.getInt("id"), res.getString("name")));
				}
			}
			conn.close();
		} catch (SQLException e) {
			//e.printStackTrace();
			throw new RuntimeException("SQL Error");
		}
		return rivers;
	}
	
	public Map<River, Dati> getAllData(Map<Integer, River> idMap){
		String sql = "SELECT river, COUNT(*) AS numMis, MIN(day) AS primo, MAX(day) AS ultimo, AVG(flow) AS avg "
				+ "FROM flow "
				+ "GROUP BY river";
		Map<River, Dati> result = new HashMap<>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			ResultSet res = st.executeQuery();
			while (res.next()) {
				River r = idMap.get(res.getInt("river"));
				if(r!=null) {
					result.put(r, new Dati(r, res.getDate("primo").toLocalDate(), res.getDate("ultimo").toLocalDate(), res.getInt("numMis"), res.getDouble("avg")));
				}
			}
			conn.close();
			return result;
		}catch(SQLException e) {
			throw new RuntimeException("SQL Error");
		}
	}
	
	public List<Flow> getRiverFlow(River r) {
		String sql = "SELECT id, river, DAY, flow "
				+ "FROM flow "
				+ "WHERE river = ?";
		List<Flow> result = new ArrayList<Flow>();
		try {
			Connection conn = DBConnect.getConnection();
			PreparedStatement st = conn.prepareStatement(sql);
			st.setInt(1, r.getId());
			ResultSet res = st.executeQuery();
			while (res.next()) {
				result.add(new Flow(res.getDate("DAY").toLocalDate(), res.getDouble("flow"), r));
			}
			conn.close();
			return result;
		}catch(SQLException e) {
			throw new RuntimeException("SQL Error");
		}
	}
}
