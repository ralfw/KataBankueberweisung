package de.mbohlen.katabankueberweisung.domain

class KontenRepository {

    private Map kunden = [:]
    private Map konten = [:]

    void init (URL kontenListenURL) {
        kontenlisteEinlesen(kontenListenURL)
        domaenenKlassenVerdrahten()
    }

    Konto findKontoByKontoNummer (String kontoNummer) {
        return konten.get(kontoNummer)
    }
    
    Kunde findKundeByName (String kundenName) {
        return kunden.get(kundenName)
    }
    
    	private kontenlisteEinlesen(URL kontenListenURL) {
		InputStream is = kontenListenURL.openStream()
		boolean istErsteZeile = true
		is.splitEachLine("\t") { fields ->
			if (!istErsteZeile) {
				def parseDouble = { field -> field.replace(',', '.').toDouble() }
				Kunde kunde = new Kunde(name: fields[0], handyNr: fields[4])
				Konto konto = new Konto(inhaber: kunde,  kontonummer: fields[1], kontostand: parseDouble(fields[2]), ueberweisungslimit: parseDouble(fields[3]))

				kunden.put(kunde.name, kunde)
				konten.put(konto.kontonummer, konto)
			}
			istErsteZeile = false
		}
	}

    private void domaenenKlassenVerdrahten() {
        Konto.metaClass.static.findByKontoNummer << { String nr -> this.findKontoByKontoNummer(nr) }
        Kunde.metaClass.static.findByName << { String name -> this.findKundeByName (name) }
        
    }
}
