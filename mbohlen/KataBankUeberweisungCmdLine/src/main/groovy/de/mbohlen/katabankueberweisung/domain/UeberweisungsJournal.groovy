package de.mbohlen.katabankueberweisung.domain

class UeberweisungsJournal {
    private List<UeberweisungsJournalEintrag> journalEintraege = []
    private def csvFeldUeberschriften
    
    void init (File journalFile) {
        journalEinlesen(journalFile)
        domaenenKlassenVerdrahten()
    }

    void saveJournalEintrag (UeberweisungsJournalEintrag eintrag) {
        journalEintraege.add(eintrag)
    }

    void save(File journalFile) {
        OutputStream os = new FileOutputStream(journalFile)
        PrintStream ps = new PrintStream(os)
        String separator = '\t'
        ps.println csvFeldUeberschriften.join(separator)
        journalEintraege.each { je ->
            List feldListe = [
                je.senderKontoNummer,
                je.ueberweisungsBetrag.toString().replace('.', ','),
                je.ueberweisungsText,
                je.ueberweisungsDatum.format("dd.MM.yy"),
                je.empfaengerName,
                je.empfaengerKontonummer,
                je.empfaengerBLZ
                ]
            ps.println feldListe.join(separator)
        }
        ps.flush()
        ps.close()
        os.close()
    }
    
    private journalEinlesen(File journalFile) {
        InputStream is = new FileInputStream(journalFile)
        boolean istErsteZeile = true
        is.splitEachLine("\t") { fields ->
            if (istErsteZeile) {
                csvFeldUeberschriften = fields
            }
            else {
                UeberweisungsJournalEintrag eintrag = new UeberweisungsJournalEintrag(
                     senderKontoNummer: fields[0],
                     ueberweisungsBetrag:  fields[1].replace(',', '.').toDouble(),
                     ueberweisungsText: fields[2],
                     ueberweisungsDatum: new Date().parse("dd.MM.yy", fields[3]),
                     empfaengerName: fields[4],
                     empfaengerKontonummer: fields[5],
                     empfaengerBLZ: fields[6]
                )
                journalEintraege.add(eintrag)
            }
            istErsteZeile = false
        }
    }

    private void domaenenKlassenVerdrahten() {
        UeberweisungsJournalEintrag.metaClass.save << { -> this.saveJournalEintrag(delegate) }
    }
    
}
