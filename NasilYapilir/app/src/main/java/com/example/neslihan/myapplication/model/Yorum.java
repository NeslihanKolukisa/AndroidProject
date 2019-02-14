package com.example.neslihan.myapplication.model;

public class Yorum {
    private int id;
    private int KullaniciId;
    private String KullaniciAd;
    private String KullaniciGorsel;
    private int UrunId;
    private String Metin;
    private String Tarih;

    public Yorum(int id, int KullaniciId, String KullaniciAd,String KullaniciGorsel, int UrunId, String Metin, String Tarih) {
        this.id = id;
        this.KullaniciId = KullaniciId;
        this.KullaniciAd = KullaniciAd;
        this.KullaniciGorsel = KullaniciGorsel;
        this.UrunId = UrunId;
        this.Metin = Metin;
        this.Tarih = Tarih;
    }
    public int getId()
    {
        return id;
    }
    public  void setId(int id)
    {
        this.id=id;
    }

    public int getKullaniciId()
    {
        return KullaniciId;
    }
    public  void setKullaniciId(int KullaniciId)
    {
        this.KullaniciId=KullaniciId;
    }

    public String getKullaniciAd()
    {
        return KullaniciAd;
    }
    public  void setKullaniciAd(String KullaniciAd)
    {
        this.KullaniciAd=KullaniciAd;
    }

    public String getKullaniciGorsel()
    {
        return KullaniciGorsel;
    }
    public  void setKullaniciGorsel(String KullaniciGorsel)
    {
        this.KullaniciGorsel=KullaniciGorsel;
    }

    public int getUrunId()
    {
        return UrunId;
    }
    public  void setUrunId(int UrunId)
    {
        this.UrunId=UrunId;
    }

    public String getMetin()
    {
        return Metin;
    }
    public  void setMetin(String Yorum)
    {
        this.Metin=Metin;
    }

    public String getTarih()
    {
        return Tarih;
    }
    public  void setTarih(String Tarih)
    {
        this.Tarih=Tarih;
    }

}

