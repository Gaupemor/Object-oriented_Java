/*
 * LegemiddelB, subklassen til abstrakte Legemiddel.
 * Tek inn ein ekstra parameter, int for grad av vanedannande styrke,
 * og hentemetode til vanedannande styrke.
 */

//Subklasse
class LegemiddelB extends Legemiddel {
  //Vanedannande legemiddel
  int vanedannendeStyrke;

  LegemiddelB(String n, double p, double virk, int vane) {
    super(n, p, virk);
    vanedannendeStyrke = vane;
  }

  public int hentVanedannendeStyrke() {
    return vanedannendeStyrke;
  }
}
