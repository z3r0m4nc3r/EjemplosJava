package model;

public class Pais {
	
	private String country;
	private long population;
	private String capital;
	private double temperature;
	private long foundation;
	private String continent;
	
	public Pais(String country, long population, String capital, double temperature, long foundation, String continent) {
		super();
		this.country = country;
		this.population = population;
		this.capital = capital;
		this.temperature = temperature;
		this.foundation = foundation;
		this.continent = continent;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}
	public long getPopulation() {
		return population;
	}
	public void setPopulation(long population) {
		this.population = population;
	}
	public String getCapital() {
		return capital;
	}
	public void setCapital(String capital) {
		this.capital = capital;
	}
	public double getTemperature() {
		return temperature;
	}
	public void setTemperature(double temperature) {
		this.temperature = temperature;
	}
	public long getFoundation() {
		return foundation;
	}
	public void setFoundation(int foundation) {
		this.foundation = foundation;
	}
	public String getContinent() {
		return continent;
	}
	public void setContinent(String continent) {
		this.continent = continent;
	}
	
	

}
