using System;
using npantarhei.runtime.contract;

namespace bankueberweisung.console.umgebung.adapter
{
	[InstanceOperations]
	public class BLZVerzeichnis
	{
		public Tuple<string,string> Bankname_ermitteln(string blz) {
			return new Tuple<string,string>(blz, "Bankname zu " + blz);
		}
	}
}

