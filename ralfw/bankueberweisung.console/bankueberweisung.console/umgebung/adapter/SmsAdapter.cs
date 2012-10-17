using System;
using npantarhei.runtime.contract;

namespace bankueberweisung.console
{
	[InstanceOperations]
	public class SmsAdapter
	{
		public void TAN_versenden(Tuple<string,string> handynr_TAN) {
			Console.ForegroundColor = ConsoleColor.Yellow;
				Console.WriteLine("  Simulation: TAN {0} versenden an {1}", handynr_TAN.Item2, handynr_TAN.Item1);
			Console.ResetColor();
		}
	}
}

