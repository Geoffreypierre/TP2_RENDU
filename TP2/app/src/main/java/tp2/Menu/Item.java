package tp2.Menu;

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