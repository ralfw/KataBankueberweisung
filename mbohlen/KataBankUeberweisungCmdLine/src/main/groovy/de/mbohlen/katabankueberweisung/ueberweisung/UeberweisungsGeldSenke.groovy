package de.mbohlen.katabankueberweisung.ueberweisung

import de.mbohlen.katabankueberweisung.domain.Konto

class UeberweisungsGeldSenke implements GeldSenke {
    String bankLeitzahl
    String kontoNummer
    String empfaenger

    @Override
    public KontoIdentifikation getIdentifikation() {
        return new KontoIdentifikation (inhaberName: empfaenger, bankLeitzahl: bankLeitzahl, kontoNummer: kontoNummer)
    }

    @Override
    public void ueberweisenVon(Double betrag, String ueberweisungsText, GeldQuelle quelle) {
        ueberweisungsAuftrag (betrag, ueberweisungsText, new Date(), quelle.getIdentifikation())
    }

    private void ueberweisungsAuftrag (Double betrag, String ueberweisungsText, Date ueberweisungsDatum, KontoIdentifikation sender) {
        // hier neuen Auftrag erzeugen und speichern
    }
}
