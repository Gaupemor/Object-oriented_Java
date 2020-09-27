/*
 * Klassa UgyldigListeIndeks, unntaksklasse,
 * som vert kasta om ein proevar aa naa eit element som ikkje finst.
 *
 * Sender ut nr paa den ugyldige indeksen ein proevde aa naa.
 * I Lenkeliste-klassa og subklassene hennar
 * returnerar me -1 om me proever aa fjerna element fraa ei tom liste.
 */

class UgyldigListeIndeks extends RuntimeException {
  public UgyldigListeIndeks(int indeks) {
    super("Ugyldig indeks: " + indeks);
  }
}
