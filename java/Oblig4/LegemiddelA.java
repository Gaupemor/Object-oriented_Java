/*
 * LegemiddelA, subklassen til abstrakte Legemiddel.
 * Tek inn ein ekstra parameter, int for grad av narkotisk styrke,
 * og hentemetode til narkotisk styrke.
 */

//Subklasse
class LegemiddelA extends Legemiddel {
  //Narkotisk legemiddel
  int narkotiskStyrke;

  LegemiddelA(String navnInn, double p, double v, int narko) {
    super(navnInn, p, v);
    narkotiskStyrke = narko;
  }

  public int hentNarkotiskStyrke() {
    return narkotiskStyrke;
  }
}
