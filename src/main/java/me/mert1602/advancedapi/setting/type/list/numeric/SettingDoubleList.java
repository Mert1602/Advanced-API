package me.mert1602.advancedapi.setting.type.list.numeric;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public class SettingDoubleList extends SettingList<Double> {

	public SettingDoubleList(SettingManager<?> settingManager, String name, String path, boolean hasLanguage, List<Double> defaultObject) {
		super(settingManager, name, path, SettingType.DOUBLELIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<Double> newDefaultValue = new ArrayList<Double>();

				for(final Double i : defaultObject){
					newDefaultValue.add(i);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public Double[] getArray(SettingLanguage lang) {
		return this.get().toArray(Tool.EMPTY_DOUBLE_LIST);
	}

	@Override
	public List<Double> get(SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ? this.getConfig().getDoubleList(lang.getPrePath() + this.getPath()) : this.getConfig().getDoubleList(this.getPath());
		}catch(Throwable e){
			return this.getDefaultObject();
		}

	}

}
