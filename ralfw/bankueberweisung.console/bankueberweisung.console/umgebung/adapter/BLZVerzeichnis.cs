using System;
using System.Linq;
using System.IO;
using npantarhei.runtime.contract;

namespace bankueberweisung.console.umgebung.adapter
{
	[InstanceOperations]
	public class BLZVerzeichnis
	{
		public void Bankname_ermitteln(string blz, Action<Tuple<string,string>> bankGefunden, Action<string> unbekannteBLZ) {
			var blzRec = File.ReadAllLines(@"db/Bankleitzahlen.txt")
							   .Select(l => l.Split('\t'))
							   .FirstOrDefault(rec => rec[0] == blz);
					           
			if (blzRec != null)
				bankGefunden(new Tuple<string,string>(blz, blzRec[1]));
			else
				unbekannteBLZ("Unbekannte BLZ! Bankname konnte nicht ermittelt werden.");
		}
	}
}

