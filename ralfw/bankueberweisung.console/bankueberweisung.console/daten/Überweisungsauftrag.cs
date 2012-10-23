using System;

namespace bankueberweisung.console.daten
{
	public class Überweisungsauftrag {
		public Kontoidentifikation Sender, Empfänger;

		public double Betrag;
		public string Text;

		public DateTime Zeitstempel;
	}
}

