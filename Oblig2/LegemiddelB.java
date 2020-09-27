/*
 * LegemiddelB, subklassen til abstrakte Legemiddel.
 * Tek inn ein ekstra parameter, int for grad av vanedannande styrke,
 * og hentemetode til vanedannande styrke.
 */

//Subklasse
class LegemiddelB extends Legemiddel
{
  //Vanedannande legemiddel
  int vanedannendeStyrke;

  LegemiddelB(String navnInn, double prisInn, double virkestoffInn, int vanedannendeStyrkeInn)
  {
    super(navnInn, prisInn, virkestoffInn);
    vanedannendeStyrke = vanedannendeStyrkeInn;
  }

  public int hentVanedannendeStyrke()
  {
    return vanedannendeStyrke;
  }
}
