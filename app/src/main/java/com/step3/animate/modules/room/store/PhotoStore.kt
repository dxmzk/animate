package com.step3.animate.modules.room.store

import com.step3.animate.modules.room.dao.PhotoDao
import com.step3.animate.modules.room.entity.Photo

/**
 * Author: Meng
 * Date: 2022/08/30
 * Desc:
 */
class PhotoStore: PhotoDao {
    override fun getAll(): List<Photo> {
        TODO("Not yet implemented")
    }

    override fun findById(id: Int): List<Photo> {
        TODO("Not yet implemented")
    }

    override fun findByAnimId(aid: Int): Photo {
        TODO("Not yet implemented")
    }

    override fun insertAll(vararg users: Photo) {
        TODO("Not yet implemented")
    }

    override fun delete(user: Photo) {
        TODO("Not yet implemented")
    }
}