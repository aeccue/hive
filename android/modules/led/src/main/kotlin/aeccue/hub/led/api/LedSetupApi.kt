package aeccue.hub.led.api

import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSetup
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LedSetupApi {

    @Transaction
    @Query("SELECT * FROM LedSetups")
    abstract fun getAll(): Flow<List<LedSetup>>

    @Insert
    abstract suspend fun insert(setup: LedSetup): Long

    @Update
    abstract suspend fun update(setup: LedSetup)

    @Transaction
    open suspend fun delete(setup: LedSetup) {
        deleteAllSections(setup.group, setup.id)
        delete(setup)
    }

    @Delete
    protected abstract suspend fun deleteSetup(setup: LedSetup)

    @Query("DELETE FROM LedSections WHERE [group] = :group AND setupId = :setupId")
    protected abstract suspend fun deleteAllSections(group: LedGroup, setupId: Int)
}