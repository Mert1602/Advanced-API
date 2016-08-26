package me.mert1602.advancedapi.setting;

import java.io.File;

import lombok.Getter;
import me.mert1602.advancedapi.Content;
import me.mert1602.advancedapi.config.file.FileConfiguration;
import me.mert1602.advancedapi.config.file.YamlConfiguration;
import me.mert1602.advancedapi.tool.ToolFile;

public class SettingFileContent extends Content<SettingManager<?>> {

	@Getter private File file;
	@Getter private FileConfiguration config;
	
	public SettingFileContent(String fileName) {
		this((SettingManager<?>)null, fileName);
	}
	
	public SettingFileContent(SettingManager<?> content, String fileName) {
		this(content, new File(fileName));
	}
	
	public SettingFileContent(String path, String fileName) {
		this(null, path, fileName);
	}
	
	public SettingFileContent(SettingManager<?> content, String path, String fileName) {
		this(content, new File(path, fileName));
	}
	
	public SettingFileContent(final File file) {
		this(null, file);
	}
	
	public SettingFileContent(SettingManager<?> content, File file) {
		super(content);
		
		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(this.file);

	}

	
	
	public void setFile(File file){

		this.file = file;
		this.config = YamlConfiguration.loadConfiguration(this.file);

	}

	
	
	public void saveConfig() {
		ToolFile.saveConfig(this.config, this.file);
	}

	public void loadConfig() {
		ToolFile.loadConfig(this.config, this.file);
	}

}
