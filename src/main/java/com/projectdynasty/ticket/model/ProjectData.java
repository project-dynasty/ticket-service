package com.projectdynasty.ticket.model;

import de.alexanderwodarz.code.database.AbstractTable;
import de.alexanderwodarz.code.database.Database;
import de.alexanderwodarz.code.database.annotation.Column;
import de.alexanderwodarz.code.database.annotation.Table;
import de.alexanderwodarz.code.database.enums.ColumnType;

@Table(name = "project_data")
public class ProjectData extends AbstractTable {

    @Column(autoIncrement = true, primaryKey = true)
    public int id;

    @Column(length = 32)
    public String name;

    @Column(length = 16)
    public String icon;

    @Column(length = 128)
    public String permission;

    @Column
    public long lead;

    @Column(type = ColumnType.MEDIUMTEXT)
    public String description;

    @Column(name = "project_key", length = 4)
    public String projectKey;

    public ProjectData(Database database) {
        super(database);
    }
}
