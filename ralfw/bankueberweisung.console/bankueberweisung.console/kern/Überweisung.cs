using System;
using npantarhei.runtime.contract;
using bankueberweisung.console.umgebung.adapter;

namespace bankueberweisung.console
{
	[InstanceOperations]
	public class Überweisung
	{
		private string _senderBLZ;
		private Senderkontodetails _senderkonto;

		public void SenderBLZ_eintragen(string blz) {
			_senderBLZ = blz;
		}

		public void Sender_eintragen(Senderkontodetails konto) {
			_senderkonto = konto;
		}
	}
}

