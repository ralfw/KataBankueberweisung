using System;
using npantarhei.runtime.contract;
using bankueberweisung.console.umgebung.adapter;
using bankueberweisung.console.daten;

namespace bankueberweisung.console
{
	[InstanceOperations]
	public class Überweisung
	{
		private string _senderBLZ;
		private Senderkontodetails _senderkonto;
		private double _betrag;
		private string _text;
		private Kontoidentifikation _empfängerkonto;
		private string _tan;

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

		public string Empfaenger_eintragen(Tuple<string,string,string> name_ktonr_blz) {
			_empfängerkonto = new Kontoidentifikation {
										Name = name_ktonr_blz.Item1,
										Kontonummer = name_ktonr_blz.Item2,
										BLZ = name_ktonr_blz.Item3
									};
			return _empfängerkonto.BLZ;
		}


		public Tuple<string,string> TAN_generieren() {
			_tan = DateTime.Now.ToString("ddhhmmss");
			return new Tuple<string, string>(_senderkonto.Handynr, _tan);
		}


		public void TAN_pruefen(string tan, Action ok, Action<string> err) {
			if (tan == _tan)
				ok();
			else
				err("TAN passt nicht zur Überweisung!");
		}


		public Überweisungsauftrag Ueberweisungsauftrag_schnueren() {
			var auftrag = new Überweisungsauftrag {
				Sender = new Kontoidentifikation {
					Name = _senderkonto.Inhaber,
					Kontonummer = _senderkonto.Nummer,
					BLZ = _senderBLZ
				},
				Empfänger = new Kontoidentifikation {
					Name = _empfängerkonto.Name,
					Kontonummer = _empfängerkonto.Kontonummer,
					BLZ = _empfängerkonto.BLZ
				},
				Betrag = _betrag,
				Text = _text,

				Zeitstempel = DateTime.Now
			};

			return auftrag;
		}


		public Tuple<string, double> Neuen_Kontostand_berechnen() {
			return new Tuple<string,double>(_senderkonto.Nummer, _senderkonto.Stand - _betrag);
		}
	}
}

