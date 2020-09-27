/*
 * LegemiddelC, subklassen til abstrakte Legemiddel.
 * Er lik superklassen, men ikkje abstrakt.
 * Tek ikkje inn nokre tilleggsparameter og har ikkje eigne metodar
 */

//Subklasse
class LegemiddelC extends Legemiddel
{

  //Vanleg legemiddel

  LegemiddelC(String navnInn, double prisInn, double virkestoffInn)
  {
    super(navnInn, prisInn, virkestoffInn);
  }
}
