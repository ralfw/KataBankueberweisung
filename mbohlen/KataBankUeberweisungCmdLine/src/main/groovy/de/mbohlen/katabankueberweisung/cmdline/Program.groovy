package de.mbohlen.katabankueberweisung.cmdline

import de.mbohlen.katabankueberweisung.domain.BankenRepository
import de.mbohlen.katabankueberweisung.domain.KontenRepository
import de.mbohlen.katabankueberweisung.domain.KontenRepositoryImpl

public class Program {

    private GeldUeberweisenContext guContext

    private void run(String[] args) {
        println "Willkommen bei der Kata Bank!"

        if (args.length < 3) {
            println "Nutzung: Program <Datenverzeichnis> <Kontenlistendatei> <Bankenlistendatei>"
            return
        }

        technischeInfrastrukturAufsetzen(args)
        guContext = new GeldUeberweisenContext()
        benutzerDialogStarten()

        println ""
        println "Auf Wiedersehen bei der Kata Bank!"
    }

    private benutzerDialogStarten() {
        System.in.withReader {
            senderkontoErfassen (it)
            betragUndTextErfragen (it)
            empfaengerKontoErfragen (it)
        }
    }

    private void empfaengerKontoErfragen (stream) {
        println ""
        print "Name des Empfängers: "
        guContext.empfaengerName = stream.readLine().trim()
        print "Kontonummer des Empfängers: "
        guContext.empfaengerKontoNummer = stream.readLine().trim()

        while (1) {
            print "Bankleitzahl des Empfängers: "
            guContext.empfaengerBlz = stream.readLine().trim()
            if (guContext.empfaengerIstValide()) {
                break
            }
            println "Ungültige Bankleitzahl(${guContext.empfaengerBlz})"
            println "Bitte noch einmal eingeben"
        }
    }

    private void betragUndTextErfragen (stream) {
        while (1) {
            println ""
            print "Überweisungsbetrag: "
            guContext.betrag = stream.readLine().trim().toDouble()
            if (guContext.betragIstValide()) {
                break
            }
            println "Ungültiger Betrag (${guContext.betrag}) oder Konto hat nicht genug Deckung (${guContext.senderKonto.kontostand})"
            println "Bitte noch einmal eingeben"
        }
        print "Überweisungstext: "
        guContext.ueberweisungsText = stream.readLine().trim()
    }

    private void senderkontoErfassen(stream) {
        while (1) {
            println ""
            print "Name des Senders: "
            guContext.senderName = stream.readLine().trim()
            print "Kontonummer des Senders: "
            guContext.senderKontoNummer = stream.readLine().trim()
            if (guContext.senderIstValide()) {
                break
            }
            println "Ungültiger Name(${guContext.senderName}) oder Kontonummer(${guContext.senderKontoNummer})"
            println "Bitte noch einmal eingeben"
        }
    }

    private void technischeInfrastrukturAufsetzen(String[] args) {
        File kontenListe = new File(args[0], args[1])
        KontenRepository kontenRepository = new KontenRepositoryImpl()
        kontenRepository.init(kontenListe.toURI().toURL())

        File bankenListe = new File(args[0], args[2])
        BankenRepository bankenRepository = new BankenRepository()
        bankenRepository.init(bankenListe.toURI().toURL())
    }

    public static void main(String[] args) {
        new Program().run(args)
    }
}
