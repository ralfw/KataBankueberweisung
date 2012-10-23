using System;
using System.IO;
using bankueberweisung.console.daten;
using npantarhei.runtime.contract;

namespace bankueberweisung.console
{
	[InstanceOperations]
	public class Überweisungsjournal
	{
		public Überweisungsauftrag Journalisieren(Überweisungsauftrag auftrag) {
			File.AppendAllText("db/Überweisungsjournal.txt",
			                   string.Format("{0}\t{1}\t{2}\t{3:dd.MM.yy}\t{4}\t{5}\t{6}\n", 
			              					  	auftrag.Sender.Kontonummer, 
	              							  	auftrag.Betrag, auftrag.Text, auftrag.Zeitstempel,
	              							  	auftrag.Empfänger.Name, auftrag.Empfänger.Kontonummer, auftrag.Empfänger.BLZ));
			return auftrag;
		}
	}
}

