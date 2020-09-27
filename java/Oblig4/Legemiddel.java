/*
 * Abstrakt superklasse Legemiddel,
 * som held ein Id etter kva n-objekt av same type det er.
 *
 * Held òg eit namn, ein pris og ei mengde verkestoff i mg,
 * hentemetodar og metode for aa setta ny prisverdi.
 */

//Kan ikkje laga objekt av klassen
abstract class Legemiddel {
  //static som held styr paa antall objekt av typen Legemiddel no
  static int antObjektLegemiddel;

  //Id-nummeret til dette objektet
  int Id;
  String navn;
  double pris;
  double virkestoff;

  Legemiddel(String n, double p, double v) {
    //Sett Id med ein privat metode
    Id = settId();
    navn = n;
    pris = p;
    virkestoff = v;
  }

  //Setter Id til noverande tal med objekt av typen Legemiddel
  private int settId() {
    //Auker static int med ein,
    antObjektLegemiddel++;
    //Returnerar talet
    return antObjektLegemiddel;
  }

  //ALLE HENTEMETODAR

  public int hentId() {
    return Id;
  }

  public String hentNavn() {
    return navn;
  }

  public double hentPris() {
    return pris;
  }

  public double hentVirkestoff() {
    return virkestoff;
  }

  //Metode som tek inn ein ny flyttalsverdi,
  //og gjev instansvariabelen pris denne verdien
  public void settNyPris(double nyPris) {
    pris = nyPris;
  }
}
