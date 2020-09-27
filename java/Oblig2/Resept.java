/*
 * Abstrakt superklasse Resept,
 * som held ein ID etter kva n-objekt av same type det er.
 *
 * Held òg eit Legemiddel-objekt, eit Lege-objekt, pasientID og reittal,
 * hentemetodar,
 * metode for aa bruka resepten ein gang,
 * og to abstrakt metodar.
 */

//Kan ikkje laga objekt av klassen
//Subklasse
abstract class Resept
{
  //static som held styr paa antall objekt av typen Resept no
  static int antObjektResept;

  //ID-nummeret til dette objektet
  int ID;
  Legemiddel legemiddel;
  Lege utskrivendeLege;
  int pasientId;
  int reit;

  Resept(Legemiddel legemiddelInn, Lege utskrivendeLegeInn, int pasientIdInn, int reitInn)
  {
    //Sett ID med ein privat metode
    ID = settID();
    legemiddel = legemiddelInn;
    utskrivendeLege = utskrivendeLegeInn;
    pasientId = pasientIdInn;
    reit = reitInn;
  }

    //Setter ID til noverande tal med objekt av typen Resept
  private int settID()
  {
    //Auker static int med ein,
    antObjektResept++;
    //Returnerar talet
    return antObjektResept;
  }

  //ALLE HENTEMETODAR
  public int hentID()
  {
    return ID;
  }

  public Legemiddel hentLegemiddel()
  {
    return legemiddel;
  }

  public Lege hentLege()
  {
    return utskrivendeLege;
  }

  public int hentPasientId()
  {
    return pasientId;
  }

  public int hentReit()
  {
    return reit;
  }

  //ANDRE METODAR

  //Prover aa bruka resepten ein gang,
  //returnerar òg om resepten er gyldig eller ei
  public boolean bruk()
  {
    boolean gyldig = false;
    if(reit > 0)
    {
      reit--;
      gyldig = true;
    }
    return gyldig;
  }

  //ABSTRAKTE METODAR

  //Returnerar anten om resepten er "hvit" eller "blaa"
  abstract public String farge();

  //Returnerar prisen pasienten maa betala etter rabattfraadrag
  abstract public double prisAaBetale();
}
