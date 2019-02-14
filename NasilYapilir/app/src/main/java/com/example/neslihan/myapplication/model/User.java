package com.example.neslihan.myapplication.model;

public class User {

    private  int id;
    private  int Yid;
    private  String name;
    private  String Yname;
    private  String password;
    private  String email;

    private  String gorsel;
    private  String Ygorsel;
    private  String arka;
    private  String Yarka;

    public User(int id, String name, String password, String email, String gorsel, String arka) {
        this.id = id;
        this.name = name;
        this.password = password;
        this.email = email;

        this.gorsel = gorsel;
        this.arka = arka;

        Yname = name;
        Yid = id;
        Ygorsel = gorsel;
        Yarka = arka;

    }

    public  int getId() {
        return Yid;
    }

    public void setId(int Id) {
        id = Id;
    }

    public String getName() {
        return name;
    }

    public void setName(String Name) {

        name = Name;
    }

    public  String getEmail() {
        return email;
    }

    public  void setEmail(String Email) {
        email = Email;
    }

    public  String getPassword() {
        return password;
    }

    public  void setPassword(String Password) {
        password = Password;
    }

    public  String getGorsel() {
        return Ygorsel;
    }

    public  void setGorsel(String Gorsel) {
        gorsel = Gorsel;
    }

    public  String getArka() {
        return Yarka;
    }

    public  void setArka(String Arka) {
        arka = Arka;
    }
}
