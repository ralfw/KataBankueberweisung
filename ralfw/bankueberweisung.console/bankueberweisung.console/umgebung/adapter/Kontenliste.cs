using System;
using System.IO;
using System.Linq;
using npantarhei.runtime.contract;
using bankueberweisung.console.daten;

namespace bankueberweisung.console.umgebung.adapter
{
	[InstanceOperations]
	public class Kontenliste
	{
		private const string DB_FILENAME = "db/Kontenliste.txt";


		public void Konto_laden(Tuple<string,string> name_kontonummer, Action<Senderkontodetails> senderGefunden, Action<string> unbekannterSender) {
			var detailsRec = File.ReadAllLines(DB_FILENAME)
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


		public void Kontenliste_aktualisieren(Tuple<string,double> senderktonr_neuerBetrag) {
			var detailsRec = File.ReadAllLines(DB_FILENAME)
								 .Select(l => l.Split('\t'))
								 .Select(d => new {kontonummer = d[1], details = d});

			File.Delete(DB_FILENAME);
			detailsRec.Where(r => r.kontonummer != senderktonr_neuerBetrag.Item1)
					  .Select(r => string.Join("\t", r.details))
					  .ToList()
					  .ForEach(l => File.AppendAllText(DB_FILENAME, l + "\n"));

			var senderDetails = detailsRec.First(r => r.kontonummer == senderktonr_neuerBetrag.Item1).details;
			senderDetails[2] = senderktonr_neuerBetrag.Item2.ToString();
			File.AppendAllText(DB_FILENAME, string.Join("\t", senderDetails) + "\n");
		}
	}
}

