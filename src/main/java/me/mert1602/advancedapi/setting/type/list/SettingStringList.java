package me.mert1602.advancedapi.setting.type.list;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public final class SettingStringList extends SettingList<String> {

	public SettingStringList(final SettingManager<?> settingManager, final String name, final String path, final boolean hasLanguage, final List<String> defaultObject) {
		super(settingManager, name, path, SettingType.STRINGLIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<String> newDefaultValue = new ArrayList<String>();

				for(final String s : defaultObject){
					newDefaultValue.add(s);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public String[] getArray(final SettingLanguage lang) {
		return this.get(lang).toArray(Tool.EMPTY_STRING_LIST);
	}

	@Override
	public List<String> get(final SettingLanguage lang) {

		this.loadConfig();

		try {

			if (this.hasLanguage()) {

				return this.getConfig().getStringList(lang.getPrePath() + this.getPath());

			} else {

				return this.getConfig().getStringList(this.getPath());

			}

		} catch (final Throwable e) {
			Log.error(e.getMessage());
			return this.getDefaultObject();
		}

	}

}
