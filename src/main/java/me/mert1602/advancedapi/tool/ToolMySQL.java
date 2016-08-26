package me.mert1602.advancedapi.tool;

import java.sql.DatabaseMetaData;
import java.sql.ResultSet;
import java.util.Iterator;

import me.mert1602.advancedapi.mysql.Entry;
import me.mert1602.advancedapi.mysql.MySQL;
import me.mert1602.advancedapi.mysql.TableColumn;
import me.mert1602.advancedapi.mysql.TableInterface;

public class ToolMySQL {

	private ToolMySQL() {}

	public static String getSQL_Table_ColumnCreate(final TableInterface table){

		//Create
		final StringBuilder columnStringBuilder = new StringBuilder();
		final Iterator<TableColumn<?>> i = table.getColumnList().iterator();

		if(i.hasNext()){
			columnStringBuilder.append(i.next().getSQLSatement());
		}

		while(i.hasNext()){
			columnStringBuilder.append(", " + i.next().getSQLSatement());
		}

		return columnStringBuilder.toString();
	}

	public static String getSQL_Entry_ColumnValues(final Entry entry){

		final StringBuilder sb = new StringBuilder();
		final Iterator<Object> i = entry.getMap().values().iterator();

		if(i.hasNext()){

			final Object object = i.next();

			if(object instanceof Boolean){

				Boolean booleanObject = (Boolean) object;

				sb.append(booleanObject.toString().toUpperCase());

			} else sb.append("'" + object + "'");

		}

		while(i.hasNext()){

			final Object object = i.next();

			if(object instanceof Boolean){

				Boolean booleanObject = (Boolean) object;

				sb.append(", " + booleanObject.toString().toUpperCase());

			} else sb.append(", '" + object + "'");

		}

		return sb.toString();
	}

	public static String getSQL_Entry_ColumnNames(final Entry entry){

		final StringBuilder sb = new StringBuilder();
		final Iterator<TableColumn<?>> i = entry.getMap().keySet().iterator();

		if(i.hasNext()){
			sb.append("`" + i.next().getName() + "`");
		}

		while(i.hasNext()){
			sb.append(", `" + i.next().getName() + "`");
		}

		return sb.toString();
	}

	public static boolean hasTable(final MySQL mysql, final TableInterface table){

		DatabaseMetaData md;
		ResultSet rs = null;

		try {

			md = mysql.getConnection().getMetaData();
			rs = md.getTables(null, null, table.getName(), null);

			if(rs.next()){
				return true;
			}else {
				return false;
			}

		} catch (final Throwable e) {
			return false;
		} finally {
			Tool.close(rs);
		}

	}

}
