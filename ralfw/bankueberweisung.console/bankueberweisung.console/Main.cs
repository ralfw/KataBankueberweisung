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
				//fr.Message += Console.WriteLine;

				fr.Process(".run");
				fr.WaitForResult();
			}
		}


		public static string HeimBLZ() {
			return "20050550";
		}
	}
}
