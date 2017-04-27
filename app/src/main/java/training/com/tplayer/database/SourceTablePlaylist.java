package training.com.tplayer.database;

import android.content.ContentValues;
import android.database.Cursor;

import com.remote.communication.PlaylistEntity;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ThoNH on 4/27/2017.
 */

public class SourceTablePlaylist extends DatabaseSource<PlaylistEntity> {

    private static SourceTablePlaylist mInstance;

    public synchronized static SourceTablePlaylist getInstance(DatabaseHelper databaseHelper) {
        if (mInstance == null) {
            mInstance = new SourceTablePlaylist(databaseHelper);
        }
        return mInstance;
    }

    private SourceTablePlaylist(DatabaseHelper databaseHelper) {
        super(databaseHelper);
    }

    @Override
    public List<PlaylistEntity> getList(Cursor cursor) {
        List<PlaylistEntity> entities = new ArrayList<>();

        int mIdIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.MID);
        int idIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn._ID);
        int dataIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn._DATA);
        int nameIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn._NAME);
        int dateAddedIndex = cursor.getColumnIndex(DataBaseUtils.DbStorePlaylistColumn.DATE_ADDED);


        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {

            PlaylistEntity entity = new PlaylistEntity();
            entity.mId = Integer.parseInt(cursor.getString(mIdIndex));
            entity.id = Integer.parseInt(cursor.getString(idIndex));
            entity.data = cursor.getString(dataIndex);
            entity.name = cursor.getString(nameIndex);
            entity.dateAdded = Integer.parseInt(cursor.getString(dateAddedIndex));

            entities.add(entity);

            cursor.moveToNext();
        }
        if (!cursor.isClosed())
            cursor.close();
        return entities;
    }

    @Override
    public PlaylistEntity getRow(Cursor cursor) {
        return getList(cursor).get(0);
    }

    @Override
    public long insertRow(PlaylistEntity entity) {
        return getSqlDb().insert(DataBaseUtils.TABLE_PLAYLIST, null, convertEntity2ContentValue(entity));
    }

    @Override
    public int deleteRow(PlaylistEntity entity) {
        return getSqlDb().delete(DataBaseUtils.TABLE_PLAYLIST,
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public int updateRow(PlaylistEntity entity) {
        return getSqlDb().update(DataBaseUtils.TABLE_PLAYLIST,
                convertEntity2ContentValue(entity),
                DataBaseUtils.DbStoreMediaColumn.MID,
                new String[]{String.valueOf(entity.mId)});
    }

    @Override
    public ContentValues convertEntity2ContentValue(PlaylistEntity entity) {
        ContentValues values = new ContentValues();
        values.put(DataBaseUtils.DbStorePlaylistColumn.MID, entity.mId);
        values.put(DataBaseUtils.DbStorePlaylistColumn._ID, entity.id);
        values.put(DataBaseUtils.DbStorePlaylistColumn._DATA, entity.data);
        values.put(DataBaseUtils.DbStorePlaylistColumn._NAME, entity.name);
        values.put(DataBaseUtils.DbStorePlaylistColumn.DATE_ADDED, entity.dateAdded);
        return values;
    }
}
