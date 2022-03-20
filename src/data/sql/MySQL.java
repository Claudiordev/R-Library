package data.sql;

import data.sql.general.AbstractSQL;

import java.sql.*;

public class MySQL extends AbstractSQL {

    public MySQL(String url, String username, String password, String database) {
        this.url = url + database;
        this.username = username;
        this.password = password;
        this.database = database;
    }
}
