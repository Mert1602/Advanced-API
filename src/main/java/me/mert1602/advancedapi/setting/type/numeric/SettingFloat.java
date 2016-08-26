package me.mert1602.advancedapi.setting.type.numeric;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.Setting;
import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingNumeric;
import me.mert1602.advancedapi.setting.SettingType;

public class SettingFloat extends Setting<Float> implements SettingNumeric<Float> {

	public SettingFloat(final SettingManager<?> settingManager, final String name, final String path, final boolean hasLanguage, final Float defaultValue) {
		super(settingManager, name, path, SettingType.FLOAT, hasLanguage, defaultValue);

		if(hasLanguage){

			for(final SettingLanguage lang : SettingLanguage.values()){
				this.getConfig().addDefault(lang.getPrePath() + path, defaultValue);
			}

		} else this.getConfig().addDefault(path, defaultValue);

	}

	@Override
	public Float get(final SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ?
					Float.valueOf(Double.valueOf(this.getConfig().getDouble(lang.getPrePath() + this.getPath(), Double.valueOf(this.getDefaultObject()))).toString()) :
						Float.valueOf(Double.valueOf(this.getConfig().getDouble(this.getPath(), Double.valueOf(this.getDefaultObject()))).toString());
		}catch(final Throwable e){
			Log.error(e.getMessage());
			return this.getDefaultObject();
		}

	}



	@Override
	public void add(final Float value) {
		this.set(this.get() + value);
	}

	@Override
	public void remove(final Float value) {
		this.set(this.get() - value);
	}

	@Override
	public void multiply(final Float value) {
		this.set(this.get() * value);
	}

	@Override
	public void divide(final Float value) {
		this.set(this.get() / value);
	}

}
