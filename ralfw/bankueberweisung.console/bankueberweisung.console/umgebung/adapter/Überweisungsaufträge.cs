using System;
using bankueberweisung.console.daten;
using npantarhei.runtime.contract;
using System.IO;

namespace bankueberweisung.console
{
	[InstanceOperations]
	public class Überweisungsaufträge
	{
		public Überweisungsauftrag Beauftragen(Überweisungsauftrag auftrag) {
			File.AppendAllText("db/Überweisungsaufträge.txt",
			                   string.Format("{0}\t{1}\t{2}\t{3}\t{4}\t{5:dd.MM.yy hh:mm}\t{6}\t{7}\t{8}\n",
			              						auftrag.Empfänger.BLZ, auftrag.Empfänger.Kontonummer, auftrag.Empfänger.Name,
			              						auftrag.Betrag, auftrag.Text, auftrag.Zeitstempel,
			              						auftrag.Sender.BLZ, auftrag.Sender.Kontonummer, auftrag.Sender.Name));
			return auftrag;
		}
	}
}

