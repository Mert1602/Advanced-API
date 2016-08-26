package me.mert1602.advancedapi.setting.type.list.numeric;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public class SettingShortList extends SettingList<Short> {

	public SettingShortList(SettingManager<?> settingManager, String name, String path, boolean hasLanguage, List<Short> defaultObject) {
		super(settingManager, name, path, SettingType.SHORTLIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<Short> newDefaultValue = new ArrayList<Short>();

				for(final Short i : defaultObject){
					newDefaultValue.add(i);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public Short[] getArray(SettingLanguage lang) {
		return this.get().toArray(Tool.EMPTY_SHORT_LIST);
	}

	@Override
	public List<Short> get(SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ? this.getConfig().getShortList(lang.getPrePath() + this.getPath()) : this.getConfig().getShortList(this.getPath());
		}catch(Throwable e){
			return this.getDefaultObject();
		}

	}

}
