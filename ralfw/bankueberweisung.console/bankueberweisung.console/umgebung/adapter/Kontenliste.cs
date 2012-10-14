using System;
using System.IO;
using System.Linq;
using npantarhei.runtime.contract;

namespace bankueberweisung.console.umgebung.adapter
{
	[InstanceOperations]
	public class Kontenliste
	{
		public void Konto_laden(Tuple<string,string> name_kontonummer, Action<Senderkontodetails> senderGefunden, Action<string> unbekannterSender) {
			var detailsRec = File.ReadAllLines("db/Kontenliste.txt")
								 .Select(l => l.Split('\t'))
								 .FirstOrDefault(rec => rec[0].ToLower()==name_kontonummer.Item1.ToLower () && 
					                             rec[1]==name_kontonummer.Item2);

			if (detailsRec != null)
				senderGefunden(new Senderkontodetails(){
															Inhaber = name_kontonummer.Item1,
															Nummer = name_kontonummer.Item2,
															Stand = double.Parse(detailsRec[2]),
															Limit = double.Parse(detailsRec[3]),
															Handynr = detailsRec[4]
													   });
			else
				unbekannterSender("Unbekanntes Bestandskonto! Name und/oder Kontonummer falsch.");
		}
	}

	public class Senderkontodetails {
		public string Inhaber;
		public string Nummer;
		public double Stand;
		public double Limit;
		public string Handynr;
	}
}

