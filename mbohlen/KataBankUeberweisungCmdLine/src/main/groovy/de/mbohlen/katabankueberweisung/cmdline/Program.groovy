package de.mbohlen.katabankueberweisung.cmdline

public class Program {
    public static void main(String[] args) {
        println "Willkommen bei der Kata Bank!"

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

        println "Auf Wiedersehen bei der Kata Bank!"
    }
}
