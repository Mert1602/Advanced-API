package me.mert1602.advancedapi.setting;

import lombok.Getter;

public enum SettingLanguage {

	EN("EN."),
	ES("ES."),
	DE("DE."),;

	@Getter private String prePath;

	private SettingLanguage(final String prePath){
		this.prePath = prePath;
	}

	public static SettingLanguage getDefaultLanguage(){
		return EN;
	}

}
