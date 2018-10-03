package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ena on 12.06.2016..
 */
public class DBHandlerPjesme extends SQLiteOpenHelper {

    public static final String DATABASE_NAME_PJESME = "mojaBazaPjesme.db";
    public static final String DATABASE_TABLE_PJESME = "Pjesme";
    public static final int DATABASE_VERSION = 1;
    public static final String PJESMA_ID="pjesma_id";
    public static final String PJESMA_IME ="pjesma_ime";
    public static final String MUZICAR_ID ="muzicar_id";

    // SQL upit za kreiranje baze
    private static final String DATABASE_CREATE_PJESME = "create table " +
            DATABASE_TABLE_PJESME + " (" + PJESMA_ID +
            " integer primary key autoincrement, " +
            PJESMA_IME + " text not null, "+ MUZICAR_ID +" INTEGER NOT NULL ,FOREIGN KEY (" +  MUZICAR_ID +
            ") REFERENCES "+ DBHandler.DATABASE_TABLE +" ("+MUZICAR_ID+"));";

    public DBHandlerPjesme(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME_PJESME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
//        db.setForeignKeyConstraintsEnabled(true);
        db.execSQL(DATABASE_CREATE_PJESME);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE_PJESME);
        onCreate(db);
    }
}