package me.mert1602.advancedapi.setting.type.numeric;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.Setting;
import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingNumeric;
import me.mert1602.advancedapi.setting.SettingType;

public class SettingLong extends Setting<Long> implements SettingNumeric<Long> {

	public SettingLong(final SettingManager<?> settingManager, final String name, final String path, final boolean hasLanguage, final Long defaultValue) {
		super(settingManager, name, path, SettingType.LONG, hasLanguage, defaultValue);

		if(hasLanguage){

			for(final SettingLanguage lang : SettingLanguage.values()){
				this.getConfig().addDefault(lang.getPrePath() + path, defaultValue);
			}

		} else this.getConfig().addDefault(path, defaultValue);

	}

	@Override
	public Long get(final SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ?
					this.getConfig().getLong(lang.getPrePath() + this.getPath(), this.getDefaultObject()) :
						this.getConfig().getLong(this.getPath(), this.getDefaultObject());
		}catch(final Throwable e){
			Log.error(e.getMessage());
			return this.getDefaultObject();
		}

	}



	@Override
	public void add(final Long value){
		this.set(this.get() + value);
	}

	@Override
	public void remove(final Long value){
		this.set(this.get() - value);
	}

	@Override
	public void multiply(final Long value){
		this.set(this.get() * value);
	}

	@Override
	public void divide(final Long value){
		this.set(this.get() / value);
	}

}
