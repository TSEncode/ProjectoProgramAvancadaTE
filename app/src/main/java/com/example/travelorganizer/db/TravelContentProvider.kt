package com.example.travelorganizer.db

import android.content.ClipData
import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns
import com.example.travelorganizer.models.ListTravel

class TravelContentProvider : ContentProvider() {

    var dbOpenHelper : DbOpenHelper? = null

    /**
     * Implement this to initialize your content provider on startup.
     * This method is called for all registered content providers on the
     * application main thread at application launch time.  It must not perform
     * lengthy operations, or application startup will be delayed.
     *
     *
     * You should defer nontrivial initialization (such as opening,
     * upgrading, and scanning databases) until the content provider is used
     * (via [.query], [.insert], etc).  Deferred initialization
     * keeps application startup fast, avoids unnecessary work if the provider
     * turns out not to be needed, and stops database errors (such as a full
     * disk) from halting application launch.
     *
     *
     * If you use SQLite, [android.database.sqlite.SQLiteOpenHelper]
     * is a helpful utility class that makes it easy to manage databases,
     * and will automatically defer opening until first use.  If you do use
     * SQLiteOpenHelper, make sure to avoid calling
     * [android.database.sqlite.SQLiteOpenHelper.getReadableDatabase] or
     * [android.database.sqlite.SQLiteOpenHelper.getWritableDatabase]
     * from this method.  (Instead, override
     * [android.database.sqlite.SQLiteOpenHelper.onOpen] to initialize the
     * database when it is first opened.)
     *
     * @return true if the provider was successfully loaded, false otherwise
     */
    override fun onCreate(): Boolean {
        dbOpenHelper = DbOpenHelper(context)

        return true
    }

    /**
     * Implement this to handle query requests from clients.
     *
     *
     * Apps targeting [android.os.Build.VERSION_CODES.O] or higher should override
     * [.query] and provide a stub
     * implementation of this method.
     *
     *
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Example client call:
     *
     *
     * <pre>// Request a specific record.
     * Cursor managedCursor = managedQuery(
     * ContentUris.withAppendedId(Contacts.People.CONTENT_URI, 2),
     * projection,    // Which columns to return.
     * null,          // WHERE clause.
     * null,          // WHERE clause value substitution
     * People.NAME + " ASC");   // Sort order.</pre>
     * Example implementation:
     *
     *
     * <pre>// SQLiteQueryBuilder is a helper class that creates the
     * // proper SQL syntax for us.
     * SQLiteQueryBuilder qBuilder = new SQLiteQueryBuilder();
     *
     * // Set the table we're querying.
     * qBuilder.setTables(DATABASE_TABLE_NAME);
     *
     * // If the query ends in a specific record number, we're
     * // being asked for a specific record, so set the
     * // WHERE clause in our query.
     * if((URI_MATCHER.match(uri)) == SPECIFIC_MESSAGE){
     * qBuilder.appendWhere("_id=" + uri.getPathLeafId());
     * }
     *
     * // Make the query.
     * Cursor c = qBuilder.query(mDb,
     * projection,
     * selection,
     * selectionArgs,
     * groupBy,
     * having,
     * sortOrder);
     * c.setNotificationUri(getContext().getContentResolver(), uri);
     * return c;</pre>
     *
     * @param uri The URI to query. This will be the full URI sent by the client;
     * if the client is requesting a specific record, the URI will end in a record number
     * that the implementation should parse and add to a WHERE or HAVING clause, specifying
     * that _id value.
     * @param projection The list of columns to put into the cursor. If
     * `null` all columns are included.
     * @param selection A selection criteria to apply when filtering rows.
     * If `null` then all rows are included.
     * @param selectionArgs You may include ?s in selection, which will be replaced by
     * the values from selectionArgs, in order that they appear in the selection.
     * The values will be bound as Strings.
     * @param sortOrder How the rows in the cursor should be sorted.
     * If `null` then the provider is free to define the sort order.
     * @return a Cursor or `null`.
     */
    override fun query(
        uri: Uri,
        projection: Array<out String>?,
        selection: String?,
        selectionArgs: Array<out String>?,
        sortOrder: String?
    ): Cursor? {
        val db = dbOpenHelper!!.readableDatabase

        requireNotNull(projection)
        val columns = projection as Array<String>
        val argsSelections = selectionArgs as Array<String>?

        val id = uri.lastPathSegment

        return  when (getUriMatcher().match(uri)) {
            URI_ITEMS -> ItemsTable(db).query(columns, selection, argsSelections, null, null, sortOrder)
            URI_CATEGORIES -> CategoriesTable(db).query(columns, selection, argsSelections, null, null, sortOrder)
            URI_LISTS -> ListTable(db).query(columns, selection, argsSelections, null, null, sortOrder)
            URI_TRAVEL ->TravelsTable(db).query(columns, selection, argsSelections, null, null, sortOrder)
            URI_LIST_TRAVEL -> ListTravelTable(db).query(columns, selection, argsSelections, null, null, sortOrder)
            URI_LIMITED_TRAVEL -> TravelsTable(db).query(columns, selection, argsSelections, null, null, "${TravelsTable.FIELD_NAME} ASC",  "3")
            URI_LIST_ITEM -> ListItemsTable(db).query(columns, selection, argsSelections, null, null, sortOrder)
            URI_SPECIFIC_ITEM -> ItemsTable(db).query(columns, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_SPECIFIC_CATEGORY -> CategoriesTable(db).query(columns, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_SPECIFIC_LISTS -> ListTable(db).query(columns, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_SPECIFIC_TRAVEL -> TravelsTable(db).query(columns, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_SPECIFIC_LIST_TRAVEL -> ListTravelTable(db).query(columns, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_SPECIFIC_LIST_ITEM -> ListItemsTable(db).query(columns, "${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_GET_LIST_ITEM -> ItemsTable(db).queryItemList(columns, selection, argsSelections, null, null, sortOrder, null)
            else -> null
        }

    }

    /**
     * Implement this to handle requests for the MIME type of the data at the
     * given URI.  The returned MIME type should start with
     * `vnd.android.cursor.item` for a single record,
     * or `vnd.android.cursor.dir/` for multiple items.
     * This method can be called from multiple threads, as described in
     * [Processes
 * and Threads]({@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * Note that there are no permissions needed for an application to
     * access this information; if your content provider requires read and/or
     * write permissions, or is not exported, all applications can still call
     * this method regardless of their access permissions.  This allows them
     * to retrieve the MIME type for a URI when dispatching intents.
     *
     * @param uri the URI to query.
     * @return a MIME type string, or `null` if there is no type.
     */
    override fun getType(uri: Uri): String? =
        when (getUriMatcher().match(uri)) {
            URI_ITEMS -> "$MULTIPLE_RECORDS/${ItemsTable.NAME}"
            URI_CATEGORIES -> "$MULTIPLE_RECORDS/${CategoriesTable.NAME}"
            URI_TRAVEL -> "$MULTIPLE_RECORDS/${TravelsTable.NAME}"
            URI_LIST_ITEM -> "$MULTIPLE_RECORDS/${ListItemsTable.NAME}"
            URI_LIST_TRAVEL -> "$MULTIPLE_RECORDS/${ListTravelTable.NAME}"
            URI_LISTS -> "$MULTIPLE_RECORDS/${ListTable.NAME}"
            URI_SPECIFIC_ITEM -> "$UNIQUE_RECORD/${ItemsTable.NAME}"
            URI_SPECIFIC_TRAVEL -> "$UNIQUE_RECORD/${TravelsTable.NAME}"
            URI_SPECIFIC_LISTS -> "$UNIQUE_RECORD/${ListTable.NAME}"
            URI_SPECIFIC_CATEGORY -> "$UNIQUE_RECORD/${CategoriesTable.NAME}"
            URI_SPECIFIC_LIST_TRAVEL -> "$UNIQUE_RECORD/${ListTravelTable.NAME}"
            URI_SPECIFIC_LIST_ITEM -> "$UNIQUE_RECORD/${ListItemsTable.NAME}"


            else -> null
        }

    /**
     * Implement this to handle requests to insert a new row. As a courtesy,
     * call
     * [ notifyChange()][ContentResolver.notifyChange] after inserting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The content:// URI of the insertion request.
     * @param values A set of column_name/value pairs to add to the database.
     * @return The URI for the newly inserted item.
     */
    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val db = dbOpenHelper!!.writableDatabase

        requireNotNull(values)

        val id = when (getUriMatcher().match(uri)) {
            URI_CATEGORIES -> CategoriesTable(db).insert(values)
            URI_LISTS -> ListTable(db).insert(values)
            URI_ITEMS -> ItemsTable(db).insert(values)
            URI_TRAVEL -> TravelsTable(db).insert(values)
            URI_LIST_TRAVEL -> ListTravelTable(db).insert(values)
            URI_LIST_ITEM -> ListItemsTable(db).insert(values)
            else -> -1
        }

        if (id == -1L) return null

        return Uri.withAppendedPath(uri, "$id")
    }

    /**
     * Implement this to handle requests to delete one or more rows. The
     * implementation should apply the selection clause when performing
     * deletion, allowing the operation to affect multiple rows in a directory.
     * As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after deleting. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     *
     * The implementation is responsible for parsing out a row ID at the end of
     * the URI, if a specific row is being deleted. That is, the client would
     * pass in `content://contacts/people/22` and the implementation
     * is responsible for parsing the record number (22) when creating a SQL
     * statement.
     *
     * @param uri The full URI to query, including a row ID (if a specific
     * record is requested).
     * @param selection An optional restriction to apply to rows when deleting.
     * @return The number of rows affected.
     * @throws SQLException
     */
    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<out String>?): Int {
        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        return  when (getUriMatcher().match(uri)) {
            URI_CATEGORIES -> CategoriesTable(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_LISTS -> ListTable(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_TRAVEL -> TravelsTable(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_ITEMS -> ItemsTable(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_LIST_TRAVEL -> ListTravelTable(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            URI_LIST_ITEM -> ListItemsTable(db).delete("${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

    }

    /**
     * Implement this to handle requests to update one or more rows. The
     * implementation should update all rows matching the selection to set the
     * columns according to the provided values map. As a courtesy, call
     * [ notifyChange()][ContentResolver.notifyChange] after updating. This method can be called from multiple
     * threads, as described in [Processes
 * and Threads](
      {@docRoot}guide/topics/fundamentals/processes-and-threads.html#Threads).
     *
     * @param uri The URI to query. This can potentially have a record ID if
     * this is an update request for a specific record.
     * @param values A set of column_name/value pairs to update in the database.
     * @param selection An optional filter to match rows to update.
     * @return the number of rows affected.
     */
    override fun update(
        uri: Uri,
        values: ContentValues?,
        selection: String?,
        selectionArgs: Array<out String>?
    ): Int {
        requireNotNull(values)

        val db = dbOpenHelper!!.writableDatabase

        val id = uri.lastPathSegment

        return when (getUriMatcher().match(uri)) {
                URI_SPECIFIC_CATEGORY -> CategoriesTable(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
                URI_SPECIFIC_ITEM -> ItemsTable(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
                URI_SPECIFIC_LISTS -> ListTable(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
                URI_SPECIFIC_TRAVEL -> TravelsTable(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
                URI_SPECIFIC_LIST_ITEM -> ListItemsTable(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
                URI_SPECIFIC_LIST_TRAVEL -> ListTravelTable(db).update(values, "${BaseColumns._ID}=?", arrayOf("${id}"))
            else -> 0
        }

    }

    companion object {
        const val AUTHORITY = "com.example.travelorganizer"

        const val URI_CATEGORIES = 100
        const val URI_SPECIFIC_CATEGORY = 101
        const val URI_ITEMS = 200
        const val URI_SPECIFIC_ITEM = 201
        const val URI_GET_LIST_ITEM = 202
        const val URI_LISTS = 300
        const val URI_SPECIFIC_LISTS = 301
        const val URI_TRAVEL = 400
        const val URI_SPECIFIC_TRAVEL = 401
        const val URI_LIMITED_TRAVEL = 402
        const val URI_LIST_TRAVEL = 500
        const val URI_SPECIFIC_LIST_TRAVEL = 501
        const val URI_LIST_ITEM = 600
        const val URI_SPECIFIC_LIST_ITEM = 601

        const val UNIQUE_RECORD = "vnd.android.cursor.item"
        const val MULTIPLE_RECORDS = "vnd.android.cursor.dir"

        private val BASE_URL = Uri.parse("content://$AUTHORITY")

        val CATEGORY_URL =Uri.withAppendedPath(BASE_URL, CategoriesTable.NAME)
        val ITEM_URL =Uri.withAppendedPath(BASE_URL, ItemsTable.NAME)
        val TRAVEL_URL =Uri.withAppendedPath(BASE_URL, TravelsTable.NAME)
        val LIST_URL =Uri.withAppendedPath(BASE_URL, ListTable.NAME)
        val LIST_ITEM_URL =Uri.withAppendedPath(BASE_URL, ListItemsTable.NAME)
        val LIST_TRAVEL_URL =Uri.withAppendedPath(BASE_URL, ListTravelTable.NAME)
        val LIST_GET_URL = Uri.withAppendedPath(ITEM_URL, URI_GET_LIST_ITEM.toString())
        val TRAVEL_LIMIT_URL = Uri.withAppendedPath(TRAVEL_URL, URI_LIMITED_TRAVEL.toString())

        fun getUriMatcher() : UriMatcher {
            val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

            uriMatcher.addURI(AUTHORITY, CategoriesTable.NAME, URI_CATEGORIES)
            uriMatcher.addURI(AUTHORITY, "${CategoriesTable.NAME}/#", URI_SPECIFIC_CATEGORY)
            uriMatcher.addURI(AUTHORITY, ItemsTable.NAME, URI_ITEMS)
            uriMatcher.addURI(AUTHORITY, "${ItemsTable.NAME}/#", URI_SPECIFIC_ITEM)
            uriMatcher.addURI(AUTHORITY, TravelsTable.NAME, URI_TRAVEL)
            uriMatcher.addURI(AUTHORITY, "${TravelsTable.NAME}/#", URI_SPECIFIC_TRAVEL)
            uriMatcher.addURI(AUTHORITY, "${TravelsTable.NAME}/#", URI_LIMITED_TRAVEL)
            uriMatcher.addURI(AUTHORITY, ListItemsTable.NAME, URI_LIST_ITEM)
            uriMatcher.addURI(AUTHORITY, "${ListItemsTable.NAME}/#", URI_SPECIFIC_LIST_ITEM)
            uriMatcher.addURI(AUTHORITY, ListTable.NAME, URI_LISTS)
            uriMatcher.addURI(AUTHORITY, "${ListTable.NAME}/#", URI_SPECIFIC_LISTS)
            uriMatcher.addURI(AUTHORITY, "${ItemsTable.NAME}/#", URI_GET_LIST_ITEM)
            uriMatcher.addURI(AUTHORITY, ListTravelTable.NAME, URI_LIST_TRAVEL)
            uriMatcher.addURI(AUTHORITY, "${ListTravelTable.NAME}/#", URI_SPECIFIC_LIST_TRAVEL)

            return uriMatcher

        }
    }
}
