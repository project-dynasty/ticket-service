package com.projectdynasty.ticket.model;

import de.alexanderwodarz.code.database.AbstractTable;
import de.alexanderwodarz.code.database.Database;
import de.alexanderwodarz.code.database.annotation.Column;
import de.alexanderwodarz.code.database.annotation.Table;
import de.alexanderwodarz.code.database.enums.ColumnType;

@Table(name = "ticket_data")
public class TicketData extends AbstractTable {

    @Column(autoIncrement = true, primaryKey = true)
    public int id;
    @Column
    public long assignee;

    @Column
    public long reporter;

    @Column
    public long deadline;

    @Column(length = 64)
    public String name;

    @Column(type = ColumnType.MEDIUMTEXT)
    public String description;

    @Column
    public int status;

    @Column
    public long created;

    @Column(length = 255)
    public String repository;

    @Column
    public int priority;

    @Column
    public int projectId;

    @Column
    public int effort;

    @Column
    public boolean deleted;

    @Column
    public long lastUpdate;

    public TicketData(Database database) {
        super(database);
    }
}
