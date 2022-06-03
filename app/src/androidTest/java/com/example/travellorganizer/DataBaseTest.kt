package com.example.travellorganizer

import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.travellorganizer.db.DbOpenHelper
import junit.framework.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)

/*
    Vão ser realizados testes à base de dados, se esta é criada e se é possível inserir e ler dados da mesma
 */
class DataBaseTest {
    private fun appContext() =
        InstrumentationRegistry.getInstrumentation().targetContext

    @Before
    fun deleteDB(){
        appContext().deleteDatabase(DbOpenHelper.NAME)
    }

    @Test

    fun canOpenDB(){
        val helper = DbOpenHelper(appContext())
        val db = helper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }
}