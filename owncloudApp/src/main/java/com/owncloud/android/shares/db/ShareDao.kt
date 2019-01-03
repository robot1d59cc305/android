/**
 * ownCloud Android client application
 *
 * @author David González Verdugo
 * Copyright (C) 2019 ownCloud GmbH.
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 2,
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.owncloud.android.shares.db

import android.arch.lifecycle.LiveData
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.Query
import com.owncloud.android.shares.datasources.LocalSharesDataSource

@Dao
interface ShareDao : LocalSharesDataSource {
    @Query("SELECT * from shares_table ORDER BY id")
    override fun shares(): LiveData<List<OCShare>>

    @Query(
        "SELECT * from shares_table " +
                "WHERE path = :filePath " +
                "AND accountOwner = :accountName AND shareType IN(:shareTypes)"
    )
    override fun sharesForAFile(
        filePath: String, accountName: String, shareTypes: List<Int>
    ): LiveData<List<OCShare>>

    @Insert
    override fun insert(ocShare: OCShare)
}