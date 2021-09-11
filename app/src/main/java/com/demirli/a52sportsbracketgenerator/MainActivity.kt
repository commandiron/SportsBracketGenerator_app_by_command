package com.demirli.a52sportsbracketgenerator

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Adapter
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import java.util.function.Predicate
import kotlin.collections.ArrayList
import kotlin.math.sqrt

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val recyclerViewList = listOf(
            round_one_RecyclerView,
            round_two_RecyclerView,
            round_three_RecyclerView,
            round_four_RecyclerView,
            round_five_RecyclerView,
            round_six_RecyclerView)

        val titleTextViewList = listOf(
            round_one_title_tv,
            round_two_title_tv,
            round_three_title_tv,
            round_four_title_tv,
            round_five_title_tv,
            round_six_title_tv
        )

        val teamA = Team("Fenerbahçe")
        val teamB = Team("Galatasaray")
        val teamC = Team("Beşiktaş")
        val teamD = Team("Trabzonspor")
        val teamE = Team("Sivasspor")
        val teamF = Team("Rizespor")
        val teamG = Team("Göztepe")
        val teamH = Team("Gençlerbirliği")

        val teamList = listOf(teamA, teamB, teamC, teamD, teamE, teamF, teamG, teamH)

        generate_btn.setOnClickListener {

            deleteDataFromRecyclerView(recyclerViewList)
            winner_text_tv.text = ""
            winner_tv.text = ""

            if(editText.text.toString().toInt() > 64){
                Toast.makeText(this, "Maksimum değer 64.", Toast.LENGTH_SHORT).show()
            }else{
                val generatedTeamList = generateTeams(editText.text.toString().toInt())
                createTournament(generatedTeamList, recyclerViewList)
            }

            generateTitle(recyclerViewList, titleTextViewList)
        }
    }

    fun generateTeams(teamSize: Int): ArrayList<Team>{
        val teamList = ArrayList<Team>()

        val teamListData = listOf(
            "Team A","Team B","Team C","Team D",
            "Team E","Team F","Team G","Team H",
            "Team I","Team J","Team K","Team L",
            "Team M","Team N","Team O","Team P",
            "Team Q","Team R","Team S","Team T",
            "Team U","Team V","Team W","Team X",
            "Team Y","Team Z","Team A2","Team B2",
            "Team C2","Team D2","Team E2","Team F2",
            "Team G2","Team H2","Team I2","Team J2",
            "Team K2","Team L2","Team M2","Team N2",
            "Team O2","Team P2","Team Q2","Team R2",
            "Team S2","Team T2","Team U2","Team V2",
            "Team W2","Team X2","Team Y2","Team Z2",
            "Team A3","Team B3","Team C3","Team D3",
            "Team E3","Team F3","Team G3","Team H3",
            "Team I3","Team J3","Team K3","Team L3",
            "Team M3","Team N3","Team O3","Team P3",
            "Team Q3","Team R3","Team S3","Team T3",
            "Team U3","Team V3","Team W3","Team X3",
            "Team Y3","Team Z3")

        for(i in 0 until teamSize){
            teamList.add(Team(teamListData[i]))
        }
        return teamList
    }

    fun createTournament(teamList: List<Team>, recyclerViewList: List<RecyclerView>){

        for(i in recyclerViewList){
            i.layoutManager = LinearLayoutManager(this)
        }

        val tournament = Tournament()

        val primeFactors = getPrimeFactors(teamList.size.toLong())

        if(!primeFactors.all{it.equals(2)}){
            Toast.makeText(this, "Lütfen 2'nin üssü olan bir sayı giriniz.", Toast.LENGTH_SHORT).show()
        }else{

            val finalSize = primeFactors.size
            var teamPairs = tournament.createRound(teamList)

            val adapter = TeamAdapter(teamPairs)
            recyclerViewList[0].adapter = adapter

            var winners = tournament.playRound(teamPairs)

            for(i in 0 until finalSize - 1){
                teamPairs = tournament.createRound(winners)
                winners = tournament.playRound(teamPairs)

                val adapter = TeamAdapter(teamPairs)
                recyclerViewList[i+1].adapter = adapter
            }

            try {
                winner_text_tv.text = "Winner: "
                winner_tv.text = winners[0].name
            }catch (e: Exception){
             e.printStackTrace()
            }
        }
    }

    fun getPrimeFactors(number: Long): ArrayList<Int> {
        var number = number
        val setPrimeFactors = ArrayList<Int>()
        var i = 2

        while (i <= number) {
            if (number % i == 0L) {
                // Add prime factor in Hash Set
                setPrimeFactors.add(i)
                number /= i
                i--
            }
            i++
        }
        return setPrimeFactors
    }

    fun deleteDataFromRecyclerView(recyclerViewList: List<RecyclerView>){
        val adapter = TeamAdapter(listOf())
        for(i in recyclerViewList){
            i.adapter = adapter
            adapter.notifyDataSetChanged()
        }
    }

    fun generateTitle(recyclerViewList: List<RecyclerView>, titleTextViewList: List<TextView>){
        for(i in 0 until recyclerViewList.size){
            if(recyclerViewList[i].adapter!!.itemCount == 0){
                titleTextViewList[i].text = ""
            }else if(recyclerViewList[i].adapter!!.itemCount == 1){
                titleTextViewList[i].text = "Final"
            }else if(recyclerViewList[i].adapter!!.itemCount == 2){
                titleTextViewList[i].text = "Semi Final"
            }else if(recyclerViewList[i].adapter!!.itemCount == 4){
                titleTextViewList[i].text = "Quarter Final"
            }else{
                titleTextViewList[i].text = "Qualifications"
            }
        }
    }
}
