package de.mbohlen.katabankueberweisung.cmdline

import de.mbohlen.katabankueberweisung.domain.KontenRepository
import de.mbohlen.katabankueberweisung.domain.Konto;

class SenderValidierenHabit {
    String senderName
    String senderKontoNummer

    boolean istValide() {
        if (senderName == null || senderName.empty || senderKontoNummer == null || senderKontoNummer.empty) {
            return false
        }
        
        Konto senderKonto = Konto.findByKontoNummer(senderKontoNummer)
        if (senderKonto == null) {
            return false
        }
        
        return senderKonto.gehoert(senderName)
    }
}
