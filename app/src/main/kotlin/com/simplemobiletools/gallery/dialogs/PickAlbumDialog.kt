package com.simplemobiletools.gallery.dialogs

import android.app.AlertDialog
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import com.simplemobiletools.gallery.R
import com.simplemobiletools.gallery.activities.SimpleActivity
import com.simplemobiletools.gallery.adapters.DirectoryAdapter
import com.simplemobiletools.gallery.asynctasks.GetDirectoriesAsynctask
import kotlinx.android.synthetic.main.dialog_album_picker.view.*
import java.util.*

class PickAlbumDialog(val activity: SimpleActivity, val callback: (path: String) -> Unit) {
    var dialog: AlertDialog
    var directoriesGrid: RecyclerView

    init {
        val view = LayoutInflater.from(activity).inflate(R.layout.dialog_album_picker, null)
        directoriesGrid = view.directories_grid

        dialog = AlertDialog.Builder(activity)
                .setTitle(activity.resources.getString(R.string.select_destination))
                .setView(view)
                .setPositiveButton(R.string.ok, null)
                .setNegativeButton(R.string.cancel, null)
                .create()

        dialog.show()
        GetDirectoriesAsynctask(activity, false, false, ArrayList<String>()) {
            val adapter = DirectoryAdapter(activity, it, null) {
                callback.invoke(it.path)
                dialog.dismiss()
            }
            directoriesGrid.adapter = adapter
        }.execute()
    }
}
