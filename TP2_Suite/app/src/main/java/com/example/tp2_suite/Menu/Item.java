package com.example.tp2_suite.Menu;

public class Item {

    String NomExo;
    int Imagexo;

    public Item(String NomExo,int Imagexo)
    {
        this.NomExo=NomExo;
        this.Imagexo=Imagexo;
    }
    public String getNomExo()
    {
        return NomExo;
    }
    public int getImagexo()
    {
        return Imagexo;
    }
}