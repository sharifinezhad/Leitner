package com.example.litner_v1

class Users {



    var id: Int = 0
    var firstName: String = ""
    var lastName: String = ""
    var rankNumber: Int = 0
    var listID = 0
    var timeStamp:Long = 100
    var readyCarD:Int = 0




    constructor()

    constructor(id: Int, firstName: String, lastName: String, rankNumber: Int, timeStamp:Long, readycard:Int) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
        this.rankNumber = rankNumber
        this.timeStamp = timeStamp
        this.readyCarD= readycard
    }

    constructor( firstName: String, lastName: String, rankNumber: Int, timeStamp:Long) {
        this.firstName = firstName
        this.lastName = lastName
        this.rankNumber = rankNumber
        this.timeStamp = timeStamp
    }

    constructor(id: Int, firstName: String, lastName: String) {
        this.id = id
        this.firstName = firstName
        this.lastName = lastName
    }

    constructor(firstName: String, lastName: String) {
        this.firstName = firstName
        this.lastName = lastName
    }
    constructor(id:Int){
        this.id =id
    }
    constructor(timeStamp: Long){
        this.timeStamp = timeStamp
    }
}