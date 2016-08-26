package me.mert1602.advancedapi.setting;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import lombok.Getter;
import me.mert1602.advancedapi.basic.BasicContentManager;
import me.mert1602.advancedapi.setting.type.SettingBoolean;
import me.mert1602.advancedapi.setting.type.SettingObject;
import me.mert1602.advancedapi.setting.type.SettingString;
import me.mert1602.advancedapi.setting.type.list.SettingBooleanList;
import me.mert1602.advancedapi.setting.type.list.SettingStringList;
import me.mert1602.advancedapi.setting.type.list.numeric.SettingByteList;
import me.mert1602.advancedapi.setting.type.list.numeric.SettingDoubleList;
import me.mert1602.advancedapi.setting.type.list.numeric.SettingFloatList;
import me.mert1602.advancedapi.setting.type.list.numeric.SettingIntegerList;
import me.mert1602.advancedapi.setting.type.list.numeric.SettingLongList;
import me.mert1602.advancedapi.setting.type.list.numeric.SettingShortList;
import me.mert1602.advancedapi.setting.type.numeric.SettingDouble;
import me.mert1602.advancedapi.setting.type.numeric.SettingFloat;
import me.mert1602.advancedapi.setting.type.numeric.SettingInteger;
import me.mert1602.advancedapi.setting.type.numeric.SettingLong;

public abstract class SettingManager<C> extends BasicContentManager<Setting<?>, C> implements ISettingManager<C> {

	private static List<SettingManager<?>> settingManagers = new ArrayList<SettingManager<?>>();

	public static List<SettingManager<?>> getSettingManagers(){
		return Collections.unmodifiableList(new ArrayList<SettingManager<?>>(SettingManager.settingManagers));
	}



	@Getter private SettingFileContent fileContent;
	private SettingTool tool;

	public SettingManager(final C basic, final String file) {
		this(basic, file, true);
	}
	
	public SettingManager(final C basic, final String file, boolean autoRepair) {
		this(basic, new File(file), autoRepair);
	}
	
	public SettingManager(final C basic, final String path, final String file) {
		this(basic, path, file, true);
	}
	
	public SettingManager(final C basic, final String path, final String file, boolean autoRepair) {
		this(basic, new File(path, file), autoRepair);
	}
	
	public SettingManager(final C basic, final File file) {
		this(basic, file, true);
	}
	
	public SettingManager(final C basic, final File file, boolean autoRepair) {
		this(basic, new SettingFileContent(null, file), autoRepair);
	}

	public SettingManager(final C basic, final SettingFileContent fileContent, final boolean autoRepair) {
		super(basic);

		SettingType.getTypes();
		
		this.fileContent = fileContent;
		this.tool = new SettingTool();

		if(autoRepair){

			this.addDefault();
			this.repair();

		}

		SettingManager.settingManagers.add(this);

	}
	


	@SuppressWarnings("unchecked")
	@Override
	public final <T extends Setting<?>> T getSetting(final String name){

		for(final Setting<?> setting : this.getList()){

			if(setting.getName().equalsIgnoreCase(name) == false) continue;

			return (T) setting;

		}

		return null;
	}



	@Override
	public final SettingBoolean getBoolean(final String name){
		return this.getSetting(name) != null ?
				(SettingBoolean) this.getSetting(name) :
					new SettingBoolean(this, name, name, false, Boolean.valueOf(false));
	}

	@Override
	public final SettingObject getObject(final String name){
		return this.getSetting(name) != null ?
				(SettingObject) this.getSetting(name) :
					new SettingObject(this, name, name, false, new Object());
	}

	@Override
	public final SettingString getString(final String name){
		return this.getSetting(name) != null ?
				(SettingString) this.getSetting(name) :
					new SettingString(this, name, name, false, "DEFAULT NOT EXISTS");
	}



	@Override
	public final SettingDouble getDouble(final String name){
		return this.getSetting(name) != null ?
				(SettingDouble) this.getSetting(name) :
					new SettingDouble(this, name, name, false, Double.valueOf(0.0D));
	}

	@Override
	public final SettingFloat getFloat(final String name){
		return this.getSetting(name) != null ?
				(SettingFloat) this.getSetting(name) :
					new SettingFloat(this, name, name, false, Float.valueOf(0.0F));
	}

	@Override
	public final SettingInteger getInteger(final String name){
		return this.getSetting(name) != null ?
				(SettingInteger) this.getSetting(name) :
					new SettingInteger(this, name, name, false, Integer.valueOf(0));
	}

	@Override
	public final SettingLong getLong(final String name) {
		return this.getSetting(name) != null ?
				(SettingLong) this.getSetting(name) :
					new SettingLong(this, name, name, false, Long.valueOf(0L));
	}



	@Override
	public final SettingBooleanList getBooleanList(String name) {
		return this.getSetting(name) != null ?
				(SettingBooleanList) this.getSetting(name) :
					new SettingBooleanList(this, name, name, false, Arrays.asList(Boolean.valueOf(false)));
	}

	@Override
	public final SettingStringList getStringList(final String name){
		return this.getSetting(name) != null ?
				(SettingStringList) this.getSetting(name) :
					new SettingStringList(this, name, name, false, Arrays.asList("DEFAULT NOT EXISTS"));
	}



	@Override
	public final SettingByteList getByteList(String name) {
		return this.getSetting(name) != null ?
				(SettingByteList) this.getSetting(name) :
					new SettingByteList(this, name, name, false, Arrays.asList(Byte.valueOf((byte)0)));
	}

	@Override
	public final SettingDoubleList getDoubleList(String name) {
		return this.getSetting(name) != null ?
				(SettingDoubleList) this.getSetting(name) :
					new SettingDoubleList(this, name, name, false, Arrays.asList(Double.valueOf(0)));
	}

	@Override
	public final SettingFloatList getFloatList(String name) {
		return this.getSetting(name) != null ?
				(SettingFloatList) this.getSetting(name) :
					new SettingFloatList(this, name, name, false, Arrays.asList(Float.valueOf(0)));
	}

	@Override
	public final SettingIntegerList getIntegerList(String name) {
		return this.getSetting(name) != null ?
				(SettingIntegerList) this.getSetting(name) :
					new SettingIntegerList(this, name, name, false, Arrays.asList(Integer.valueOf(0)));
	}

	@Override
	public final SettingLongList getLongList(String name) {
		return this.getSetting(name) != null ?
				(SettingLongList) this.getSetting(name) :
					new SettingLongList(this, name, name, false, Arrays.asList(Long.valueOf(0L)));
	}

	@Override
	public final SettingShortList getShortList(String name) {
		return this.getSetting(name) != null ?
				(SettingShortList) this.getSetting(name) :
					new SettingShortList(this, name, name, false, Arrays.asList(Short.valueOf((short)0)));
	}



	@Override
	public final Setting<?> addSetting(final String path, final Object defaultObject){
		return this.addSetting(path, defaultObject, false);
	}

	@Override
	public final Setting<?> addSetting(final String path, final Object defaultObject, final boolean hasLanguage){
		return this.addSetting(path, path, defaultObject, hasLanguage);
	}

	@Override
	public final Setting<?> addSetting(final String name, final String path, final Object defaultObject){
		return this.addSetting(name, path, defaultObject, false);
	}

	@Override
	public final Setting<?> addSetting(final String name, final String path, final Object defaultObject, final boolean hasLanguage){

		if(this.getSetting(name) != null) return null;

		return this.register(this.tool.getSetting(this, name, path, hasLanguage, defaultObject));

	}

	
	
	@Override
	public final void removeSetting(final String name){

		if(this.getSetting(name) == null) {
			return;
		}

		this.unregister(this.getSetting(name));

	}
	
	
	
	@Override
	public abstract void addDefault();
	
	@Override
	public final void repair(){

		this.fileContent.loadConfig();
		this.fileContent.getConfig().options().copyHeader(true);
		this.fileContent.getConfig().options().copyDefaults(true);
		this.fileContent.saveConfig();

	}

}
