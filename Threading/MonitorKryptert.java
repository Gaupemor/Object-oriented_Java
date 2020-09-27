import java.util.concurrent.locks.*;
import java.util.ArrayList;

//Tek inn antall telegrafister

public class MonitorKryptert {

  ArrayList<Melding> meldinger = new ArrayList<Melding>();
  Lock laas = new ReentrantLock();
  Condition ikkeTom = laas.newCondition();

  int antallTelegrafister;
  int antallTelegrafisterFerdige;
  boolean ingenFlereKrypterteMeldinger;

  public MonitorKryptert(int t) {
    antallTelegrafister = t;
    antallTelegrafisterFerdige = 0;
    ingenFlereKrypterteMeldinger = false;
  }

  public void leggTilMelding(Melding m) throws InterruptedException {
    laas.lock();
    try {
      meldinger.add(m);
      ikkeTom.signal();
    } finally {
      laas.unlock();
    }
  }

  public Melding hentMelding() throws InterruptedException {
    laas.lock();
    try {
      if(meldinger.size() == 0) ikkeTom.await();
      //System.out.println(meldinger.get(0).hentKanalId() + " : " + meldinger.get(0).hentSekvensNr());
      return meldinger.remove(0);
    } finally {
      laas.unlock();
    }
  }

  public void telegrafistFerdig() {
    laas.lock();
    try {
      antallTelegrafisterFerdige++;
      if(antallTelegrafisterFerdige == antallTelegrafister) {
        ingenFlereKrypterteMeldinger = true;
      }
    } finally {
      laas.unlock();
    }
  }

  public boolean ingenFlereKrypterteMeldinger() {
    laas.lock();
    try {
      if(ingenFlereKrypterteMeldinger && meldinger.size() == 0) return true;
      else return false;
    } finally {
      laas.unlock();
    }
  }
}
