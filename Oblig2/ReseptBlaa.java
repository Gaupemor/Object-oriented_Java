/*
 * ReseptBlaa, subklassen til abstrakte Resept.
 *
 * Implementerar den abstrakte metoden .prisAaBetale() fraa Resept.
 * Rabatt paa 75%, betalar 25%.
 */

//Subklasse
class ReseptBlaa extends Resept
{
  //Alle har 75% rabatt, pasienten betaler 25% av prisen

  ReseptBlaa(Legemiddel legemiddelInn, Lege utskrivendeLegeInn, int pasientIdInn, int reitInn)
  {
    super(legemiddelInn, utskrivendeLegeInn, pasientIdInn, reitInn);
  }

  //Implementerar abstrakt metode fraa Resept,
  //returnerar farge paa resept som string
  @Override public String farge()
  {
    return "blaa";
  }

  //Implementerar abstrakt metode fraa Resept,
  //returnerar pris aa betala etter rabattfraadrag,
  //75% rabatt, betalar 25%
  @Override public double prisAaBetale()
  {
    double endeligPris = legemiddel.hentPris() / 4;
    return endeligPris;
  }
}
