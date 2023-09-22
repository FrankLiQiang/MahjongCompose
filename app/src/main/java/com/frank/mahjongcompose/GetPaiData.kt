package com.frank.mahjongcompose

import com.frank.mahjongcompose.ui.isToDraw
import com.frank.mahjongcompose.ui.kouTing
import com.frank.mahjongcompose.ui.paiNum
import com.frank.mahjongcompose.ui.searchString

class GetPaiData(private var num: Int) : Thread() {
    private var ting = 0
    fun setTingNum(ting0: Int) {
        ting = ting0
    }

    override fun run() {
        mutableData.clear()
        showHuPai(num, ting)
        isToDraw = 1 - isToDraw
    }
}

fun getPaiList() {
    huPaiCount = 0
    if (searchString.isEmpty()) {
        val thread = GetPaiData(paiNum)
        thread.setTingNum(kouTing)
        thread.start()
    } else {
        PaiDataSet.clear()
        val paiList: ArrayList<ArrayList<Int>> = arrayListOf()
        val p13: ArrayList<Int> = arrayListOf()
        for (i in 0 until searchString.length) {
            p13.add(
                searchString.substring(searchString.length - i - 1, searchString.length - i).toInt()
            )
        }
        paiList.add(p13)
        PaiDataSet[0] = paiList
        val thread = GetPaiData(0)
        thread.setTingNum(0)
        thread.start()
    }
    isToDraw = 1 - isToDraw
}