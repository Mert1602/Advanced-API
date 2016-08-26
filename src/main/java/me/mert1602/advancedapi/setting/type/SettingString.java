package me.mert1602.advancedapi.setting.type;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.Setting;
import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;

public class SettingString extends Setting<String> {

	public SettingString(final SettingManager<?> settingManager, final String name, final String path, final boolean hasLanguage, final String defaultValue) {
		super(settingManager, name, path, SettingType.STRING, hasLanguage, defaultValue);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {
				this.getConfig().addDefault(lang.getPrePath() + this.getPath(), defaultValue);
			}

		} else this.getConfig().addDefault(path, defaultValue);

	}

	@Override
	public String get(final SettingLanguage lang) {

		this.loadConfig();

		try {

			return this.hasLanguage()
					? this.getConfig().getString(lang.getPrePath() + this.getPath(), this.getDefaultObject()).replace('&', '§')
							: this.getConfig().getString(this.getPath(), this.getDefaultObject()).replace('&', '§');

		} catch (final Throwable e) {
			Log.error(e.getMessage());
			return this.getDefaultObject();
		}

	}

	@Override
	public void set(String t, SettingLanguage lang) {

		if(this.hasLanguage()){

			this.getConfig().set(lang.getPrePath() + this.getPath(), t.replace('§', '&'));

		}else{

			this.getConfig().set(this.getPath(), t.replace('§', '&'));

		}

		this.saveConfig();

	}

}
