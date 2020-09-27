/*
 * Operasjonsleder
 * Tek inn dekrypterte meldinger fraa montiorD,
 * Sorterar desse, og til slutt skrivar dei ut til ei fil.
 */

import java.io.PrintWriter;
import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

 public class Operasjonsleder implements Runnable {
   PrintWriter ut;
   MonitorDekryptert monitorD;
   ArrayList<ArrayList<Melding>> meldinger;

   public Operasjonsleder(MonitorDekryptert md, int antallKanaler) throws FileNotFoundException, UnsupportedEncodingException {
     ut = new PrintWriter("utfil.txt", "utf-8");
     monitorD = md;
     //Klargjer listene som tek inn dekrypterte meldinger
     //Nr. i array er kanalId
     meldinger = new ArrayList<ArrayList<Melding>>();
     for(int i = 0; i < antallKanaler; i++) {
       meldinger.add(new ArrayList<Melding>());
     }
   }

   @Override
   public void run() {
     try {
       while(!monitorD.ingenFlereDekrypterteMeldinger()) {
         //Henter ny melding fraa monitorD
         Melding meldingInn = monitorD.hentMelding();
         //Viss meldingene som har komt inn fraa denne kanalen til no er mindre enn sekvensNr til det som kjem inn no...
         if(meldinger.get(meldingInn.hentKanalId() - 1).size() < meldingInn.hentSekvensNr()) {
           //...er meldingen som kjem inn sist til no.
           meldinger.get(meldingInn.hentKanalId() - 1).add(meldingInn);
         } else {
           //Viss ikkje, sett inn meldingen i posisjon i lista
           meldinger.get(meldingInn.hentKanalId() - 1).add(meldingInn.hentSekvensNr(), meldingInn);
         }
       }
       skrivTilFil();
     } catch (InterruptedException e) {}
   }

   private void skrivTilFil() {
     ut.println("  DEKRYPTERTE MELDINGER  ");
     ut.println("<>><<>><<>><<>><<>><<>><>");

     for(ArrayList<Melding> kanal : meldinger) {
       ut.println("\n\n\nNY MELDING\n\n\n");
       for(Melding melding : kanal) {
         ut.println(melding.hentSekvensNr() + "  " + melding.hent() + "\n\n");
       }
     }

     ut.close();
   }
 }
