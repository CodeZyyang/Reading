package com.hankkin.reading.greendao;

import java.util.List;
import java.util.ArrayList;
import android.database.Cursor;
import android.database.sqlite.SQLiteStatement;

import org.greenrobot.greendao.AbstractDao;
import org.greenrobot.greendao.Property;
import org.greenrobot.greendao.internal.SqlUtils;
import org.greenrobot.greendao.internal.DaoConfig;
import org.greenrobot.greendao.database.Database;
import org.greenrobot.greendao.database.DatabaseStatement;

import com.hankkin.reading.domain.TranslateBean;

import com.hankkin.reading.domain.WordNoteBean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT.
/** 
 * DAO for table "WORD_NOTE_BEAN".
*/
public class WordNoteBeanDao extends AbstractDao<WordNoteBean, Long> {

    public static final String TABLENAME = "WORD_NOTE_BEAN";

    /**
     * Properties of entity WordNoteBean.<br/>
     * Can be used for QueryBuilder and for referencing column names.
     */
    public static class Properties {
        public final static Property Id = new Property(0, long.class, "id", true, "_id");
        public final static Property IsEmphasis = new Property(1, boolean.class, "isEmphasis", false, "isEmphasis");
    }

    private DaoSession daoSession;


    public WordNoteBeanDao(DaoConfig config) {
        super(config);
    }
    
    public WordNoteBeanDao(DaoConfig config, DaoSession daoSession) {
        super(config, daoSession);
        this.daoSession = daoSession;
    }

    /** Creates the underlying database table. */
    public static void createTable(Database db, boolean ifNotExists) {
        String constraint = ifNotExists? "IF NOT EXISTS ": "";
        db.execSQL("CREATE TABLE " + constraint + "\"WORD_NOTE_BEAN\" (" + //
                "\"_id\" INTEGER PRIMARY KEY NOT NULL ," + // 0: id
                "\"isEmphasis\" INTEGER NOT NULL );"); // 1: isEmphasis
    }

    /** Drops the underlying database table. */
    public static void dropTable(Database db, boolean ifExists) {
        String sql = "DROP TABLE " + (ifExists ? "IF EXISTS " : "") + "\"WORD_NOTE_BEAN\"";
        db.execSQL(sql);
    }

    @Override
    protected final void bindValues(DatabaseStatement stmt, WordNoteBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getIsEmphasis() ? 1L: 0L);
    }

    @Override
    protected final void bindValues(SQLiteStatement stmt, WordNoteBean entity) {
        stmt.clearBindings();
        stmt.bindLong(1, entity.getId());
        stmt.bindLong(2, entity.getIsEmphasis() ? 1L: 0L);
    }

    @Override
    protected final void attachEntity(WordNoteBean entity) {
        super.attachEntity(entity);
        entity.__setDaoSession(daoSession);
    }

    @Override
    public Long readKey(Cursor cursor, int offset) {
        return cursor.getLong(offset + 0);
    }    

    @Override
    public WordNoteBean readEntity(Cursor cursor, int offset) {
        WordNoteBean entity = new WordNoteBean( //
            cursor.getLong(offset + 0), // id
            cursor.getShort(offset + 1) != 0 // isEmphasis
        );
        return entity;
    }
     
    @Override
    public void readEntity(Cursor cursor, WordNoteBean entity, int offset) {
        entity.setId(cursor.getLong(offset + 0));
        entity.setIsEmphasis(cursor.getShort(offset + 1) != 0);
     }
    
    @Override
    protected final Long updateKeyAfterInsert(WordNoteBean entity, long rowId) {
        entity.setId(rowId);
        return rowId;
    }
    
    @Override
    public Long getKey(WordNoteBean entity) {
        if(entity != null) {
            return entity.getId();
        } else {
            return null;
        }
    }

    @Override
    public boolean hasKey(WordNoteBean entity) {
        throw new UnsupportedOperationException("Unsupported for entities with a non-null key");
    }

    @Override
    protected final boolean isEntityUpdateable() {
        return true;
    }
    
    private String selectDeep;

    protected String getSelectDeep() {
        if (selectDeep == null) {
            StringBuilder builder = new StringBuilder("SELECT ");
            SqlUtils.appendColumns(builder, "T", getAllColumns());
            builder.append(',');
            SqlUtils.appendColumns(builder, "T0", daoSession.getTranslateBeanDao().getAllColumns());
            builder.append(" FROM WORD_NOTE_BEAN T");
            builder.append(" LEFT JOIN TRANSLATE_BEAN T0 ON T.\"_id\"=T0.\"_id\"");
            builder.append(' ');
            selectDeep = builder.toString();
        }
        return selectDeep;
    }
    
    protected WordNoteBean loadCurrentDeep(Cursor cursor, boolean lock) {
        WordNoteBean entity = loadCurrent(cursor, 0, lock);
        int offset = getAllColumns().length;

        TranslateBean translateBean = loadCurrentOther(daoSession.getTranslateBeanDao(), cursor, offset);
         if(translateBean != null) {
            entity.setTranslateBean(translateBean);
        }

        return entity;    
    }

    public WordNoteBean loadDeep(Long key) {
        assertSinglePk();
        if (key == null) {
            return null;
        }

        StringBuilder builder = new StringBuilder(getSelectDeep());
        builder.append("WHERE ");
        SqlUtils.appendColumnsEqValue(builder, "T", getPkColumns());
        String sql = builder.toString();
        
        String[] keyArray = new String[] { key.toString() };
        Cursor cursor = db.rawQuery(sql, keyArray);
        
        try {
            boolean available = cursor.moveToFirst();
            if (!available) {
                return null;
            } else if (!cursor.isLast()) {
                throw new IllegalStateException("Expected unique result, but count was " + cursor.getCount());
            }
            return loadCurrentDeep(cursor, true);
        } finally {
            cursor.close();
        }
    }
    
    /** Reads all available rows from the given cursor and returns a list of new ImageTO objects. */
    public List<WordNoteBean> loadAllDeepFromCursor(Cursor cursor) {
        int count = cursor.getCount();
        List<WordNoteBean> list = new ArrayList<WordNoteBean>(count);
        
        if (cursor.moveToFirst()) {
            if (identityScope != null) {
                identityScope.lock();
                identityScope.reserveRoom(count);
            }
            try {
                do {
                    list.add(loadCurrentDeep(cursor, false));
                } while (cursor.moveToNext());
            } finally {
                if (identityScope != null) {
                    identityScope.unlock();
                }
            }
        }
        return list;
    }
    
    protected List<WordNoteBean> loadDeepAllAndCloseCursor(Cursor cursor) {
        try {
            return loadAllDeepFromCursor(cursor);
        } finally {
            cursor.close();
        }
    }
    

    /** A raw-style query where you can pass any WHERE clause and arguments. */
    public List<WordNoteBean> queryDeep(String where, String... selectionArg) {
        Cursor cursor = db.rawQuery(getSelectDeep() + where, selectionArg);
        return loadDeepAllAndCloseCursor(cursor);
    }
 
}
