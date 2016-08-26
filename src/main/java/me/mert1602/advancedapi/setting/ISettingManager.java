package me.mert1602.advancedapi.setting;

import me.mert1602.advancedapi.basic.ContentManager;
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

public interface ISettingManager<C> extends ContentManager<Setting<?>, C> {

	public SettingFileContent getFileContent();



	public <T extends Setting<?>> T getSetting(String name);



	public SettingBoolean getBoolean(String name);

	public SettingObject getObject(String name);

	public SettingString getString(String name);



	public SettingDouble getDouble(String name);

	public SettingFloat getFloat(String name);

	public SettingInteger getInteger(String name);

	public SettingLong getLong(String name);



	public SettingBooleanList getBooleanList(String name);

	public SettingStringList getStringList(String name);



	public SettingByteList getByteList(String name);

	public SettingDoubleList getDoubleList(String name);

	public SettingFloatList getFloatList(String name);

	public SettingIntegerList getIntegerList(String name);

	public SettingLongList getLongList(String name);

	public SettingShortList getShortList(String name);



	public Setting<?> addSetting(String path, Object defaultObject);

	public Setting<?> addSetting(String name, String path, Object defaultObject);

	public Setting<?> addSetting(String path, Object defaultObject, boolean hasLanguage);

	public Setting<?> addSetting(String name, String path, Object defaultObject, boolean hasLanguage);

	public void removeSetting(String name);



	public void repair();

}
