package com.example.neslihan.myapplication.model;

public class Favori {
    private  int id;
    private  int KullaniciId;
    private  int UrunId;
    private  int EkleyenKulId;
    private  int KategoriId;
    private String UrunBaslik;
    private String UrunTanim;
    private  String UrunAciklama;
    private String Malzemeler;
    private int YapilmaSuresi;
    private String UrunResim;
    private  int BegeniSayisi;
    private  int yorumSayisi;
    public Favori(int id,int KullaniciId,int UrunId,int EkleyenKulId,int KategoriId, String UrunBaslik,
                String UrunTanim, String UrunAciklama,String Malzemeler,int YapilmaSuresi,
                String UrunResim,int BegeniSayisi,int yorumSayisi) {
        this.id = id;
        this.KullaniciId = KullaniciId;
        this.UrunId = UrunId;
        this.EkleyenKulId = EkleyenKulId;
        this.KategoriId = KategoriId;
        this.UrunBaslik = UrunBaslik;
        this.UrunTanim = UrunTanim;
        this.UrunAciklama = UrunAciklama;
        this.Malzemeler = Malzemeler;
        this.YapilmaSuresi = YapilmaSuresi;
        this.UrunResim = UrunResim;
        this.BegeniSayisi = BegeniSayisi;
        this.yorumSayisi = yorumSayisi;
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

    public int getUrunId()
    {
        return UrunId;
    }
    public  void setUrunId(int UrunId)
    {
        this.UrunId=UrunId;
    }

    public int getEkleyenKulId()
    {
        return EkleyenKulId;
    }
    public  void setEkleyenKulId(int EkleyenKulId)
    {
        this.EkleyenKulId=EkleyenKulId;
    }

    public int getKategoriId()
    {
        return KategoriId;
    }
    public  void setKategoriId(int KategoriId)
    {
        this.KategoriId=KategoriId;
    }

    public String getUrunBaslik()
    {
        return UrunBaslik;
    }
    public void setUrunBaslik(String UrunBaslik) {
        this.UrunBaslik = UrunBaslik;
    }

    public String getUrunTanim()
    {
        return UrunTanim;
    }
    public void setUrunTanim(String UrunTanim)
    {
        this.UrunTanim=UrunTanim;
    }

    public String getUrunAciklama()
    {
        return UrunAciklama;
    }
    public void setUrunAciklama(String UrunAciklama)
    {
        this.UrunAciklama=UrunAciklama;
    }

    public String getMalzemeler()
    {
        return Malzemeler;
    }
    public  void setMalzemeler(String Malzemeler)
    {
        this.Malzemeler=Malzemeler;
    }


    public int getYapilmaSuresi()
    {
        return YapilmaSuresi;
    }
    public  void setYapilmaSuresi(int YapilmaSuresi)
    {
        this.YapilmaSuresi=YapilmaSuresi;
    }

    public String getUrunResim()
    {
        return UrunResim;
    }
    public  void setUrunResim(String UrunResim)
    {
        this.UrunResim=UrunResim;
    }

    public int getBegeniSayisi()
    {
        return BegeniSayisi;
    }
    public  void setBegeniSayisi(int BegeniSayisi)
    {
        this.BegeniSayisi=BegeniSayisi;
    }

    public int getyorumSayisi()
    {
        return yorumSayisi;
    }
    public  void setyorumSayisi(int yorumSayisi)
    {
        this.yorumSayisi=yorumSayisi;
    }


}
