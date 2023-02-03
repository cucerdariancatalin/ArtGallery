package cucerdariancatalin.sdui.domain.database

import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.javi.render.processor.data.enums.RenderType
import cucerdariancatalin.sdui.domain.database.skeletons.SkeletonsDao
import cucerdariancatalin.sdui.domain.database.skeletons.SkeletonsEntity
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import java.io.IOException

@RunWith(AndroidJUnit4::class)
class SkeletonsDaoTest {

    private lateinit var skeletonsDao: SkeletonsDao
    private lateinit var db: AppDatabase

    @Before
    fun setUp() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDatabase::class.java
        ).build()
        skeletonsDao = db.skeletonsDao()
    }

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }

    @Test
    @Throws(Exception::class)
    fun writeRecentSearchAndReadIt() {
        val skeletonsEntity = SkeletonsEntity(
            "TEST",
            listOf(RenderType.TEXT, RenderType.MESSAGE)
        )

        skeletonsDao.saveSkeletonsByContext(skeletonsEntity)
        val skeletonsByContext = skeletonsDao.provideSkeletonsByContext("TEST")
        assert(skeletonsByContext == skeletonsEntity)
    }
}