package me.mert1602.advancedapi.setting;

import lombok.Getter;
import lombok.experimental.Accessors;
import me.mert1602.advancedapi.Content;
import me.mert1602.advancedapi.config.file.FileConfiguration;

public abstract class Setting<T> extends Content<SettingManager<?>> {

	@Getter private final String name;
	@Getter private final String path;
	@Getter private final SettingType type;

	@Getter @Accessors(fluent = true) private final boolean hasLanguage;
	@Getter private final T defaultObject;

	public Setting(final SettingManager<?> settingManager, final String name, final String path,
			final SettingType type, final boolean hasLanguage, final T defaultObject) {
		super(settingManager);

		this.name = name;
		this.path = path;
		this.type = type;

		this.hasLanguage = hasLanguage;
		this.defaultObject = defaultObject;

	}



	public final T get(){
		return this.get(SettingLanguage.getDefaultLanguage());
	}

	public abstract T get(SettingLanguage lang);



	public final void set(final T t){
		this.set(t, SettingLanguage.getDefaultLanguage());
	}

	public void set(final T t, final SettingLanguage lang){

		if(this.hasLanguage()){
			this.getConfig().set(lang.getPrePath() + this.getPath(), t);
		}else{
			this.getConfig().set(this.getPath(), t);
		}

		this.saveConfig();

	}



	public final FileConfiguration getConfig(){
		return this.getContent().getFileContent().getConfig();
	}

	public final void saveConfig(){
		this.getContent().getFileContent().saveConfig();
	}

	public final void loadConfig(){
		this.getContent().getFileContent().loadConfig();
	}

}
