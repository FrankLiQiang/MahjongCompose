package com.frank.mahjongcompose

import com.frank.mahjongcompose.ui.isPaiType
import com.frank.mahjongcompose.ui.isSpecify
import com.frank.mahjongcompose.ui.kouTing
import java.util.Vector

lateinit var root: NonBinTree
private val PaiXingKu: HashMap<ArrayList<Int>, Int> = HashMap()
val PaiDataSet: HashMap<Int, ArrayList<ArrayList<Int>>> = HashMap()
var forest: ArrayList<NonBinTree> = arrayListOf()
var Pai13: ArrayList<Int> = arrayListOf()
private val AllPaiDataSet: HashMap<Int, Vector<Any>> = HashMap()
var huPaiCount = 0

fun showHuPai(num: Int, ting: Int) {

    if (kouTing != 10 || isSpecify) {
        if (num > 0 && num % 3 == 0) {
            return
        }
    }
    root = NonBinTree(0)
    root.count = 4
    PaiXingKu.clear()
    if (PaiDataSet.isEmpty() || PaiDataSet[num]!!.isEmpty()) {
        forest.clear()
        addNodeRecursion(root, num, num)
        val paiList: ArrayList<ArrayList<Int>> = arrayListOf()
        for (f in forest.indices) {
            val p13: ArrayList<Int> = arrayListOf()
            getParentTop(p13, forest[f])
            paiList.add(p13)
        }
        PaiDataSet[num] = paiList
    }
    val pList = PaiDataSet[num]

    //输出所有组合 Start
    if (kouTing == 10 && !isSpecify) {
        val s: String
        if (AllPaiDataSet[num] == null) {
            huPaiCount = 0
            val sb = StringBuffer()
            var maxSize = pList!!.size
            if (num > 7) maxSize = 5000
            for (f in 0 until maxSize) {
                Pai13 = pList[f]
                huPaiCount++
                sb.append("($huPaiCount)")
                for (k in Pai13.indices) {
                    sb.append(Pai13[Pai13.size - k - 1])
                }
                sb.append("\n")
            }
            if (num > 7) {
                sb.append("......")
            }
            sb.append("\n")
            s = sb.toString()
            val v = Vector<Any>()
            huPaiCount = pList.size
            v.add(huPaiCount)
            v.add(s)
            AllPaiDataSet[num] = v
        } else {
            val v = AllPaiDataSet[num]
            huPaiCount = v!![0] as Int
        }
        return
    }
    for (f in pList!!.indices) {
        Pai13 = pList[f]
        val huSting: MutableList<String> = ArrayList()
        val huPai: MutableList<Int> = ArrayList()
        val head: MutableList<String> = ArrayList()
        val tail: MutableList<String> = ArrayList()
        var isSame = true
        for (n in 1..9) {
            var count = 0
            for (i in Pai13.indices) {
                if (Pai13.get(i) == n) {
                    count++
                }
            }
            if (count == 4) {
                continue
            }
            val paiNew: ArrayList<Int> = arrayListOf()
            var k = 0
            for (i in Pai13.indices) {
                if (n >= Pai13.get(Pai13.size - i - 1)) {
                    paiNew.add(Pai13.get(Pai13.size - i - 1))
                    k = i
                }
            }
            paiNew.add(n)
            for (i in k until Pai13.size) {
                if (n < Pai13.get(Pai13.size - i - 1)) {
                    paiNew.add(Pai13.get(Pai13.size - i - 1))
                }
            }
            val HuPaiString: String = getHuPaiString(paiNew).toString()
            if (!HuPaiString.isEmpty()) {
                if (HuPaiString.length > 3) {
                    if (HuPaiString.substring(2, 3) == " ") {
                        head.add(HuPaiString.substring(3, 6))
                    } else {
                        head.add(HuPaiString.substring(0, 3))
                    }
                    tail.add(HuPaiString.substring(HuPaiString.length - 4))
                }
                huPai.add(n)
                huSting.add(HuPaiString)
            }
        }
        if (head.size > 1) {
            val firstHead = head[0]
            val firstTail = tail[0]
            for (i in 0 until head.size - 1) {
                if (firstHead != head[i + 1]) {
                    isSame = false
                    break
                }
            }
            if (isSame) {
                huSting.clear()
            } else {
                isSame = true
                for (i in 0 until tail.size - 1) {
                    if (firstTail != tail[i + 1]) {
                        isSame = false
                        break
                    }
                }
                if (isSame) {
                    huSting.clear()
                }
            }
        }
        if (huSting.size == ting || ting == 0) {     //设定几口听，才输出
            // 搜索牌型库，判断是否已经存在
            var isShow = false
            if (isPaiType) {
                val thisPaiXing = getPaiXing(Pai13)
                if (!isExist(thisPaiXing)) {
                    PaiXingKu.put(thisPaiXing, 1)
                    isShow = true
                }
            } else {
                isShow = true
            }
            if (isShow) {
                huPaiCount++
                val sb0 = StringBuilder()
                sb0.append("($huPaiCount)")
                for (i in Pai13.indices) {
                    sb0.append(Pai13.get(Pai13.size - i - 1))
                }
                val sb1 = StringBuilder()
                for (i in huSting.indices) {
                    sb1.append(
                        """  ${huPai[i]}: ${huSting[i]}
"""
                    )
                }
                val info = Information()
                info.item = sb0.toString()
                if (sb1.length > 2) {
                    info.password = sb1.toString().substring(0, sb1.length - 2)
                }
                mutableData.add(info)
            }
        }
    }
}

class NonBinTree(var rootVal: Int) {
    var parent: NonBinTree? = null
    var count = 0
    var allCount = 0
    val nodes: ArrayList<NonBinTree> = arrayListOf()
    fun addNode(node: NonBinTree) {
        node.parent = this
        nodes.add(node)
    }
}

private fun addNodeRecursion(node: NonBinTree, maxN: Int, rawN: Int) {
    if (maxN <= 0) {
        if (node.allCount == rawN) {
            forest.add(node)
        }
        return
    }
    val last: Int
    if (node.count == 4) {
        last = node.rootVal + 1
        if (last > 9) {
            if (node.count == rawN) {
                forest.add(node)
            }
            return
        }
        for (i in last..9) {
            val nd = NonBinTree(i)
            nd.count = 1
            nd.allCount = node.allCount + 1
            node.addNode(nd)
            addNodeRecursion(nd, maxN - 1, rawN)
        }
    } else {
        last = node.rootVal
        val c: Int = node.count
        for (i in last..9) {
            val nd = NonBinTree(i)
            nd.allCount = node.allCount + 1
            if (i == last) {
                nd.count = c + 1
            } else {
                nd.count = 1
            }
            node.addNode(nd)
            addNodeRecursion(nd, maxN - 1, rawN)
        }
    }
}

private fun getParentTop(p: MutableList<Int>, node: NonBinTree) {
    p.add(node.rootVal)
    if (node.parent != null && node.parent!!.rootVal != 0) {
        getParentTop(p, node.parent!!)
    }
}

private fun getHuPaiString(pai: ArrayList<Int>): java.lang.StringBuilder {
    val s = java.lang.StringBuilder()
    val len0 = pai.size
    if (len0 % 3 == 1) {
        return s
    }
    if (len0 % 3 == 2) {
        if (len0 == 2) {
            val isJ: Boolean = isJiang(pai[0], pai[1])
            if (isJ) {
                s.append(pai[0].toString() + pai[1])
            }
            return s
        }
        for (i in 0 until len0 - 1) {
            if (isJiang(pai[i], pai[i + 1])) {
                val paiNew: ArrayList<Int> = arrayListOf()
                for (j in pai.indices) {
                    if (j != i && j != i + 1) {
                        paiNew.add(pai[j])
                    }
                }
                val pai13 = isHuPai3(paiNew)
                if (pai13.toString().isNotEmpty()) {
                    s.append(pai[i].toString() + pai[i + 1].toString() + " " + pai13)
                    return s
                }
            }
        }
        return s
    }
    return isHuPai3(pai)
}

private fun isJiang(a: Int, b: Int): Boolean {
    return a == b
}

private fun getPaiXing(p: ArrayList<Int>): ArrayList<Int> {
    var i = 10
    for (k in p.indices) {
        if (p[k] < i) {
            i = p[k]
        }
    }
    val d = i - 1
    if (d <= 0) {
        return p
    }
    val newP: ArrayList<Int> = arrayListOf()
    for (k in p.indices) {
        newP.add(p[k] - d)
    }
    return newP
}

private fun isExist(a: ArrayList<Int>): Boolean {
    return PaiXingKu[a] != null
}

private fun isHuPai3(p: ArrayList<Int>): java.lang.StringBuilder {
    val s = java.lang.StringBuilder()
    val len3 = p.size
    run {
        var i = 0
        while (i < len3) {
            if (!isKezi(
                    p[i],
                    p[i + 1],
                    p[i + 2]
                ) &&
                !isShunzi(
                    p[i],
                    p[i + 1],
                    p[i + 2]
                )
            ) {
                return s
            }
            i += 3
        }
    }
    var i = 0
    while (i < len3) {
        s.append(p[i].toString() + p[i + 1].toString() + p[i + 2].toString() + " ")
        i += 3
    }
    return s
}

private fun isKezi(a: Int, b: Int, c: Int): Boolean {
    return a == b && b == c
}

private fun isShunzi(a: Int, b: Int, c: Int): Boolean {
    return a + 1 == b && b + 1 == c
}
