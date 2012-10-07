package de.mbohlen.katabankueberweisung.cmdline

import de.mbohlen.katabankueberweisung.domain.Bank
import de.mbohlen.katabankueberweisung.domain.BankenRepository
import de.mbohlen.katabankueberweisung.domain.KontenRepository
import de.mbohlen.katabankueberweisung.domain.Konto

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
            while (1) {
                senderkontoErfassen (it)
                betragUndTextErfragen (it)
                empfaengerKontoErfragen (it)
                if (angabenPruefen (it)) {
                    break
                }
            }
            tanVerschicken (it)
            tanErfragenUndPruefen (it)
            println "*** Hier würden wir jetzt das Geld überweisen! ***"
        }
    }

    private boolean tanErfragenUndPruefen (reader) {
        println ""
        while (1) {
            println "Bitte geben Sie die TAN ein,"
            print "die Sie auf Ihrem Mobiltelefon erhalten haben: "
            guContext.eingegebeneTAN = reader.readLine().trim()
            if (guContext.tanIstValide()) {
                break
            }
            println "Ungültige TAN(${guContext.eingegebeneTAN})"
            println "Bitte noch einmal eingeben"
        }
    }

    private void tanVerschicken (reader) {
        println ""
        println "An Ihr Mobiltelefon wurde eine TAN verschickt!"
        println "*** FAKE: Wir verschicken nicht, sondern nehmen an, wir hätten 1234 verschickt!"
        guContext.tanVerschicken()
    }

    private boolean angabenPruefen (reader) {
        println ""
        println "Sender: ${guContext.senderName}"
        println "Sender-Kontonummer: ${guContext.senderKontoNummer}"
        println "Betrag: ${guContext.betrag}"
        println "Überweisungstext: ${guContext.ueberweisungsText}"
        println "Empfänger: ${guContext.empfaengerName}"
        println "Empfänger-Kontonummer: ${guContext.empfaengerKontoNummer}"
        println "Empfänger-Bankleitzahl: ${guContext.empfaengerBlz} (${guContext.empfaengerBank.name})"

        String antwort
        def positiv = { a -> a.empty || a == "ja" }
        def negativ = { a -> a == "nein" }
        println ""
        while (1) {
            print "Alle Angaben korrekt? [ja, nein, leer=ja] "
            antwort = reader.readLine().trim()
            if (positiv(antwort) || negativ(antwort))
                break
            println "Ungültige Antwort"
        }

        return positiv(antwort)
    }

    private void empfaengerKontoErfragen (reader) {
        println ""
        print "Name des Empfängers: "
        guContext.empfaengerName = reader.readLine().trim()
        print "Kontonummer des Empfängers: "
        guContext.empfaengerKontoNummer = reader.readLine().trim()

        while (1) {
            print "Bankleitzahl des Empfängers: "
            guContext.empfaengerBlz = reader.readLine().trim()
            if (guContext.empfaengerIstValide()) {
                break
            }
            println "Ungültige Bankleitzahl(${guContext.empfaengerBlz})"
            println "Bitte noch einmal eingeben"
        }
    }

    private void betragUndTextErfragen (reader) {
        while (1) {
            println ""
            print "Überweisungsbetrag: "
            guContext.betrag = reader.readLine().trim().toDouble()
            if (guContext.betragIstValide()) {
                break
            }
            println "Ungültiger Betrag (${guContext.betrag}) oder Konto hat nicht genug Deckung (${guContext.senderKonto.kontostand})"
            println "Bitte noch einmal eingeben"
        }
        print "Überweisungstext: "
        guContext.ueberweisungsText = reader.readLine().trim()
    }

    private void senderkontoErfassen(reader) {
        while (1) {
            println ""
            print "Name des Senders: "
            guContext.senderName = reader.readLine().trim()
            print "Kontonummer des Senders: "
            guContext.senderKontoNummer = reader.readLine().trim()
            if (guContext.senderIstValide()) {
                break
            }
            println "Ungültiger Name(${guContext.senderName}) oder Kontonummer(${guContext.senderKontoNummer})"
            println "Bitte noch einmal eingeben"
        }
    }

    private void technischeInfrastrukturAufsetzen(String[] args) {
        File kontenListe = new File(args[0], args[1])
        KontenRepository kontenRepository = new KontenRepository()
        kontenRepository.init(kontenListe.toURI().toURL())

        File bankenListe = new File(args[0], args[2])
        BankenRepository bankenRepository = new BankenRepository()
        bankenRepository.init(bankenListe.toURI().toURL())
    }

    public static void main(String[] args) {
        new Program().run(args)
    }
}
