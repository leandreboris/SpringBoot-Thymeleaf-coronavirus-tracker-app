package com.main.models;


public class LocalStats {
	private String state;
	private String country;
	private Long latestCases;
	
	@Override
	public String toString() {
		return "LocalStats [state=" + state + ", country=" + country + ", latestCases=" + latestCases + "]";
	}
	public String getState() {
		return state;
	}
	public void setState(String state) {
		this.state = state;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public Long getLatestCases() {
		return latestCases;
	}
	public void setLatestCases(Long latestCases) {
		this.latestCases = latestCases;
	}
	
	

}
