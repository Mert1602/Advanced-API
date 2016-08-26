package me.mert1602.advancedapi.setting;

import java.util.List;

import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.ReflectionUtils;
import me.mert1602.advancedapi.setting.type.SettingObject;
import me.mert1602.advancedapi.tool.Tool;

public final class SettingTool {

	public Setting<?> getSetting(final SettingManager<?> manager, final String name, final String path, final boolean hasLanguage, final Object defaultValue){

		Class<?> defaultValueClass = ReflectionUtils.DataType.getPrimitive(defaultValue.getClass());

		if(defaultValue instanceof List<?>){

			final List<?> list = (List<?>) defaultValue;

			if(list.isEmpty()){
				return null;
			}

			final Object defaultSingelValue = list.get(0);
			Class<?> defaultSingelValueClass = ReflectionUtils.DataType.getPrimitive(defaultSingelValue.getClass());

			for(final SettingType type : SettingType.values()){

				if(type.isList() == false) continue;
				if(type.getDefaultObjectClass().isAssignableFrom(defaultSingelValueClass) == false) continue;

				try {
					return (Setting<?>) ReflectionUtils.instantiateObject(type.getSettingClass(), manager, name, path, hasLanguage, defaultValue);
				} catch (final Throwable e){
					continue;
				}

			}

		}else{

			for(final SettingType type : SettingType.values()){

				if(type.isList()) continue;
				if(type == SettingType.OBJECT) continue;
				if(type.getDefaultObjectClass().isAssignableFrom(defaultValueClass) == false) continue;
				
				try {
					return (Setting<?>) ReflectionUtils.instantiateObject(type.getSettingClass(), manager, name, path, hasLanguage, defaultValue);
				} catch (final Throwable e){
					e.printStackTrace();
					Log.exception(e, this.getClass(), Tool.getLineNumber());
					continue;
				}

			}

		}

		return new SettingObject(manager, name, path, hasLanguage, defaultValue);

	}

}
