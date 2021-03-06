package training.com.tplayer.ui.adapter.offline;

import android.content.Context;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.View;
import android.view.ViewGroup;

import com.remote.communication.MediaEntity;

import training.com.tplayer.R;
import training.com.tplayer.base.recyclerview.BaseRecyclerViewAdapter;
import training.com.tplayer.base.recyclerview.BaseViewHolder;
import training.com.tplayer.base.recyclerview.IRecyclerViewOnItemClickListener;

/**
 * Created by ThoNH on 28/04/2017.
 */

public class DownloadAdapter  extends BaseRecyclerViewAdapter<MediaEntity, DownloadAdapter.ViewHolder> {

    private int mMenuContextPosition = -1 ;


    public interface DownloadAdapterListener extends IRecyclerViewOnItemClickListener<MediaEntity> {
    }

    public DownloadAdapter(Context context, DownloadAdapterListener listener) {
        super(context, listener);
    }

    @Override
    public ViewHolder onCreateViewHolderAdapter(ViewGroup parent, int viewType) {
        return new ViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.item_offline_songs, parent, false));
    }

    @Override
    public void onBindViewHolderAdapter(ViewHolder holder, final int position) {

    }


    public class ViewHolder extends BaseViewHolder implements View.OnCreateContextMenuListener {
        public ViewHolder(View itemView) {
            super(itemView);

            itemView.setOnCreateContextMenuListener(this);

        }


        @Override
        public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
            menu.add(Menu.NONE, R.id.action_add_to_first_now_playing, Menu.NONE, R.string.context_menu_download_first_now_playings);
            menu.add(Menu.NONE, R.id.action_add_to_now_plays, Menu.NONE, R.string.context_menu_download_now_playings);
            menu.add(Menu.NONE, R.id.action_add_playlist, Menu.NONE, R.string.context_menu_download_to_playlist);
            menu.add(Menu.NONE, R.id.action_set_is_rington, Menu.NONE, R.string.context_menu_download_set_rington);
            menu.add(Menu.NONE, R.id.action_delete_in_download, Menu.NONE, R.string.context_menu_download_delete_in_download);
            menu.add(Menu.NONE, R.id.action_delete, Menu.NONE, R.string.context_menu_song_delete);
        }
    }
}
