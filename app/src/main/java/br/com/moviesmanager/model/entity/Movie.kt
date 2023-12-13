package br.com.moviesmanager.model.entity

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(indices = [Index(value = ["name"], unique = true)])
data class Movie (
    @PrimaryKey(autoGenerate = false) var id: Long = INVALID_TIME,
    var name: String = "",
    var released: String = "",
    var producer: String = "",
    var duration: Int = 0,
    var watched: Int = WATCHED_FALSE,
    var note: Int = 0,
    var gender: Int = 0,
): Parcelable {
    companion object {
        const val INVALID_TIME = -1L
        const val WATCHED_TRUE = 1
        const val WATCHED_FALSE = 0
    }
}
