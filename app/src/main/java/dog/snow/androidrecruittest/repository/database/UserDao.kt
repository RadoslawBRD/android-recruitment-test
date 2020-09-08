package dog.snow.androidrecruittest.repository.database

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import dog.snow.androidrecruittest.repository.model.RawUser

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun upsert(UsersEntry: List<RawUser>)

    @Query( "select * from user")
    fun getUsers(): LiveData<List<RawUser>>

    @Query("select * from user where id =(:id)")
    fun getUserById(id:String): LiveData<RawUser>
}