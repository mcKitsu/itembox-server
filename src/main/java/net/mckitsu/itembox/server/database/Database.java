package net.mckitsu.itembox.server.database;

import net.mckitsu.itembox.protocol.database.DatabaseConfig;
import net.mckitsu.itembox.protocol.database.format.Repository;
import net.mckitsu.itembox.server.ItemBox;
import net.mckitsu.lib.sqlite.SQLite;
import net.mckitsu.lib.sqlite.SQLiteTable;

public class Database {
    private boolean isConnect = false;
    private SQLite repository;

    /* **************************************************************************************
     *  Abstract method
     */

    /* **************************************************************************************
     *  Construct method
     */
    public Database(){
        ItemBox.getLogger().info("[Database] protocol version " + net.mckitsu.itembox.protocol.ItemBoxProtocol.getVersion());
    }

    /* **************************************************************************************
     *  Override method
     */

    /* **************************************************************************************
     *  Public method
     */
    public boolean connect(){
        if(isConnect)
            return true;

        this.isConnect = true;

        this.repository = sqLiteConnect(new Repository().getColumnType());

        if(this.repository == null){
            ItemBox.getLogger().severe("[Database] \"repository\" connect fail!");
            return false;
        }


        ItemBox.getLogger().info("[Database] connect success");
        return true;
    }

    public void close(){
        if(!isConnect)
            return;

        this.isConnect = false;

        this.repository.close();
        ItemBox.getLogger().info("[Database] sql \"repository\" close");
    }

    /* **************************************************************************************
     *  protected method
     */

    /* **************************************************************************************
     *  Private method
     */

    private SQLite sqLiteConnect(DatabaseConfig config){
        SQLite result;
        String filePath = String.format("database\\%s.db", config.getSqlName());
        result = new SQLite(filePath);

        if(result.connect())
            ItemBox.getLogger().info("[Database] connect successful " + filePath);

        else{
            ItemBox.getLogger().severe("[Database] connect fail " + filePath);
            return null;
        }

        SQLiteTable sqLiteTable = convertToSQLiteTable(config);
        SQLite.Status sqLiteResult = result.createTable(sqLiteTable);

        if(sqLiteResult == SQLite.Status.SUCCESS){
            ItemBox.getLogger().info(String.format("[Database] add table \"%s\" to %s", config.getTableName(), filePath));
        }else{
            ItemBox.getLogger().info(String.format("[Database] found table \"%s\" from %s", config.getTableName(), filePath));
        }

        ItemBox.getLogger().info(String.format("[Database] sql \"%s\" connected", config.getSqlName()));
        return result;
    }

    private SQLiteTable convertToSQLiteTable(DatabaseConfig config){
        SQLiteTable result = new SQLiteTable();
        result.setTableName(config.getTableName());
        result.setPrimaryKey(config.getPrimaryKey());
        result.setTable(config.getMap());
        return result;
    }
}
