package me.mert1602.advancedapi.tool;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ToolString {

	private ToolString() {}

	public static List<String> getStringParts(final String text, final String breakString){
		return Arrays.asList(text.split(breakString));
	}

	public static String removeString(final String text, final String string){
		return text.replaceAll(string, "");
	}

	public static String removeChars(final String text, final int amount){

		String t = text;

		for(int i = 0; i < amount; i++){
			t = t.substring(1);
		}

		return t;
	}

	public static boolean isChar(final String text, final int position, final char cha){

		if(text.charAt(position) == cha){
			return true;
		} else {
			return false;
		}

	}

	public static String getValue(final String text, final String breakPoint, final String category){

		String c = category + breakPoint;
		c = ToolString.removeString(text, c);

		return c;
	}

	public static String removeLastChar(final String text) {

		if ((text == null) || (text.length() == 0)) {
			return text;
		}

		return text.substring(0, text.length()-1);
	}

	public static List<String> replaceAll(final List<String> list, final String replaceText, final String to){

		final List<String> newList = new ArrayList<String>();

		for(final String s : list){
			newList.add(s.replaceAll(replaceText, to));
		}

		return newList;
	}

	public static String[] replaceAll(final String[] list, final String replaceText, final String to){
		return ToolString.replaceAll(Arrays.asList(list), replaceText, to).toArray(new String[0]);
	}

	public static String getNameWithoutExtension(final String string){

		String name = string;
		final int pos = name.lastIndexOf(".");
		if (pos > 0) {
			name = name.substring(0, pos);
		}

		return name;
	}

}
