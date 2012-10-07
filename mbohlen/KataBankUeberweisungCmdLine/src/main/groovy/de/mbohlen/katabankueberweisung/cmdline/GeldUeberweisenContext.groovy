package de.mbohlen.katabankueberweisung.cmdline

import de.mbohlen.katabankueberweisung.domain.Bank
import de.mbohlen.katabankueberweisung.domain.Konto

class GeldUeberweisenContext {
    String senderName
    String senderKontoNummer
    Konto senderKonto
    Double betrag
    String ueberweisungsText
    String empfaengerName
    String empfaengerKontoNummer
    String empfaengerBlz
    Bank empfaengerBank

    boolean senderIstValide() {
        if (senderName == null || senderName.empty || senderKontoNummer == null || senderKontoNummer.empty) {
            return false
        }

        senderKonto = Konto.findByKontoNummer(senderKontoNummer)
        if (senderKonto == null) {
            return false
        }

        return senderKonto.gehoert(senderName)
    }

    boolean betragIstValide() {
        return senderKonto != null && betrag <= senderKonto.kontostand && betrag <= senderKonto.ueberweisungslimit
    }

    boolean empfaengerIstValide() {
        if (empfaengerBlz == null)
            return false
        empfaengerBank = Bank.findByBankLeitzahl (empfaengerBlz)
        return empfaengerBank != null
    }
}