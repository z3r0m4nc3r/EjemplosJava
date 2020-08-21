package model;

import java.util.Date;

public class records {
	private Date dateRep;
	private int day;
	private int month;
	private int year;
	private int cases;
	private int deaths;
	private String countriesAndTerritories;
	private long popData2019;
	private String continentExp;
	private double cases_per_100000;
	
	public records(Date dateRep, int day, int month, int year, int cases, int deaths, String countriesAndTerritories,
			long popData2019, String continentExp, double cases_per_100000) {
		super();
		this.dateRep = dateRep;
		this.day = day;
		this.month = month;
		this.year = year;
		this.cases = cases;
		this.deaths = deaths;
		this.countriesAndTerritories = countriesAndTerritories;
		this.popData2019 = popData2019;
		this.continentExp = continentExp;
		this.cases_per_100000 = cases_per_100000;
	}
	
	public records(Date dateRep, int cases, int deaths, String countriesAndTerritories, long popData2019,
			String continentExp, double cases_per_100000) {
		super();
		this.dateRep = dateRep;
		this.cases = cases;
		this.deaths = deaths;
		this.countriesAndTerritories = countriesAndTerritories;
		this.popData2019 = popData2019;
		this.continentExp = continentExp;
		this.cases_per_100000 = cases_per_100000;
	}

	public Date getDateRep() {
		return dateRep;
	}

	public void setDateRep(Date dateRep) {
		this.dateRep = dateRep;
	}

	public int getDay() {
		return day;
	}

	public void setDay(int day) {
		this.day = day;
	}

	public int getMonth() {
		return month;
	}

	public void setMonth(int month) {
		this.month = month;
	}

	public int getYear() {
		return year;
	}

	public void setYear(int year) {
		this.year = year;
	}

	public int getCases() {
		return cases;
	}

	public void setCases(int cases) {
		this.cases = cases;
	}

	public int getDeaths() {
		return deaths;
	}

	public void setDeaths(int deaths) {
		this.deaths = deaths;
	}

	public String getCountriesAndTerritories() {
		return countriesAndTerritories;
	}

	public void setCountriesAndTerritories(String countriesAndTerritories) {
		this.countriesAndTerritories = countriesAndTerritories;
	}

	public long getPopData2019() {
		return popData2019;
	}

	public void setPopData2019(long popData2019) {
		this.popData2019 = popData2019;
	}

	public String getContinentExp() {
		return continentExp;
	}

	public void setContinentExp(String continentExp) {
		this.continentExp = continentExp;
	}

	public double getCases_per_100000() {
		return cases_per_100000;
	}

	public void setCases_per_100000(double cases_per_100000) {
		this.cases_per_100000 = cases_per_100000;
	}
	
	
	
	
	
	

}
