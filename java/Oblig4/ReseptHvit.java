/*
 * ReseptHvit, IKKJE abstrakt,
 * subklasse til Resept, superklasse til ReseptHvitP og ReseptHvitMilitaer.
 *
 * Implementerar den abstrakte metoden .farge() fraa Resept.
 */

//Subklasse
class ReseptHvit extends Resept {

  //Berre for aa halda subklassene Militaer og P.

  ReseptHvit(Legemiddel l, Lege u, Pasient p, int r) {
    super(l, u, p, r);
  }

  //Implementerar abstrakt metode fraa Resept,
  //returnerar farge paa resept som string
  @Override
  public String farge() {
    return "hvit";
  }

  @Override
  public double prisAaBetale() {
    return legemiddel.hentPris();
  }
}
