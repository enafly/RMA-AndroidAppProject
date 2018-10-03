package ba.unsa.etf.rma.ena_muratspahic.rma2016_16334;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Ena on 11.06.2016..
 */
public class DBHandler extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "mojaBaza.db";
    public static final String DATABASE_TABLE = "Muzicari";
    public static final int DATABASE_VERSION = 1;
    public static final String MUZICAR_ID ="_id";
    public static final String MUZICAR_IME ="ime";
    public static final String MUZICAR_ZANR ="zanr";


    // SQL upit za kreiranje baze
    private static final String DATABASE_CREATE = "create table " +
            DATABASE_TABLE + " (" + MUZICAR_ID +
            " integer primary key autoincrement, "+
            MUZICAR_IME + " text not null, " +
            MUZICAR_ZANR + " text not null);";

    public DBHandler(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, DATABASE_NAME, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(DATABASE_CREATE);

       /* try {
            db = this.getWritableDatabase();
            String selectQuery = "MUZICAR_IME,MUZICAR_ZANR";
            StringBuilder sb= new StringBuilder();
            Cursor cursor1 = db.rawQuery(selectQuery, null);
            while (cursor1.moveToNext()) {
                String MUZICAR_ID= cursor1.getString(cursor1.getColumnIndex("MUZICAR_IME"));
                sb.append(MUZICAR_IME);

                sb.append("-");
                String USERNAME= cursor1.getString(cursor1.getColumnIndex("MUZICAR_"));
                sb.append(USERNAME);
                sb.append("-");
                String LECTURE_NAME=cursor1.getString(cursor1.getColumnIndex("LECTURE_NAME"));
                sb.append(LECTURE_NAME);
                sb.append("-");
                String LECTURE_NOTES = cursor1.getString(cursor1.getColumnIndex("LECTURE_NOTES "));
                sb.append(LECTURE_NOTES );

            }

        } catch (Exception e) {
            e.printStackTrace();
        }*/
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF IT EXISTS " + DATABASE_TABLE);

        onCreate(db);
    }
}
