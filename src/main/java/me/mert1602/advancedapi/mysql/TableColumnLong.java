package me.mert1602.advancedapi.mysql;

import lombok.Getter;

public class TableColumnLong implements TableColumn<Long> {

	@Getter private final String name;
	@Getter private final Long defaultValue;

	public TableColumnLong(final String name) {
		this(name, 0L);
	}

	public TableColumnLong(final String name, final Long defaultValue) {

		this.name = name;
		this.defaultValue = defaultValue;

	}

	@Override
	public String getSQLSatement() {
		return "`" + this.name + "` BIGINT NOT NULL DEFAULT '" + this.defaultValue + "'";
	}

}
