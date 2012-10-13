using System;
using npantarhei.runtime;
using npantarhei.runtime.operations;
using npantarhei.runtime.config;
using System.Reflection;
using npantarhei.runtime.contract;

namespace bankueberweisung.console
{
	[StaticOperations]
	class MainClass
	{
		public static void Main (string[] args)
		{
			var config = new FlowRuntimeConfiguration()
								.AddStreamsFrom("bankueberweisung.console.root.flow", Assembly.GetExecutingAssembly())
								.AddOperations(new AssemblyCrawler(Assembly.GetExecutingAssembly()));
			using(var fr = new FlowRuntime(config)) {
				fr.Message += Console.WriteLine;

				fr.Process(".run");
				fr.WaitForResult();
			}
		}


		public static string HeimBLZ() {
			return "20050550";
		}
	}


	[StaticOperations]
	class DummyOperations {
		public static void Phase2_Bewegung_pruefen(Tuple<double,string> betrag_text) {
			Log("Bewegung geprüft: betrag={0}, text={1}", betrag_text.Item1, betrag_text.Item2);
		}

		public static string Phase3_Empfaenger_pruefen(Tuple<string,string,string> empfänger_name_ktonr_blz) {
			Log("Empfänger geprüft!");
			return "30060660";
		}

		public static void Phase4_TAN_versenden(bool geprüft) {
			Log("TAN versandt!");
		}

		public static void Phase5_Ueberweisung_durchfuehren(string TAN) {
			Log("TAN geprüft und Überweisung ausgeführt!");
		}

		private static void Log(string message, params object[] values) {
			Console.ForegroundColor = ConsoleColor.Yellow;
			
			Console.WriteLine("  {0}", string.Format(message, values));
			
			Console.ForegroundColor = ConsoleColor.Black;
		}
	}
}
