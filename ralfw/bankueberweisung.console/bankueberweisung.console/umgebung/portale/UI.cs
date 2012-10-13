using System;
using npantarhei.runtime.contract;

namespace bankueberweisung.console.umgebung.portale
{
	[EventBasedComponent]
	public class UI
	{
		public void Start(Tuple<string,string> sender_blz_bankname) {
			Console.WriteLine("Willkommen bei der Kata Bank ({0}, {1})", sender_blz_bankname.Item1, sender_blz_bankname.Item2);
			Console.WriteLine("Ihr Name: ");
			Console.WriteLine("Ihre Kontonummer: ");
			Sender_erfasst(new Tuple<string,string>("Sendername", "Senderktonr"));
		}

		public void Bewegung_erfragen() {
			Console.WriteLine("Überweisungsbetrag: ");
			Console.WriteLine("Überweisungstext: ");
			Bewegung_erfasst(new Tuple<double,string>(42.24, "Text"));
		}

		public void Empfaenger_erfragen() {
			Console.WriteLine("Angaben zum Empfänger");
			Console.WriteLine("Name: ");
			Console.WriteLine("Kontonummer: ");
			Console.WriteLine("BLZ: ");
			Empfaenger_erfasst(new Tuple<string,string,string>("Empfängername", "Empfängerktonr", "EmpfängerBLZ"));
		}

		public void Bestaetigung_erbitten(string empfaenger_blz) {
			Console.WriteLine("Bitte überprüfen Sie die Angaben.");
			Console.WriteLine("Überweisung durchführen? [jN]");
			Bestaetigt(true);
		}

		public void TAN_erfragen() {
			Console.WriteLine("Bitte TAN eingeben: ");
			TAN_erfasst("TAN1234");
		}

		public void Beenden() {
			Console.WriteLine("Überweisung durchgeführt.");
			Ende();
		}

		public event Action<Tuple<string,string>> Sender_erfasst;
		public event Action<Tuple<double,string>> Bewegung_erfasst;
		public event Action<Tuple<string,string,string>> Empfaenger_erfasst;
		public event Action<bool> Bestaetigt;
		public event Action<string> TAN_erfasst;
		public event Action Ende;
	}
}

