package com.gattal.asta.mobileproject.dao

import androidx.lifecycle.LiveData
import androidx.room.*
import com.gattal.asta.mobileproject.modeldata.AdEntity


@Dao
interface EntityDao {

    @Query("SELECT * FROM ad_table WHERE id = :id ")
    fun getAd(id: String): LiveData<AdEntity>

    @Query("SELECT * FROM ad_table")
    fun getAds(): LiveData<List<AdEntity>>

    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun insert(realEstateAd: AdEntity): Long

    @Delete
    fun delete(realEstateAd: AdEntity): Int

    @Query("DELETE FROM ad_table")
    fun deleteAll()
}