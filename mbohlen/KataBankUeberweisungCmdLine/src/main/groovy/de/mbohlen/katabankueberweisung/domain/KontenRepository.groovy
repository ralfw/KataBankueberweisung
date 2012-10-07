package de.mbohlen.katabankueberweisung.domain

class KontenRepository {

    private Map kunden = [:]
    private Map konten = [:]

    def init (URL kontenListenURL) {
        InputStream is = kontenListenURL.openStream()
        boolean istErsteZeile = true
        is.splitEachLine("\t") { fields ->
            if (!istErsteZeile) {
                def parseDouble = { field -> field.replace(',', '.').toDouble() }
                Kunde kunde = new Kunde(name: fields[0], ueberweisungslimit: parseDouble(fields[3]), handyNr: fields[4])
                Konto konto = new Konto(inhaber: kunde,  kontonummer: fields[1], kontostand: parseDouble(fields[2]))

                kunden.put(kunde.name, kunde)
                konten.put(konto.kontonummer, konto)
            }
            istErsteZeile = false
        }
    }

    Konto findKontoByKontoNummer (String kontoNummer) {
        return konten.get(kontoNummer)
    }
}
