/* SKAL DETTE VERA EIN ABSTRAKT KLASSE ELLER IKKJE?
 * Abstrakt klasse ReseptHvit,
 * subklasse til Resept, superklasse til ReseptHvitP og ReseptHvitMilitaer.
 *
 * Implementerar den abstrakte metoden .farge() fraa Resept.
 */

//Kan ikkje laga objekt av klassen
//Subklasse
abstract class ReseptHvit extends Resept
{

  //Berre for aa halda subklassene Militaer og P.

  ReseptHvit(Legemiddel legemiddelInn, Lege utskrivendeLegeInn, int pasientIdInn, int reitInn)
  {
    super(legemiddelInn, utskrivendeLegeInn, pasientIdInn, reitInn);
  }

  //Implementerar abstrakt metode fraa Resept,
  //returnerar farge paa resept som string
  @Override public String farge()
  {
    return "hvit";
  }
}
