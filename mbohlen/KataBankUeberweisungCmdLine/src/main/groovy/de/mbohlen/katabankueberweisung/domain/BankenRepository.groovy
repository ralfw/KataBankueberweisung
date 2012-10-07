package de.mbohlen.katabankueberweisung.domain

import java.net.URL
import java.util.Map

class BankenRepository {
    private Map banken = [:]

    void init (URL bankenListenURL) {
        bankenlisteEinlesen(bankenListenURL)
        domaenenKlassenVerdrahten()
    }

    Bank findBankByLeitzahl (String bankLeitzahl) {
        return banken.get(bankLeitzahl)
    }

    private bankenlisteEinlesen(URL bankenListenURL) {
        InputStream is = bankenListenURL.openStream()
        boolean istErsteZeile = true
        is.splitEachLine("\t") { fields ->
            if (!istErsteZeile) {
                Bank bank = new Bank(bankLeitzahl: fields[0], name: fields[1])
                banken.put(bank.bankLeitzahl, bank)
            }
            istErsteZeile = false
        }
    }

    private void domaenenKlassenVerdrahten() {
        Bank.metaClass.static.findByBankLeitzahl << { String blz -> this.findBankByLeitzahl(blz) }
    }
}
