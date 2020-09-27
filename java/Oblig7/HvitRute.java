//Hvit representerar ei kvit rute i labyrinten,
//underklasse av Rute,
//dei kan du gaa gjennom

public class HvitRute extends Rute {
  public HvitRute(int r, int k) {
    super(r, k);
  }

  //'.'
  @Override
  public char tilTegn() {
    return '.';
  }
}
