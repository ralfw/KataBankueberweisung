package de.mbohlen.katabankueberweisung.ueberweisung

/**
 * Methodenlose Rolle, also "I" im Sinne von DCI.
 *
 */
interface GeldSenke {
    KontoIdentifikation getIdentifikation()
    void ueberweisenVon (Double betrag, String ueberweisungsText, GeldQuelle quelle)
}
