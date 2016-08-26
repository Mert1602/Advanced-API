package me.mert1602.advancedapi.setting.type.list.numeric;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public class SettingLongList extends SettingList<Long> {

	public SettingLongList(SettingManager<?> settingManager, String name, String path, boolean hasLanguage, List<Long> defaultObject) {
		super(settingManager, name, path, SettingType.LONGLIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<Long> newDefaultValue = new ArrayList<Long>();

				for(final Long i : defaultObject){
					newDefaultValue.add(i);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public Long[] getArray(SettingLanguage lang) {
		return this.get().toArray(Tool.EMPTY_LONG_LIST);
	}

	@Override
	public List<Long> get(SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ? this.getConfig().getLongList(lang.getPrePath() + this.getPath()) : this.getConfig().getLongList(this.getPath());
		}catch(Throwable e){
			return this.getDefaultObject();
		}

	}

}
