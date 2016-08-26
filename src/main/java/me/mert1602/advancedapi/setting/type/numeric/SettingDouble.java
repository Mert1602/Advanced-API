package me.mert1602.advancedapi.setting.type.numeric;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.setting.Setting;
import me.mert1602.advancedapi.setting.SettingLanguage;
import me.mert1602.advancedapi.setting.SettingManager;
import me.mert1602.advancedapi.setting.SettingNumeric;
import me.mert1602.advancedapi.setting.SettingType;

public final class SettingDouble extends Setting<Double> implements SettingNumeric<Double> {

	public SettingDouble(final SettingManager<?> settingManager, final String name, final String path, final boolean hasLanguage, final Double defaultValue) {
		super(settingManager, name, path, SettingType.DOUBLE, hasLanguage, defaultValue);

		if(hasLanguage){

			for(final SettingLanguage lang : SettingLanguage.values()){
				this.getConfig().addDefault(lang.getPrePath() + path, defaultValue);
			}

		} else this.getConfig().addDefault(path, defaultValue);

	}

	@Override
	public Double get(final SettingLanguage lang) {

		this.loadConfig();

		try{
			return this.hasLanguage() ?
					this.getConfig().getDouble(lang.getPrePath() + this.getPath(), this.getDefaultObject()) :
						this.getConfig().getDouble(this.getPath(), this.getDefaultObject());
		}catch(final Throwable e){
			Log.error(e.getMessage());
			return this.getDefaultObject();
		}

	}



	@Override
	public void add(final Double value){
		this.set(this.get() + value);
	}

	@Override
	public void remove(final Double value){
		this.set(this.get() - value);
	}

	@Override
	public void multiply(final Double value){
		this.set(this.get() * value);
	}

	@Override
	public void divide(final Double value){
		this.set(this.get() / value);
	}

}
