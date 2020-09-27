/*
 * Telegrafist
 * Lytter til meldinger fraa kanal
 * Videresender til MonitorK
 *
 * Om Telegrafisten er ferdig, send melding til MonitorK
 */

public class Telegrafist implements Runnable {
  //Monitor for krypterte meldinger
  MonitorKryptert monitorK;
  //Kanalen telegrafisten lytter til
  Kanal kanal;


  public Telegrafist(MonitorKryptert mk, Kanal k) {
    monitorK = mk;
    kanal = k;
  }

  @Override
  public void run() {
    try {
      //Opprett sekvensnr for melding
      int sekvensNr = 0;
      //Lytter til kanalen
      String meldingInn = kanal.lytt();
      //Medan det framleis er meldinger i kanalen...
      while(meldingInn != null) {
        //Sender Melding til MonitorK (thread safe)
        monitorK.leggTilMelding(new Melding(meldingInn, sekvensNr, kanal.hentId()));
        sekvensNr++;
        meldingInn = kanal.lytt();
      }
      //Gjev signal til MonitorK om at telegrafisten er ferdig med kanalen
      monitorK.telegrafistFerdig();
    } catch (InterruptedException e) {}
  }
}
