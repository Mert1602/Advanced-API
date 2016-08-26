package me.mert1602.advancedapi.setting.type.list.numeric;

import java.util.ArrayList;
import java.util.List;

import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingList;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;
import me.mert1602.advancedapi.tool.Tool;

public class SettingByteList extends SettingList<Byte> {

	public SettingByteList(SettingManager<?> settingManager, String name, String path, boolean hasLanguage, List<Byte> defaultObject) {
		super(settingManager, name, path, SettingType.BYTELIST, hasLanguage, defaultObject);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {

				final List<Byte> newDefaultValue = new ArrayList<Byte>();

				for(final Byte i : defaultObject){
					newDefaultValue.add(i);
				}

				this.getConfig().addDefault(lang.getPrePath() + path, newDefaultValue);

			}

		} else this.getConfig().addDefault(path, defaultObject);

	}

	@Override
	public Byte[] getArray(SettingLanguage lang) {
		return this.get().toArray(Tool.EMPTY_BYTE_LIST);
	}

	@Override
	public List<Byte> get(SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ? this.getConfig().getByteList(lang.getPrePath() + this.getPath()) : this.getConfig().getByteList(this.getPath());
		}catch(Throwable e){
			return this.getDefaultObject();
		}

	}

}
