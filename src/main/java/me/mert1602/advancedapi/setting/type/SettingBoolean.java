package me.mert1602.advancedapi.setting.type;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.Setting;
import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingType;

public final class SettingBoolean extends Setting<Boolean> {

	public SettingBoolean(final SettingManager<?> settingManager, final String name, final String path, final boolean hasLanguage, final Boolean defaultValue) {
		super(settingManager, name, path, SettingType.BOOLEAN, hasLanguage, defaultValue);

		if(hasLanguage){

			for(final SettingLanguage lang : SettingLanguage.values()){
				this.getConfig().addDefault(lang.getPrePath() + path, defaultValue);
			}

		} else  this.getConfig().addDefault(path, defaultValue);

	}

	@Override
	public Boolean get(final SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ?
					this.getConfig().getBoolean(lang.getPrePath() + this.getPath(), this.getDefaultObject()) :
						this.getConfig().getBoolean(this.getPath(), this.getDefaultObject());
		}catch(final Throwable e){
			Log.error(e.getMessage());
			return this.getDefaultObject();
		}

	}



	public void switchValue(){
		this.set(!this.get());
	}

}
