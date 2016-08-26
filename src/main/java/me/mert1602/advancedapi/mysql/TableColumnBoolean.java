package me.mert1602.advancedapi.mysql;

import lombok.Getter;

public class TableColumnBoolean implements TableColumn<Boolean> {

	@Getter private String name;
	@Getter private Boolean defaultValue;

	public TableColumnBoolean(String name) {
		this(name, false);
	}

	public TableColumnBoolean(String name, Boolean defaultValue) {

		this.name = name;
		this.defaultValue = defaultValue;

	}

	@Override
	public String getSQLSatement() {
		return "`" + this.name + "` BOOLEAN NOT NULL DEFAULT " + this.defaultValue.toString().toUpperCase();
	}

}
