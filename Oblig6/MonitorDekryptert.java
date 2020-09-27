import java.util.concurrent.locks.*;
import java.util.ArrayList;

public class MonitorDekryptert {
  ArrayList<Melding> meldinger = new ArrayList<Melding>();
  Lock laas = new ReentrantLock();
  Condition ikkeTom = laas.newCondition();

  int antallKryptografer;
  int antallKryptograferFerdige;
  boolean ingenFlereDekrypterteMeldinger;

  public MonitorDekryptert(int k) {
    antallKryptografer = k;
    antallKryptograferFerdige = 0;
    ingenFlereDekrypterteMeldinger = false;
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
      return meldinger.remove(0);
    } finally {
      laas.unlock();
    }
  }

  public void kryptografFerdig() {
    laas.lock();
    try {
      antallKryptograferFerdige++;
      if(antallKryptografer == antallKryptograferFerdige) {
        ingenFlereDekrypterteMeldinger = true;
      }
    } finally {
      laas.unlock();
    }
  }

  public boolean ingenFlereDekrypterteMeldinger() {
    laas.lock();
    try {
      if(ingenFlereDekrypterteMeldinger && meldinger.size() == 0) return true;
      else return false;
    } finally {
      laas.unlock();
    }
  }
}
