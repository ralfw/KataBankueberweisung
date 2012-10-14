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
		private double _betrag;
		private string _text;

		public void SenderBLZ_eintragen(string blz) {
			_senderBLZ = blz;
		}

		public void Sender_eintragen(Senderkontodetails konto) {
			_senderkonto = konto;
		}

		public void Bewegung_eintragen(Tuple<double,string> bewegung) {
			_betrag = bewegung.Item1;
			_text = bewegung.Item2;
		}


		public void Limit_eingehalten(Tuple<double,string> bewegung, Action<Tuple<double,string>> ok, Action<string> err) {
			if (bewegung.Item1 <= _senderkonto.Limit)
				ok(bewegung);
			else
				err(string.Format("Der Überweisungsbetrag {0} EUR überschreitet das Limit von {1} EUR!", bewegung.Item1, _senderkonto.Limit));
		}

		public void Konto_gedeckt(Tuple<double,string> bewegung, Action<Tuple<double,string>> ok, Action<string> err) {
			if (bewegung.Item1 <= _senderkonto.Stand)
				ok(bewegung);
			else
				err(string.Format("Der Überweisungsbetrag {0} EUR ist höher als der Kontostand von {1} EUR!", bewegung.Item1, _senderkonto.Stand));
		}
	}
}

