package me.mert1602.advancedapi.tool;

public class ToolNumber {

	private ToolNumber() {}

	public static boolean isEvenNumber(final int number){
		return ToolNumber.isDivisible(number, 2);
	}

	public static boolean isDivisible(int value, int target){
		return (value % target) == 0;
	}

	public static boolean isNumeric(final String text){

		try{
			Double.parseDouble(text);
			return true;
		}catch(final Throwable e){
			return false;
		}

	}

	public static int fetchInteger(final String string){

		final int length = string.length();
		String result = "";

		for (int i = 0; i < length; i++) {
			final Character character = string.charAt(i);
			if (Character.isDigit(character)) {
				result += character;
			}
		}

		if(ToolNumber.isNumeric(result)){
			final int number = Integer.parseInt(result);
			return number;
		}

		return 0;
	}

	public static double randomDouble(final double min, final double max){
		return min + ((max - min) * Tool.getRandom().nextDouble());
	}
	
	
	
	public static int getPI(float value, float max){
		return ToolNumber.getPI(value, max, 100);
	}
	
	public static int getPI(float value, float max, float maxPercent){
		return Integer.valueOf((100 * Math.round(maxPercent)) / ((100 * Math.round(max)) / Math.round(value)));
	}
	
	
	
	public static long getPL(float value, float max){
		return ToolNumber.getPL(value, max, 100L);
	}
	
	public static long getPL(double value, double max, double maxPercent){
		return Long.valueOf((100L * Math.round(maxPercent)) / ((100L * Math.round(max)) / Math.round(value)));
	}
	
	
	
	public static float getPF(float value, float max){
		return ToolNumber.getPF(value, max, 100F);
	}
	
	public static float getPF(float value, float max, float maxPercent){
		return Float.valueOf((100F * maxPercent) / ((100F * max) / value));
	}
	
	
	
	public static double getPD(double value, double max){
		return ToolNumber.getPD(value, max, 100D);
	}
	
	public static double getPD(double value, double max, double maxPercent){
		return Double.valueOf((100D * maxPercent) / ((100D * max) / value));
	}

}
