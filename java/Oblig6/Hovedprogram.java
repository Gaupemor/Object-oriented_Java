import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;

public class Hovedprogram {
  public static void main(String[] args) throws FileNotFoundException, UnsupportedEncodingException {
    int antallTelegrafister = 3;
    int antallKryptografer = 25;
    Operasjonssentral ops = new Operasjonssentral(antallTelegrafister);
    Kanal[] kanaler = ops.hentKanalArray();
    Thread[] telegrafister = new Thread[antallTelegrafister];
    Thread[] kryptografer = new Thread[antallKryptografer];

    MonitorKryptert monitorK = new MonitorKryptert(antallTelegrafister);
    MonitorDekryptert monitorD = new MonitorDekryptert(antallKryptografer);

    for(int i = 0; i < telegrafister.length; i++) {
      telegrafister[i] = new Thread(new Telegrafist(monitorK, kanaler[i]));
    }

    for(int i = 0; i < kryptografer.length; i++) {
      kryptografer[i] = new Thread(new Kryptograf(monitorK, monitorD));
    }

    Thread o = new Thread(new Operasjonsleder(monitorD, antallTelegrafister));

    long startTid = System.currentTimeMillis();

    for(Thread t : telegrafister) {
      t.start();
    }

    for(Thread k : kryptografer) {
      k.start();
    }

    o.start();

    try {
      for(Thread t : telegrafister) {
        t.join();
      }
      for(Thread k : kryptografer) {
        k.join();
      }
      o.join();
    } catch (InterruptedException e) {}

    System.out.println("Tid bruka med " + antallKryptografer + " kryptografar: " + (System.currentTimeMillis() - startTid));
  }
}
