/*
 * Abstrakt superklasse Resept,
 * som held ein Id etter kva n-objekt av same type det er.
 *
 * Held òg eit Legemiddel-objekt, eit Lege-objekt, pasientId og reittal,
 * hentemetodar,
 * metode for aa bruka resepten ein gang,
 * og to abstrakt metodar.
 */

//Kan ikkje laga objekt av klassen
//Superklasse
abstract class Resept {
  //static som held styr paa antall objekt av typen Resept no
  static int antObjektResept;

  //Id-nummeret til dette objektet
  int Id;
  Legemiddel legemiddel;
  Lege utskrivendeLege;
  Pasient pasient;
  int reit;

  Resept(Legemiddel l, Lege u, Pasient p, int r) {
    //Sett Id med ein privat metode
    Id = settId();
    legemiddel = l;
    utskrivendeLege = u;
    pasient = p;
    reit = r;
  }

    //Setter Id til noverande tal med objekt av typen Resept
  private int settId() {
    //Auker static int med ein,
    antObjektResept++;
    //Returnerar talet
    return antObjektResept;
  }

  //ALLE HENTEMETODAR
  public int hentId() {
    return Id;
  }

  public Legemiddel hentLegemiddel() {
    return legemiddel;
  }

  public Lege hentLege() {
    return utskrivendeLege;
  }

  public Pasient hentPasient() {
    return pasient;
  }

  public int hentReit() {
    return reit;
  }

  //ANDRE METODAR

  //Prover aa bruka resepten ein gang,
  //returnerar òg om resepten er gyldig eller ei
  public boolean bruk() {
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
