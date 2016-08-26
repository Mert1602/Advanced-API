package me.mert1602.advancedapi.mysql;

import lombok.Getter;

public class TableColumnFloat implements TableColumn<Float> {

	@Getter private final String name;
	@Getter private final Float defaultValue;

	public TableColumnFloat(final String name) {
		this(name, 0.0F);
	}

	public TableColumnFloat(final String name, final Float defaultValue) {

		this.name = name;
		this.defaultValue = defaultValue;

	}

	@Override
	public String getSQLSatement() {
		return "`" + this.name + "` FLOAT NOT NULL DEFAULT '" + this.defaultValue + "'";
	}

}
