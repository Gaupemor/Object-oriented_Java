/*
 * ReseptHvitMilitaer, subklassen til abstrakte ReseptHvit, subklassen til abstrakte Resept.
 *
 * Implementerar den abstrakte metoden .prisAaBetale() fraa Resept.
 * Rabatt paa 100% av prisen; betalar alltid 0 kr.
 */

//Subklasse av subklasse
class ReseptHvitMilitaer extends ReseptHvit {

  //100% rabatt paa pris av Legemiddel-objekt

  ReseptHvitMilitaer(Legemiddel l, Lege u, Pasient p, int r) {
    super(l, u, p, r);
  }

  //Implementerar abstrakt metode fraa Resept,
  //returnerar pris aa betala etter rabattfraadrag,
  //100% rabatt, betalar alltid 0 kr.
  @Override
  public double prisAaBetale() {
    return 0.0;
  }
}
