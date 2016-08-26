package me.mert1602.advancedapi.setting.type.list;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public class SettingBooleanList extends SettingList<Boolean> {

	public SettingBooleanList(SettingManager<?> settingManager, String name, String path, boolean hasLanguage, List<Boolean> defaultObject) {
		super(settingManager, name, path, SettingType.BOOLEANLIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<Boolean> newDefaultValue = new ArrayList<Boolean>();

				for(final Boolean i : defaultObject){
					newDefaultValue.add(i);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public Boolean[] getArray(SettingLanguage lang) {
		return this.get().toArray(Tool.EMPTY_BOOLEAN_LIST);
	}

	@Override
	public List<Boolean> get(SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ? this.getConfig().getBooleanList(lang.getPrePath() + this.getPath()) : this.getConfig().getBooleanList(this.getPath());
		}catch(Throwable e){
			return this.getDefaultObject();
		}

	}

}
