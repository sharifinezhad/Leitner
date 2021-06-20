package com.example.litner_v1

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.util.Log
import android.widget.Toast
import kotlin.math.log

import kotlin.math.pow

class DatabaseHandler(context: Context) : SQLiteOpenHelper(context, DB_Name, null, DB_Version) {

    companion object {

        private val DB_Name = "DB"
        private val DB_Version = 1

        private val TABLE_NAME = "users"
        private val ID = "id"
        private val First_Name = "FirstName"
        private val Last_Name = "LastName"
        private val Rank_Number = "RankNumber"
        private val Time_Stamp = "TimeStamp"
        private val Ready_Card = "ReadyCard"//faghat hamin yek kart amade ast ya kheir

        private val TABLE_STEP = "steps"
        private val ID_STEP = "id"
        private val CAPACITY = "Capacity"
        private val READYCARDS = "ReadyCards"// majmo kol karthaye amade
        private val BROWSETIME = "BrowseTime"
        private val COLORBACKGROUND = "ColorBackground"
    }

    override fun onCreate(db: SQLiteDatabase?) {
        val CREATE_TABLE = "CREATE TABLE $TABLE_NAME " +
                "($ID INTEGER PRIMARY KEY, $First_Name TEXT, $Last_Name TEXT, $Rank_Number INTEGER," +
                " $Time_Stamp LONG , $Ready_Card INTEGER)"
        db?.execSQL(CREATE_TABLE)

        val CREATE_TABLE_STEP = "CREATE TABLE $TABLE_STEP " +
                "($ID_STEP INTEGER PRIMARY KEY, $CAPACITY INTEGER, $READYCARDS INTEGER, $BROWSETIME INTEGER, $COLORBACKGROUND)"

        db?.execSQL(CREATE_TABLE_STEP)

    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {

        db!!.execSQL("DROP TABLE IF EXISTS $TABLE_NAME")
        db.execSQL("DROP TABLE IF EXISTS $TABLE_STEP")
        onCreate(db)
    }

    // get CAPACITY
    fun getCapacity(id: Int): Int {
        val db = readableDatabase
        val capacity = "SELECT  $CAPACITY FROM $TABLE_STEP "
        val cursor = db.rawQuery(capacity, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(CAPACITY))
        cursor.close()
        db.close()
        return a.toInt()
    }



    // get READYCARDS
    fun getReadcard(id: Int): Int {
        val db = readableDatabase
        val readycard = "SELECT  $READYCARDS FROM $TABLE_STEP "
        val cursor = db.rawQuery(readycard, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(READYCARDS)).toInt()
        cursor.close()
        db.close()
        return a
    }

    // get BROWSETIME
    fun getBrowsetime(id: Int): Int {
        val db = readableDatabase
        val browsetime = "SELECT  $READYCARDS FROM $TABLE_STEP "
        val cursor = db.rawQuery(browsetime, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(BROWSETIME)).toInt()
        cursor.close()
        db.close()
        return a
    }

    //delete record step
    fun deletStep(id: Int) {

        val db = this.writableDatabase
        db.delete(TABLE_STEP, "ID_STEP = ?", arrayOf(id.toString()))


    }

    //update step
    fun updatStep(user: UsersStep): Int {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(CAPACITY, user.capaitY)
        values.put(READYCARDS, user.readyCards)
        values.put(BROWSETIME, user.browseTime)
        return db.update(TABLE_STEP, values, "ID = ?", arrayOf(user.stepId.toString()))
    }

    // ADD RECORD TO TABLE step
    fun addStep(userstep: UsersStep): Boolean {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(CAPACITY, userstep.capaitY)//
        values.put(READYCARDS, userstep.readyCards)//
        values.put(BROWSETIME, userstep.browseTime)//
        values.put(COLORBACKGROUND, userstep.colorBackground)//
        val success = db.insert(TABLE_STEP, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    //get  all step list
    fun getAllStepList(): ArrayList<UsersStep> {

        val list = ArrayList<UsersStep>()
        val db = readableDatabase
        val selectAll = "SELECT * FROM $TABLE_STEP"
        val cursor = db.rawQuery(selectAll, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val stepid = cursor.getString(cursor.getColumnIndex(ID_STEP))
                    val capacity = cursor.getString(cursor.getColumnIndex(CAPACITY))
                    val readycards = cursor.getString(cursor.getColumnIndex(READYCARDS))
                    val browsetime = cursor.getString(cursor.getColumnIndex(BROWSETIME))
                    list.add(
                        UsersStep(
                            stepid.toInt(),
                            capacity.toInt(),
                            readycards.toInt(),
                            browsetime.toInt()
                        )
                    )
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    //delete record

    fun deletUser(id: Int) {

        val db = this.writableDatabase
        db.delete(TABLE_NAME, "ID = ?", arrayOf(id.toString()))
    }

    //update user

    fun updatUser(user: Users): Int {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(First_Name, user.firstName)
        values.put(Last_Name, user.lastName)
        values.put(Rank_Number, user.rankNumber)
        values.put(Time_Stamp, user.timeStamp)
        values.put(Ready_Card, user.readyCarD)
        //values.put(Rank_Number, user.rankNumber)
        return db.update(TABLE_NAME, values, "ID = ?", arrayOf(user.id.toString()))
    }

    // update ToUp ranknumber
    fun updatToUpRankfun(id: String): String {
        val db = readableDatabase
        val yaser = "SELECT  $Rank_Number FROM $TABLE_NAME "
        val cursor = db.rawQuery(yaser, null)
        cursor.move(id.toInt())
        val a = cursor.getString(cursor.getColumnIndex(Rank_Number))
        cursor.close()
        db.close()
        val db1 = this.writableDatabase
        val values = ContentValues()
        values.put(Rank_Number, a.toInt() + 1)
        // values.put(Time_Stamp,(System.currentTimeMillis()+(86400000 * (Math.pow(2.toDouble(), a.toDouble())))))
        values.put(
            Time_Stamp,
            (System.currentTimeMillis() + (1000 * (2.toDouble().pow(a.toDouble()))))///////////////////////////////////////
        )

        return db1.update(TABLE_NAME, values, "ID = ?", arrayOf(id)).toString()
    }

    // reset ranknumber
    fun resetRanknumber(id: String): String {
        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Rank_Number, 0)
        values.put(Time_Stamp, System.currentTimeMillis())

        return db.update(TABLE_NAME, values, "ID = ?", arrayOf(id)).toString()
    }

    // ADD RECORD TO TABLE name

    fun addUser(user: Users): Boolean {

        val db = this.writableDatabase
        val values = ContentValues()

        values.put(First_Name, user.firstName)
        values.put(Last_Name, user.lastName)
        values.put(Rank_Number, 0)
        values.put(Time_Stamp, System.currentTimeMillis())
        values.put(Ready_Card, 0)
        val success = db.insert(TABLE_NAME, null, values)
        db.close()
        return (Integer.parseInt("$success") != -1)
    }

    //get user
    fun getAllUsersList(): ArrayList<Users> {

        val list = ArrayList<Users>()
        val db = readableDatabase
        val selectAll = "SELECT * FROM $TABLE_NAME "
        val cursor = db.rawQuery(selectAll, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex(ID))
                    val firstname = cursor.getString(cursor.getColumnIndex(First_Name))
                    val lastname = cursor.getString(cursor.getColumnIndex(Last_Name))
                    val ranknumber = cursor.getString(cursor.getColumnIndex(Rank_Number))
                    val timestamp = cursor.getString(cursor.getColumnIndex(Time_Stamp))
                    val readycard = cursor.getString(cursor.getColumnIndex(Ready_Card))
                    list.add(
                        Users(
                            id.toInt(),
                            firstname,
                            lastname,
                            ranknumber.toInt(),
                            timestamp.toLong(),
                            readycard.toInt()
                        )
                    )
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    // get name :yaser
    fun getname(id: Int): String {
        val db = readableDatabase
        val yaser = "SELECT  $First_Name FROM $TABLE_NAME "
        val cursor = db.rawQuery(yaser, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(First_Name))
        cursor.close()
        db.close()
        return a
    }

    // get lastname :yaser
    fun getlastname(id: String): String {
        val db = readableDatabase
        val yaser = "SELECT  $Last_Name FROM $TABLE_NAME "
        val cursor = db.rawQuery(yaser, null)
        cursor.move(id.toInt())
        val b = cursor.getString(cursor.getColumnIndex(Last_Name))
        cursor.close()
        db.close()
        return b
    }

    //get jast all with same RANKNUMBER
    fun getWithRank(rank: Int): ArrayList<Users> {
        val list = ArrayList<Users>()
        val db = readableDatabase
        val selectAll = "SELECT * FROM $TABLE_NAME WHERE $Rank_Number = $rank"
        val cursor = db.rawQuery(selectAll, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex(ID))
                    val firstname = cursor.getString(cursor.getColumnIndex(First_Name))
                    val lastname = cursor.getString(cursor.getColumnIndex(Last_Name))
                    val ranknumber = cursor.getString(cursor.getColumnIndex(Rank_Number))
                    val timestamp = cursor.getString(cursor.getColumnIndex(Time_Stamp))
                    val readycard = cursor.getString(cursor.getColumnIndex(Ready_Card))
                    list.add(
                        Users(
                            id.toInt(),
                            firstname,
                            lastname,
                            ranknumber.toInt(),
                            timestamp.toLong(),
                            readycard.toInt()
                        )
                    )
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    //get jast   ID   all with same RANKNUMBER and readycard=1
    fun get_ID_WithRank(rank: Int): ArrayList<Users> {
        val list = ArrayList<Users>()
        val db = readableDatabase
        val selectAll = "SELECT * FROM $TABLE_NAME WHERE $Rank_Number = $rank AND $Ready_Card= 1"
        val cursor = db.rawQuery(selectAll, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    val id = cursor.getString(cursor.getColumnIndex(ID))
                    list.add(Users(id.toInt()))
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    //get numbercard one ranknumber //tedad kart mojod dar yek rank
    fun get_numbercard_ranknumber(rank: Int): Int {
        var list = 0
        val db = readableDatabase
        val selectAll = "SELECT * FROM $TABLE_NAME WHERE $Rank_Number = $rank"
        val cursor = db.rawQuery(selectAll, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list += 1
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    ///////////recive time for alarm reading
    //fun reciceTime(): ArrayList<Users> {
    fun reciceTime(): Int {
        val db = readableDatabase
        val x = "SELECT  $ID FROM $TABLE_NAME  "
        val cursor = db.rawQuery(x, null)
        //cursor.moveToLast()
        cursor.moveToLast()
        val a = cursor.getString(cursor.getColumnIndex(ID)).toInt()
        cursor.close()
        db.close()
        return a
    }

    // get time_stamp :yaser
    fun getTime_Stamp(id: Int): Long {
        val db = readableDatabase
        val x = "SELECT  $Time_Stamp FROM $TABLE_NAME "
        val cursor = db.rawQuery(x, null)
        cursor.move(id)
        val b = cursor.getString(cursor.getColumnIndex(Time_Stamp)).toLong()
        cursor.close()
        db.close()
        return b
    }

    // get ranknumber whit ID
    fun getRanknumber(id: Int): Int {
        val db = readableDatabase
        val yaser = "SELECT  $Rank_Number FROM $TABLE_NAME "
        val cursor = db.rawQuery(yaser, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(Rank_Number)).toInt()
        cursor.close()
        db.close()
        return a
    }

    // set  COLORBACKGROUND
    fun setcolorBackground(id: Int): String {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLORBACKGROUND, 1)
        return db.update(TABLE_STEP, values, "ID = ?", arrayOf(id.toString())).toString()
    }

    // reset  COLORBACKGROUND
    fun resetcolorBackground(id: Int): String {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(COLORBACKGROUND, 0)
        return db.update(TABLE_STEP, values, "ID = ?", arrayOf(id.toString())).toString()
    }

    // get ColorBackground
    fun getColorBackground(id: Int): Int {
        val db = readableDatabase
        val background = "SELECT  $COLORBACKGROUND FROM $TABLE_STEP "
        val cursor = db.rawQuery(background, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(COLORBACKGROUND)).toInt()
        cursor.close()
        db.close()
        return a
    }

    //finden readycard
    fun find_readycard(rank:Int):Int{
        var readycard:Int=0
            val db = readableDatabase
            val selectAll = "SELECT * FROM $TABLE_NAME WHERE $Rank_Number = $rank AND $Ready_Card= 1"
            val cursor = db.rawQuery(selectAll, null)
            if (cursor != null) {
                if (cursor.moveToFirst()) {
                    do {
                        //Log.i("yaser",readycard.toString())
                           readycard += 1
                    } while (cursor.moveToNext())
                }
            }
            cursor.close()
            db.close()
        return readycard
    }
    // set  ReadyCard
    fun setReadyCard(id1: Int): Int {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Ready_Card, 1)
        return db.update(TABLE_NAME, values, "ID = ?", arrayOf(id1.toString()))
    }
    // set  ReadyCards
    fun setReadyCards(id1: Int,getRanknumber:Int): Int {

        val db = this.writableDatabase
        val values = ContentValues()
        //values.put(READYCARDS,getRanknumber(2))
        values.put(READYCARDS, getRanknumber)
        return db.update(TABLE_STEP, values, "ID = ?", arrayOf(id1.toString()))
        //return db.update(TABLE_NAME, values, "ID = ?", arrayOf(id1.toString()))
    }

    // reset  ReadyCards
    fun resetReadyCard(id: Int): Int {

        val db = this.writableDatabase
        val values = ContentValues()
        values.put(Ready_Card, 0)
        //db.close()
        return db.update(TABLE_NAME, values, "ID = ?", arrayOf(id.toString()))
    }

    // decriment  ReadyCards
    fun dec_ReadyCards(id1:Int,getRanknumber:Int): Int {

        val db = this.writableDatabase
        val values = ContentValues()
        //values.put(READYCARDS,getRanknumber(2))
        values.put(READYCARDS,getRanknumber-1 )
        return db.update(TABLE_STEP, values, "ID = ?", arrayOf(id1.toString()))
    }

    //get numbercard one ReadyCards //tedad kart mojod dar yek rank baraye motale
    fun get_numbercard_ReadyCard(ready: Int): Int {
        var list = 0
        val db = readableDatabase
        val selectAll = "SELECT * FROM $TABLE_NAME WHERE $Ready_Card = $ready"
        val cursor = db.rawQuery(selectAll, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list += 1
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }

    // get Ready_Card
    fun getreadycard(id: Int): Boolean {
        val db = readableDatabase
        val background = "SELECT  $Ready_Card FROM $TABLE_NAME "
        val cursor = db.rawQuery(background, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(Ready_Card)).toBoolean()
        cursor.close()
        db.close()
        return a
    }
    // get Ready_Cards
    fun getreadycards(id: Int): Int {
        val db = readableDatabase
        val background = "SELECT  $READYCARDS FROM $TABLE_STEP "
        val cursor = db.rawQuery(background, null)
        cursor.move(id)
        val a = cursor.getString(cursor.getColumnIndex(READYCARDS)).toInt()
        cursor.close()
        db.close()
        return a
    }

  /*  //get numbercard one ReadyCards //tedad kart mojod dar yek rank baraye motale
    fun get_numbercard_readycards(ready: Int): Int {
        var list = 0
        val db = readableDatabase
        val selectAll = "SELECT * FROM $TABLE_STEP WHERE $READYCARDS = $ready"
        val cursor = db.rawQuery(selectAll, null)
        if (cursor != null) {
            if (cursor.moveToFirst()) {
                do {
                    list += 1
                } while (cursor.moveToNext())
            }
        }
        cursor.close()
        db.close()
        return list
    }
    */
}

