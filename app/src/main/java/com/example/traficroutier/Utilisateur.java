package com.example.traficroutier;

public class Utilisateur {

    private String longitude;

    private String lattitude;

    private String vitesse;

    private String sexe;
    private String age;

    //the Constructers

    public Utilisateur(String longitude, String lattitude, String vitesse, String sexe, String age) {
        this.longitude = longitude;
        this.lattitude = lattitude;
        this.vitesse = vitesse;
        this.sexe = sexe;
        this.age = age;
    }

    //the getters

    public String getLongitude() {
        return longitude;
    }

    public String getLattitude() {
        return lattitude;
    }

    public String getVitesse() {
        return vitesse;
    }

    public String getSexe() {
        return sexe;
    }

    public String getAge() {
        return age;
    }

    //the setters

    public void setLongitude(String longitude) {
        this.longitude = longitude;
    }

    public void setLattitude(String lattitude) {
        this.lattitude = lattitude;
    }

    public void setVitesse(String vitesse) {
        this.vitesse = vitesse;
    }

    public void setSexe(String sexe) {
        this.sexe = sexe;
    }

    public void setAge(String age) {
        this.age = age;
    }
}
