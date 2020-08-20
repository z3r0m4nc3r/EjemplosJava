package model;

public class Pais {
	
	private String name;
	private String region;
	private long population;
	private String alpha2code;
	private double[] latlng = new double[2];
	public Pais(String name, String region, long population, String alpha2code, double[] latlng) {
		super();
		this.name = name;
		this.region = region;
		this.population = population;
		this.alpha2code = alpha2code;
		this.latlng = latlng;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public long getPopulation() {
		return population;
	}
	public void setPopulation(long population) {
		this.population = population;
	}
	public String getAlpha2code() {
		return alpha2code;
	}
	public void setAlpha2code(String alpha2code) {
		this.alpha2code = alpha2code;
	}
	public double[] getLatlng() {
		return latlng;
	}
	public void setLatlng(double[] latlng) {
		this.latlng = latlng;
	}
	
	

}
