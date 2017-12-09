package com.example.user.topsort

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*
import kotlin.collections.ArrayList

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

    }

    var n: Int = 0
    var m: Int = 0
    val st: Stack<Int> = Stack()
    var color = IntArray(0)
    var topol = IntArray(0)
    var graph = Array<ArrayList<Int>>(0, { i -> ArrayList() })

    fun dfs(color: IntArray, graph: Array<ArrayList<Int>>, v: Int): Boolean {
        if (color[v] == 1) return true
        if (color[v] == 2) return false
        color[v] = 1
        for (i in graph[v]) {
            if (dfs(color, graph, i)) return true
        }
        st.push(v)
        color[v] = 2
        return false
    }

    fun topsort(color: IntArray, graph: Array<ArrayList<Int>>, topol: IntArray): Boolean {
        var cyclic: Boolean
        for (i in 0..graph.size - 1) {
            cyclic = dfs(color, graph, i)
            if (cyclic) return false
        }
        for (i in 1..graph.size) {
            topol[st.pop()] = i
        }
        return true
    }

    fun makeVisibility(vis: Int) {
        findViewById<TextView>(R.id.edge_number).visibility = vis
        findViewById<TextView>(R.id.text_request).visibility = vis
        findViewById<TextView>(R.id.text_v).visibility = vis
        findViewById<TextView>(R.id.text_u).visibility = vis
        findViewById<EditText>(R.id.editText_u).visibility = vis
        findViewById<EditText>(R.id.editText_v).visibility = vis
        findViewById<Button>(R.id.button_graph).visibility = vis
    }

    fun doSMTH(view: View) {
        val text_n = findViewById<EditText>(R.id.editText_n)
        val text_m = findViewById<EditText>(R.id.editText_m)
        val newN = Integer.parseInt(text_n.text.toString())
        n = newN
        color = IntArray(n)
        topol = IntArray(n)
        graph = Array<ArrayList<Int>>(n, { i -> ArrayList() })
        val newM = Integer.parseInt(text_m.text.toString())
        m = newM
        val request = findViewById<TextView>(R.id.text_request)
        val st: String = "Enter " + newM.toString() + " edges"
        request.text = st
        makeVisibility(View.VISIBLE)
    }


    fun setEdges(view: View) {
        val s = findViewById<TextView>(R.id.edge_number)
        var i = Integer.parseInt(s.text[0].toString())
        if (i == m - 1) makeVisibility(View.INVISIBLE)
        val text_v = findViewById<EditText>(R.id.editText_v)
        val text_u = findViewById<EditText>(R.id.editText_u)
        val v = Integer.parseInt(text_v.text.toString())
        val u = Integer.parseInt(text_u.text.toString())
        i++
        val str = i.toString() + " edge"
        s.text = str
        text_u.text.clear()
        text_v.text.clear()
        graph[v].add(u)
    }

    fun topological_sort(view: View) {
        val ans = findViewById<TextView>(R.id.text_request)
        var s = ""
        if (!topsort(color, graph, topol)) {
            s = "Graph is cyclic"
        }
        ans.text = s
        ans.visibility = View.VISIBLE

    }
}
