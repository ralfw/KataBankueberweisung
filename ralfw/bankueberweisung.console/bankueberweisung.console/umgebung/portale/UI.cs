using System;
using npantarhei.runtime.contract;

namespace bankueberweisung.console.umgebung.portale
{
	[EventBasedComponent]
	public class UI
	{
		public void Start(Tuple<string,string> sender_blz_bankname) {
			Console.WriteLine("Willkommen bei der Kata Bank ({0}, {1})", sender_blz_bankname.Item1, sender_blz_bankname.Item2);
			Console.Write("Ihr Name: "); var name = Console.ReadLine();
			Console.Write("Ihre Kontonummer: "); var kontonummer = Console.ReadLine();
			Sender_erfasst(new Tuple<string,string>(name, kontonummer));
		}

		public void Bewegung_erfragen() {
			Console.Write("Überweisungsbetrag: "); var betrag = double.Parse(Console.ReadLine());
			Console.Write("Überweisungstext: "); var text = Console.ReadLine();
			Bewegung_erfasst(new Tuple<double,string>(betrag, text));
		}

		public void Empfaenger_erfragen() {
			Console.WriteLine("Angaben zum Empfänger");
			Console.Write("Name: "); var name = Console.ReadLine();
			Console.Write("Kontonummer: "); var kontonr = Console.ReadLine();
			Console.Write("BLZ: "); var blz = Console.ReadLine();
			Empfaenger_erfasst(new Tuple<string,string,string>(name, kontonr, blz));
		}

		public void Bestaetigung_erbitten(Tuple<string,string> empfänger_blz_bankname) {
			Console.WriteLine("Bankname: {0}", empfänger_blz_bankname.Item2);

			Console.WriteLine("Bitte überprüfen Sie die Angaben.");
			Console.Write("Überweisung durchführen? [jN]"); var cmd = Console.ReadLine();

			if (cmd == "" || cmd.ToLower() == "j")
				Bestaetigt();
			else
			{
				Console.ForegroundColor = ConsoleColor.Yellow;
					Console.WriteLine("*** Überweisung abgebrochen!");
				Console.ResetColor();
				Ende();
			}
		}

		public void TAN_erfragen() {
			Console.Write("Bitte TAN eingeben: "); var tan = Console.ReadLine();
			TAN_erfasst(tan);
		}

		public void Beenden() {
			Console.WriteLine("Überweisung durchgeführt.");
			Ende();
		}

		public void Fehler(string fehlermeldung) {
			Console.ForegroundColor = ConsoleColor.Red;
				Console.WriteLine("*** Fehler: {0}", fehlermeldung);
			Console.ResetColor();
			Ende();
		}

		public event Action<Tuple<string,string>> Sender_erfasst;
		public event Action<Tuple<double,string>> Bewegung_erfasst;
		public event Action<Tuple<string,string,string>> Empfaenger_erfasst;
		public event Action Bestaetigt;
		public event Action<string> TAN_erfasst;
		public event Action Ende;
	}
}

