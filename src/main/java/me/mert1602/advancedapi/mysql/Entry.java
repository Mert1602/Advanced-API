package me.mert1602.advancedapi.mysql;

import java.util.HashMap;
import java.util.Map;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import me.mert1602.advancedapi.Log;
import me.mert1602.advancedapi.tool.Tool;

@ToString
@EqualsAndHashCode
public class Entry {

	@Getter private final Map<TableColumn<?>, Object> map;

	public Entry(final TableInterface table) {

		this.map = new HashMap<TableColumn<?>, Object>();

		for(final TableColumn<?> column : table.getColumnList()){
			this.map.put(column, column.getDefaultValue());
		}

	}



	public Object getValue(final String columnName){

		for(final TableColumn<?> tc : this.map.keySet()){

			if(tc.getName().equalsIgnoreCase(columnName)){
				return this.map.get(tc);
			}

		}

		return null;
	}

	public String getString(final String columnName){
		return String.valueOf(this.getValue(columnName));
	}



	public boolean getBoolean(final String columnName){
		return Boolean.valueOf(this.getString(columnName));
	}

	public double getDouble(final String columnName){

		try{
			return Double.valueOf(this.getString(columnName));
		}catch(Throwable e){
			e.printStackTrace();
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return 0D;
		}

	}

	public float getFloat(final String columnName){

		try{
			return Float.valueOf(this.getString(columnName));
		}catch(Throwable e){
			e.printStackTrace();
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return 0F;
		}

	}

	public int getInteger(final String columnName){

		try{
			return Integer.valueOf(this.getString(columnName));
		}catch(Throwable e){
			e.printStackTrace();
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return 0;
		}

	}

	public long getLong(final String columnName){

		try{
			return Long.valueOf(this.getString(columnName));
		}catch(Throwable e){
			e.printStackTrace();
			Log.exception(e, this.getClass(), Tool.getLineNumber());
			return 0L;
		}

	}



	public Entry setValue(final String columnName, Object object){

		for(final TableColumn<?> tc : this.map.keySet()){

			if(!tc.getName().equalsIgnoreCase(columnName)) continue;

			this.map.put(tc, object);

			return this;
		}

		return this;
	}

}
