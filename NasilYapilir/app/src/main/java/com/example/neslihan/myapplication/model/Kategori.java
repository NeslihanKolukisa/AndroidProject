package com.example.neslihan.myapplication.model;

public class Kategori {

    private  int id;
    private String KategoriAd;
    private String Gorsel;

    public Kategori(int id, String KategoriAd,String Gorsel) {
        this.id = id;
        this.KategoriAd = KategoriAd;
        this.Gorsel = Gorsel;
    }

    public int getId()
    {
        return id;
    }
    public  void setId(int id)
    {
        this.id=id;
    }
    public String getKategoriAd()
    {
        return KategoriAd;
    }
    public void setKategoriAd(String KategoriAd) {
        this.KategoriAd = KategoriAd;
    }
    public String getGorsel()
    {
        return Gorsel;
    }
    public void setGorsel(String Gorsel) {
        this.Gorsel = Gorsel;
    }

}
