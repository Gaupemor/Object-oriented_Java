/*
 * Abstrakt superklasse Legemiddel,
 * som held ein ID etter kva n-objekt av same type det er.
 *
 * Held òg eit namn, ein pris og ei mengde verkestoff i mg,
 * hentemetodar og metode for aa setta ny prisverdi.
 */

//Kan ikkje laga objekt av klassen
abstract class Legemiddel
{
  //static som held styr paa antall objekt av typen Legemiddel no
  static int antObjektLegemiddel;

  //ID-nummeret til dette objektet
  int ID;
  String navn;
  double pris;
  double virkestoff;

  Legemiddel(String navnInn, double prisInn, double virkestoffInn)
  {
    //Sett ID med ein privat metode
    ID = settID();
    navn = navnInn;
    pris = prisInn;
    virkestoff = virkestoffInn;
  }

  //Setter ID til noverande tal med objekt av typen Legemiddel
  private int settID()
  {
    //Auker static int med ein,
    antObjektLegemiddel++;
    //Returnerar talet
    return antObjektLegemiddel;
  }

  //ALLE HENTEMETODAR

  public int hentID()
  {
    return ID;
  }

  public String hentNavn()
  {
    return navn;
  }

  public double hentPris()
  {
    return pris;
  }

  public double hentVirkestoff()
  {
    return virkestoff;
  }

  //Metode som tek inn ein ny flyttalsverdi,
  //og gjev instansvariabelen pris denne verdien
  public void settNyPris(double nyPris)
  {
    pris = nyPris;
  }
}
