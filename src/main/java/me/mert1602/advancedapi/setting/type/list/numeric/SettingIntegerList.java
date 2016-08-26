package me.mert1602.advancedapi.setting.type.list.numeric;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public class SettingIntegerList extends SettingList<Integer> {

	public SettingIntegerList(SettingManager<?> settingManager, String name, String path, boolean hasLanguage, List<Integer> defaultObject) {
		super(settingManager, name, path, SettingType.INTEGERLIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<Integer> newDefaultValue = new ArrayList<Integer>();

				for(final Integer i : defaultObject){
					newDefaultValue.add(i);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public Integer[] getArray(SettingLanguage lang) {
		return this.get().toArray(Tool.EMPTY_INTEGER_LIST);
	}

	@Override
	public List<Integer> get(SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ? this.getConfig().getIntegerList(lang.getPrePath() + this.getPath()) : this.getConfig().getIntegerList(this.getPath());
		}catch(Throwable e){
			return this.getDefaultObject();
		}

	}

}
