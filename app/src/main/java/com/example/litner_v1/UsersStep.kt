package com.example.litner_v1

class UsersStep {



        var stepId: Int = 0
        var capaitY: Int = 0
        var readyCards: Int = 0
        var browseTime: Int = 0
        var colorBackground:Int=0

        constructor()
    constructor(stepid: Int, capaity: Int, readycards: Int, browsetime: Int, colorBackground:Int) {
        this.stepId = stepid
        this.capaitY = capaity
        this.readyCards = readycards
        this.browseTime= browsetime
        this.colorBackground= colorBackground
    }
        constructor(stepid: Int, capaity: Int, readycards: Int, browsetime: Int) {
            this.stepId = stepid
            this.capaitY = capaity
            this.readyCards = readycards
            this.browseTime= browsetime
        }


        constructor(stepid: Int, capaity: Int, readycards: Int) {
            this.stepId = stepid
            this.capaitY = capaity
            this.readyCards = readycards
        }

        constructor(capaity: Int, readycards: Int) {
            this.capaitY = capaity
            this.readyCards = readycards
        }


    }
