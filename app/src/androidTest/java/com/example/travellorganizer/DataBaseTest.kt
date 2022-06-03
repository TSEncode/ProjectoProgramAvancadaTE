package com.example.travellorganizer


import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.travellorganizer.db.DbOpenHelper
import com.example.travellorganizer.db.ItemsTable
import com.example.travellorganizer.models.Items
import org.junit.Assert.*
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


    private fun insertItem( db: SQLiteDatabase, items : Items){
        items.id = ItemsTable(db).insert(items.toContentValues())
        assertNotEquals(-1, items.id)
    }

    @Before
    fun deleteDB() {
        appContext().deleteDatabase(DbOpenHelper.NAME)
    }

    @Test
    fun canOpenDB(){
        val helper = DbOpenHelper(appContext())
        val db = helper.readableDatabase

        assertTrue(db.isOpen)

        db.close()
    }

    // vamos realizar um teste para verificar se um item é inserido na base de dados
    @Test
    fun canInsertItem(){
        val helper = DbOpenHelper(appContext())
        val db = helper.writableDatabase

        insertItem(db, Items("colher", 20, null))

        db.close()
    }
}