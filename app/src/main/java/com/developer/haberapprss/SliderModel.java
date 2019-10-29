package com.developer.haberapprss;

public class SliderModel {
    private String haberResim;
    private String haberBaslik;
    private String haberTarih;
    private String haberDetayResim;
    private String iplink;


    public SliderModel(String haberResim, String haberBaslik,String haberTarih,String haberDetayResim,String iplink) {
        this.haberResim = haberResim;
        this.haberBaslik = haberBaslik;
        this.haberTarih = haberTarih;
        this.haberDetayResim = haberDetayResim;
        this.iplink = iplink;
    }

    public String getHaberTarih() {
        return haberTarih;
    }

    public void setHaberTarih(String haberTarih) {
        this.haberTarih = haberTarih;
    }

    public String getHaberDetayResim() {
        return haberDetayResim;
    }

    public void setHaberDetayResim(String haberDetayResim) {
        this.haberDetayResim = haberDetayResim;
    }

    public String getIplink() {
        return iplink;
    }

    public void setIplink(String iplink) {
        this.iplink = iplink;
    }

    public String getHaberResim() {
        return haberResim;
    }

    public void setHaberResim(String haberResim) {
        this.haberResim = haberResim;
    }

    public String getHaberBaslik() {
        return haberBaslik;
    }

    public void setHaberBaslik(String haberBaslik) {
        this.haberBaslik = haberBaslik;
    }
}



