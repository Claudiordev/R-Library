package data.sql;

import data.sql.general.AbstractSQL;

import java.io.File;

public class SQLLite extends AbstractSQL {

    File file; //Object to create directory
    String path; //Path to the folder where the Database file will be contained

    public SQLLite(String url,String path) {
        this.url = url;
        this.path = path; //plugins/HolographicChatEvo/db
        file = new File(path);
    }

    public boolean createdir() {
        if (!file.exists()) {
            return file.mkdir();
        }

        return false;
    }
}
