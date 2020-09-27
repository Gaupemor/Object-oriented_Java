//SortRute representerar ei svart rute i labyrinten,
//underklasse av Rute,
//dei kan du ikkje gaa gjennom

public class SortRute extends Rute {
  public SortRute(int r, int k) {
    super(r, k);
  }

  //Har teiknet '#'
  @Override
  public char tilTegn() {
    return '#';
  }
}
