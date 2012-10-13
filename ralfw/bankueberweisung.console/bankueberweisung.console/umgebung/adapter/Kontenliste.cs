using System;
using npantarhei.runtime.contract;

namespace bankueberweisung.console.umgebung.adapter
{
	[InstanceOperations]
	public class Kontenliste
	{
		public Senderkontodetails Konto_laden(Tuple<string,string> name_kontonummer) {
			return new Senderkontodetails{
				Inhaber = name_kontonummer.Item1,
				Nummer = name_kontonummer.Item2
			};
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

