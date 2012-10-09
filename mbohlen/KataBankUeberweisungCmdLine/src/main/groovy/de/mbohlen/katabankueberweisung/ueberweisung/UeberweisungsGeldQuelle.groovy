package de.mbohlen.katabankueberweisung.ueberweisung

import java.util.Date

import de.mbohlen.katabankueberweisung.domain.UeberweisungsJournalEintrag

/**
 * Methodenreiche Rolle, also "I" im Sinne von DCI.
 *
 */
class UeberweisungsGeldQuelle implements GeldQuelle {

    @Override
    public void ueberweisenAn(Double betrag, String ueberweisungsText, GeldSenke empfaenger) {
        if (kontostand < betrag || betrag > ueberweisungslimit) {
            throw new RuntimeException("zuwenig Deckung oder zu enges Limit auf dem Konto")
        }
        erniedrigeStand(betrag)
        empfaenger.ueberweisenVon(betrag, ueberweisungsText, this)
        journal (betrag, ueberweisungsText, empfaenger.getIdentifikation())
    }

    private void journal (Double betrag, String ueberweisungsText, KontoIdentifikation empfaenger) {
        UeberweisungsJournalEintrag eintrag = new UeberweisungsJournalEintrag(
                senderKontoNummer: kontonummer,
                ueberweisungsBetrag:  betrag,
                ueberweisungsText: ueberweisungsText,
                ueberweisungsDatum: new Date(),
                empfaengerName: empfaenger.inhaberName,
                empfaengerKontonummer: empfaenger.kontoNummer,
                empfaengerBLZ: empfaenger.bankLeitzahl
                )
        eintrag.save()
    }

    @Override
    public KontoIdentifikation getIdentifikation() {
        return new KontoIdentifikation(inhaberName: inhaber.name, bankLeitzahl: "KataBankLeitzahl", kontoNummer: kontonummer)
    }
}
