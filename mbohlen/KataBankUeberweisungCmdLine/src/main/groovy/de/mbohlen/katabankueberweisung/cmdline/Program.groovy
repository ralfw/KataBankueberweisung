package de.mbohlen.katabankueberweisung.cmdline

import de.mbohlen.katabankueberweisung.domain.KontenRepository
import de.mbohlen.katabankueberweisung.domain.KontenRepositoryImpl

public class Program {

    private void run(String[] args) {
        println "Willkommen bei der Kata Bank!"

        if (args.length < 2) {
            println "Nutzung: Program <Datenverzeichnis> <Name der Kontenlistendatei>"
        }

        technischeInfrastrukturAufsetzen(args)
        benutzerDialogStarten()

        println ""
        println "Auf Wiedersehen bei der Kata Bank!"
    }

	private benutzerDialogStarten() {
		System.in.withReader {
			SenderValidierenHabit svHabit = new SenderValidierenHabit()

			while (1) {
				println ""
				print "Name des Senders: "
				svHabit.senderName = it.readLine().trim()
				print "Kontonummer des Senders: "
				svHabit.senderKontoNummer = it.readLine().trim()
				if (svHabit.istValide()) {
					break
				}
				println "Ungültiger Name(${svHabit.senderName}) oder Kontonummer(${svHabit.senderKontoNummer})"
				println "Bitte noch einmal eingeben"
			}
		}
	}

	private void technischeInfrastrukturAufsetzen(String[] args) {
		File kontenListe = new File(args[0], args[1])
		KontenRepository kontenRepository = new KontenRepositoryImpl()
		kontenRepository.init(kontenListe.toURI().toURL())
	}
    
    public static void main(String[] args) {
        new Program().run(args)
    }

}
