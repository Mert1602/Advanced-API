package me.mert1602.advancedapi.setting.type.list.numeric;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public class SettingFloatList extends SettingList<Float> {

	public SettingFloatList(SettingManager<?> settingManager, String name, String path, boolean hasLanguage, List<Float> defaultObject) {
		super(settingManager, name, path, SettingType.FLOATLIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<Float> newDefaultValue = new ArrayList<Float>();

				for(final Float i : defaultObject){
					newDefaultValue.add(i);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public Float[] getArray(SettingLanguage lang) {
		return this.get().toArray(Tool.EMPTY_FLOAT_LIST);
	}

	@Override
	public List<Float> get(SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ? this.getConfig().getFloatList(lang.getPrePath() + this.getPath()) : this.getConfig().getFloatList(this.getPath());
		}catch(Throwable e){
			return this.getDefaultObject();
		}

	}

}
