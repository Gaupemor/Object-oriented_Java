/*
 * LegemiddelA, subklassen til abstrakte Legemiddel.
 * Tek inn ein ekstra parameter, int for grad av narkotisk styrke,
 * og hentemetode til narkotisk styrke.
 */

//Subklasse
class LegemiddelA extends Legemiddel
{
  //Narkotisk legemiddel
  int narkotiskStyrke;

  LegemiddelA(String navnInn, double prisInn, double virkestoffInn, int narkotiskStyrkeInn)
  {
    super(navnInn, prisInn, virkestoffInn);
    narkotiskStyrke = narkotiskStyrkeInn;
  }

  public int hentNarkotiskStyrke()
  {
    return narkotiskStyrke;
  }
}
