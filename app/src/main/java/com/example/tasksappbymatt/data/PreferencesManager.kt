package com.example.tasksappbymatt.data

import android.content.Context
import android.util.Log
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.emptyPreferences
import androidx.datastore.preferences.core.stringPreferencesKey

import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException
import javax.inject.Inject
import javax.inject.Singleton


private const val TAG = "PreferencesManager"

enum class SortOrder{BY_NAME, BY_DATE}

data class FilterPreferences(
    val sortOrder: SortOrder,
    val hideCompleted: Boolean
)

@Singleton
class PreferencesManager @Inject constructor(@ApplicationContext context: Context, private val dataStore: DataStore<Preferences>){

    // At the top level of your kotlin file:


    val preferencesFlow: Flow<FilterPreferences> = dataStore.data
        .catch { exception ->
            if (exception is IOException){
                Log.e(TAG, "Error reading preferences", exception )
                emit(emptyPreferences())
            } else  {
                throw exception
            }

        }
        .map { preferences ->
            val sortOrder = SortOrder.valueOf(
                preferences[PreferencesKeys.SORT_ORDER] ?: SortOrder.BY_DATE.name
            )
            val hideCompleted = preferences[PreferencesKeys.HIDE_COMPLETED] ?: false
            FilterPreferences(sortOrder, hideCompleted)
        }

    suspend fun updateSortOrder(sortOrder: SortOrder){
        dataStore.edit{ preferences ->
            preferences[PreferencesKeys.SORT_ORDER] = sortOrder.name

        }
    }

    suspend fun updateHideCompleted(hideCompleted: Boolean){
        dataStore.edit { preferences ->
            preferences[PreferencesKeys.HIDE_COMPLETED] = hideCompleted

        }
    }

    private object PreferencesKeys {
        val SORT_ORDER = stringPreferencesKey("sort_order")
        val HIDE_COMPLETED = booleanPreferencesKey("hide_completed")
    }
}