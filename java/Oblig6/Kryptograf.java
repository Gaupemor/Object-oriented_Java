/*
 * Kryptograf
 * Henter meldinger fraa monitorK,
 * dekrypterer dei,
 * legger til monitorD.
 *
 * Om Kryptografane er ferdige,
 *    om telegrafistane ikkje er ferdige
 *        vent
 *    om telegrafistane er ferdige
 *        meld fraa til monitorD.
 */

public class Kryptograf implements Runnable {
  MonitorKryptert monitorK;
  MonitorDekryptert monitorD;

  public Kryptograf (MonitorKryptert mk, MonitorDekryptert md) {
    monitorK = mk;
    monitorD = md;
  }

  //Henter kryptert melding fraa monitorK,
  //dekrypterer og sender til monitorD
  @Override
  public void run() {
    try {
      while(!monitorK.ingenFlereKrypterteMeldinger()) {
      Melding meldingInn = monitorK.hentMelding();
      String dekryptertMelding = Kryptografi.dekrypter(meldingInn.hent());
      monitorD.leggTilMelding(new Melding(dekryptertMelding, meldingInn.hentSekvensNr(), meldingInn.hentKanalId()));
      }
      //Gjev signal til monitorD om kryptografen er ferdig
      monitorD.kryptografFerdig();
    } catch (InterruptedException e) {}
  }
}
