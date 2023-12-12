package br.com.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["name"], unique = true)])
data class Movie (
    @PrimaryKey(autoGenerate = true) val id: Int,
    var name: String = "",
    var released: String = "",
    var producer: String = "",
    var duration: Int = 0,
    var watched: Int = WATCHED_FALSE,
    var note: Int = 0,
    var gender: String = ""
): Parcelable {
    companion object {
        const val WATCHED_TRUE = 1
        const val WATCHED_FALSE = 0
    }
}
