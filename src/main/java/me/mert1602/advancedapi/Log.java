package me.mert1602.advancedapi;

public class Log {

	private Log(){}

	public static void info(String... strings){
		Log.log("Info", strings);
	}

	public static void error(String... strings){
		Log.log("Error", strings);
	}

	public static void debug(String... strings){
		Log.log("Debug", strings);
	}

	public static void exception(Throwable throwable, Class<?> clazz, int line){
		Log.log("Error", "'" + throwable.getMessage() + "' in '" + clazz.getName() + "' at line '" + line + "'");
	}



	public static void log(String prefix, String... strings){

		for(String s : strings){
			System.out.println("[" + prefix + "] " + s);
		}

	}

}
