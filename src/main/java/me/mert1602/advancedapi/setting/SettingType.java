package me.mert1602.advancedapi.setting;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang.Validate;

import lombok.Getter;
import me.mert1602.advancedapi.ReflectionUtils;
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
import me.mert1602.advancedapi.tool.Tool;

public class SettingType {

	/**
	 * Singel
	 */

	//Normal
	public static final SettingType BOOLEAN 		= new SettingType(SettingBoolean.class, 		Boolean.valueOf(false));
	public static final SettingType OBJECT 			= new SettingType(SettingObject.class, 			new Object());
	public static final SettingType STRING 			= new SettingType(SettingString.class, 			"NOT SET");

	//Numeric
	public static final SettingType DOUBLE 			= new SettingType(SettingDouble.class, 			Double.valueOf(0.0D));
	public static final SettingType FLOAT 			= new SettingType(SettingFloat.class, 			Float.valueOf(0.0F));
	public static final SettingType INTEGER 		= new SettingType(SettingInteger.class, 		Integer.valueOf(0));
	public static final SettingType LONG 			= new SettingType(SettingLong.class, 			Long.valueOf(0L));

	/**
	 * List
	 */

	//Normal
	public static final SettingType BOOLEANLIST 	= new SettingType(SettingBooleanList.class, 	true, Arrays.asList(Boolean.valueOf(false)));
	public static final SettingType STRINGLIST 		= new SettingType(SettingStringList.class, 		true, Arrays.asList("NOT SET"));

	//Numeric
	public static final SettingType BYTELIST 		= new SettingType(SettingByteList.class, 		true, Arrays.asList(Byte.valueOf((byte)0)));
	public static final SettingType DOUBLELIST 		= new SettingType(SettingDoubleList.class, 		true, Arrays.asList(Double.valueOf(0)));
	public static final SettingType FLOATLIST 		= new SettingType(SettingFloatList.class, 		true, Arrays.asList(Float.valueOf(0)));
	public static final SettingType INTEGERLIST 	= new SettingType(SettingIntegerList.class, 	true, Arrays.asList(Integer.valueOf(0)));
	public static final SettingType LONGLIST 		= new SettingType(SettingLongList.class, 		true, Arrays.asList(Long.valueOf(0L)));
	public static final SettingType SHORTLIST 		= new SettingType(SettingShortList.class, 		true, Arrays.asList(Short.valueOf((short)0)));

	/**
	 * ETC.
	 */

	@Getter private static final List<SettingType> types;

	static{

		types = new ArrayList<SettingType>();

		for(Field field : SettingType.class.getDeclaredFields()){

			Class<?> returnType = ReflectionUtils.DataType.getPrimitive(field.getType());

			if(Modifier.isStatic(field.getModifiers()) == false) continue;
			if(returnType.isAssignableFrom(SettingType.class) == false) continue;

			if(field.isAccessible() == false){
				field.setAccessible(true);
			}

			try {
				types.add((SettingType) ReflectionUtils.getValue(null, SettingType.class, true, field.getName()));
			} catch (Throwable e) {}

			if(field.isAccessible()){
				field.setAccessible(false);
			}

		}

	}

	public static SettingType[] values(){
		return types.toArray(Tool.EMPTY_SETTINGTYPE_LIST);
	}

	/**
	 * Class
	 */

	@Getter private Class<? extends Setting<?>> settingClass;
	private Class<?> defaultObjectClass;
	@Getter private boolean list;
	@Getter private Object defaultObject;

	public SettingType(final Class<? extends Setting<?>> settingClass, Object defaultObject){
		this(settingClass, false, defaultObject);
	}

	public SettingType(final Class<? extends Setting<?>> settingClass, Class<?> defaultObjectClass){
		this(settingClass, false, defaultObjectClass);
	}

	public SettingType(final Class<? extends Setting<?>> settingClass, final boolean list, Object defaultObject){

		Validate.notNull(settingClass);
		Validate.notNull(defaultObject);

		this.settingClass = settingClass;

		if(defaultObject instanceof List){

			List<?> defaultObjectList = (List<?>) defaultObject;

			if(defaultObjectList.isEmpty()) return;
			Object defaultObjectListFirstValue = defaultObjectList.get(0);
			Class<?> defaultObjectListFirstValueClass = ReflectionUtils.DataType.getPrimitive(defaultObjectListFirstValue.getClass());

			this.defaultObjectClass = ReflectionUtils.DataType.getPrimitive(defaultObjectListFirstValueClass);

		}else this.defaultObjectClass = ReflectionUtils.DataType.getPrimitive(defaultObject.getClass());

		this.list = list;
		this.defaultObject = defaultObject;

	}

	public SettingType(final Class<? extends Setting<?>> settingClass, final boolean list, Class<?> defaultObjectClass){

		Validate.notNull(settingClass);
		Validate.notNull(defaultObjectClass);

		this.settingClass = settingClass;
		this.defaultObjectClass = ReflectionUtils.DataType.getPrimitive(defaultObjectClass);
		this.list = list;
		this.defaultObject = null;

	}

	public Class<?> getDefaultObjectClass(){

		if(this.hasDefaultObject()){

			if(this.getDefaultObject() instanceof List){

				List<?> defaultObjectList = (List<?>) this.getDefaultObject();

				if(defaultObjectList.isEmpty()) return null;
				Object defaultObjectListFirstValue = defaultObjectList.get(0);
				Class<?> defaultObjectListFirstValueClass = ReflectionUtils.DataType.getPrimitive(defaultObjectListFirstValue.getClass());

				return ReflectionUtils.DataType.getPrimitive(defaultObjectListFirstValueClass);

			}else return ReflectionUtils.DataType.getPrimitive(this.getDefaultObject().getClass());

		}else return ReflectionUtils.DataType.getPrimitive(this.defaultObjectClass);

	}

	public boolean hasDefaultObject(){
		return this.getDefaultObject() != null;
	}

}
