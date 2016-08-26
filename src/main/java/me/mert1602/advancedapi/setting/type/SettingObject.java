package me.mert1602.advancedapi.setting.type;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.Setting;
import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;

public final class SettingObject extends Setting<Object> {

	public SettingObject(final SettingManager<?> settingManager, final String name, final String path, final boolean hasLanguage, final Object defaultValue) {
		super(settingManager, name, path, SettingType.OBJECT, hasLanguage, defaultValue);

		if (this.hasLanguage()) {

			for (final SettingLanguage lang : SettingLanguage.values()) {
				this.getConfig().addDefault(lang.getPrePath() + this.getPath(), defaultValue);
			}

		} else this.getConfig().addDefault(path, defaultValue);

	}

	@Override
	public Object get(final SettingLanguage type) {

		this.loadConfig();

		try {

			return this.hasLanguage() ?
					this.getConfig().get(type.getPrePath() + this.getPath(), this.getDefaultObject()) :
						this.getConfig().get(this.getPath(), this.getDefaultObject());

		} catch (final Throwable e) {
			Log.error(e.getMessage());
			return this.getDefaultObject();
		}

	}

}
