package de.mbohlen.katabankueberweisung.ueberweisung

/**
 * Methodenlose Rolle, also "I" im Sinne von DCI.
 *
 */
interface GeldQuelle {
    KontoIdentifikation getIdentifikation()
    void ueberweisenAn (Double betrag, String ueberweisungsText, GeldSenke empfaenger)
}
