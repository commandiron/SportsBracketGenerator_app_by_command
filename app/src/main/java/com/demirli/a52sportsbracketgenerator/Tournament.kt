package com.demirli.a52sportsbracketgenerator

import android.os.Build
import kotlinx.android.synthetic.*
import java.util.concurrent.atomic.AtomicInteger
import java.util.stream.Collectors

class Tournament(
) {

    fun createRound(teamList: List<Team>): List<List<Team>>{

        val shuffledTeamList = teamList.shuffled()

        val teamPairs = shuffledTeamList.withIndex().groupBy { it.index / 2 }
            .map{it.value.map { it.value }}


        try{
            for(i in teamPairs){
                println("Finalist1 : " + i[0].name)
                println("Finalist2 : " + i[1].name)
            }
        }catch(e: Exception){

        }


        return teamPairs
    }

    fun playRound(teamPairs: List<List<Team>>): List<Team>{

        val winners = ArrayList<Team>()

        try {
            for (i in teamPairs){
                val randomNumber = (0..1).random()
                val won = i[randomNumber]
                winners.add(won)
            }
        }catch (e: Exception){
            e.printStackTrace()
        }

        for(i in winners){
            println("Won: " + i.name)
        }

        return winners
    }
}