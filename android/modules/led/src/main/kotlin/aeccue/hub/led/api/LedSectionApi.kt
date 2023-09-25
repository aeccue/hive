package aeccue.hub.led.api

import aeccue.hub.led.model.LedGroup
import aeccue.hub.led.model.LedSection
import aeccue.hub.led.model.LedSet
import androidx.room.*
import kotlinx.coroutines.flow.Flow

@Dao
abstract class LedSectionApi {

    @Transaction
    @Query("SELECT * FROM LedSetups WHERE [group] = :group AND id = :id")
    abstract fun getAll(group: LedGroup, id: Int): Flow<LedSet>

    @Query("SELECT * FROM LedSections WHERE [group] = :group AND setupId = :setupId AND id = :sectionId")
    abstract fun get(group: LedGroup, setupId: Int, sectionId: Int): Flow<LedSection>

    @Insert
    abstract suspend fun insert(section: LedSection)

    @Update
    abstract suspend fun update(section: LedSection)

    @Delete
    abstract suspend fun delete(section: LedSection)
}
