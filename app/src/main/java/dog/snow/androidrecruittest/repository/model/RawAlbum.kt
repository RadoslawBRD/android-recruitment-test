package dog.snow.androidrecruittest.repository.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.android.parcel.Parcelize

@Entity(tableName = "album")
@Parcelize
data class RawAlbum(
    @PrimaryKey(autoGenerate = false)
    val id: Int,
    val userId: Int,
    val title: String
) : Parcelable
