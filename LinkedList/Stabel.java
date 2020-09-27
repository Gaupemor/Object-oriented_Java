/* BRUKE UnsupportedOperationException HER FOR leggTil() OG fjern()??
 * Den generiske klassa Stabel<T>,
 * subklasse av Lenkeliste<T>,
 * held ein stabel med noder som held verdi av ukjent type T.
 *
 * LIFO - taAv()
 */

public class Stabel<T> extends Lenkeliste<T> {

  //Legg til eit element til sist, likt leggTil()
  public void leggPaa (T x) {
    leggTil(x);
  }

  //Tek av siste el. oevste element i stabelen, LIFO,
  //og returnerar verdien til noden me tok av,
  //kastar unntak om stabelen er tom
  public T taAv() throws UgyldigListeIndeks {
    return fjern(stoerrelse() - 1);
  }
}
