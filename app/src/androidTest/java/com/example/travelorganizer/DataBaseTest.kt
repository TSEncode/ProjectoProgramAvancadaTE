package com.example.travelorganizer


import android.database.sqlite.SQLiteDatabase
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import com.example.travelorganizer.db.CategoriesTable
import com.example.travelorganizer.db.DbOpenHelper
import com.example.travelorganizer.db.ItemsTable
import com.example.travelorganizer.db.ListTable
import com.example.travelorganizer.models.Category
import com.example.travelorganizer.models.Items
import com.example.travelorganizer.models.Lists
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

    private fun insertCategory( db: SQLiteDatabase, category : Category){
        category.id = CategoriesTable(db).insert(category.toContentValues())
        assertNotEquals(-1, category.id)
    }

    private fun insertList( db: SQLiteDatabase, list : Lists){
        list.id = ListTable(db).insert(list.toContentValues())
        assertNotEquals(-1, list.id)
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

        insertItem(db, Items("colher", null))

        db.close()
    }

    // vamos realizar um teste para verificar se uma categoria é inserida na base de dados
    @Test
    fun canInsertCategory(){
        val helper = DbOpenHelper(appContext())
        val db = helper.writableDatabase

        insertCategory(db, Category("Roupa"))

        db.close()
    }

    @Test
    fun canInserList(){
        val helper = DbOpenHelper(appContext())
        val db = helper.writableDatabase

        insertList(db, Lists("Viagem Algarve"))
        insertList(db, Lists("Viagem Salamanca"))
        insertList(db, Lists("Viagem Suíça"))

        db.close()
    }
}