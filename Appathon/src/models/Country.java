package models;

public class Country {
	protected String country;
	protected double vat;
	
	public Country() {
	}
	
	public Country(int id, String country, double vat) {
		super();
		this.country = country;
		this.vat = vat;
	}
	public String getCountry() {
		return country;
	}
	public void setName(String country) {
		this.country = country;
	}
	public double getVat() {
		return vat;
	}
	public void setVat(double vat) {
		this.vat = vat;
	}
}
