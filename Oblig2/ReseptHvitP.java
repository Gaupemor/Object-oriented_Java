/* TEK IKKJE INN REIT PARAMETAR
 * ReseptHvitP, subklassen til abstrakte ReseptHvit, subklassen til abstrakte Resept.
 *
 * Implementerar den abstrakte metoden .prisAaBetale() fraa Resept.
 * Rabatt paa minus 116 kr., men ikkje mindre enn 0.
 *
 * Sender alltid 3 som reittal til super.
 */

//Subklasse av subklasse
class ReseptHvitP extends ReseptHvit
{

  //Statisk rabatt, minus 116, MEN ikkje mindre enn 0 kr.
  //Alltid 3 reit

  ReseptHvitP(Legemiddel legemiddelInn, Lege utskrivendeLegeInn, int pasientIdInn)
  {
    //3 reit til super
    super(legemiddelInn, utskrivendeLegeInn, pasientIdInn, 3);
  }

  //Implementerar abstrakt metode fraa Resept,
  //returnerar pris aa betala etter rabattfraadrag,
  //minus 116 kr., men ikkje mindre enn 0 kr.
  @Override public double prisAaBetale()
  {
    double endeligPris;
    if (legemiddel.hentPris() <= 116)
    {
      endeligPris = 0;
    }
    else
    {
      endeligPris = legemiddel.hentPris() - 116;
    }
    return endeligPris;
  }
}
