package com.example.latihanrecycleview

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.latihanrecycleview.com.example.latihanrecycleview.Handphone
import com.example.latihanrecycleview.com.example.latihanrecycleview.ListHandphoneAdapter

class MainActivity : AppCompatActivity() {
    private lateinit var rvHandphones: RecyclerView
    private val list = ArrayList<Handphone>()
    private var isListView = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        rvHandphones = findViewById(R.id.rv_handphone)
        rvHandphones.setHasFixedSize(true)

        list.addAll(getListHandphones())
        showRecyclerList()
    }

    private fun getListHandphones(): ArrayList<Handphone> {
        val dataName = resources.getStringArray(R.array.data_name)
        val dataDescription = resources.getStringArray(R.array.data_description)
        val dataPhoto = resources.obtainTypedArray(R.array.data_photo)
        val listHandphone = ArrayList<Handphone>()

        for (i in dataName.indices) {
            val handphone = Handphone(dataName[i], dataDescription[i], dataPhoto.getResourceId(i, -1))
            listHandphone.add(handphone)
        }
        dataPhoto.recycle()
        return listHandphone
    }

    private fun showRecyclerList() {
        rvHandphones.layoutManager = LinearLayoutManager(this)
        val listHandphoneAdapter = ListHandphoneAdapter(list)
        rvHandphones.adapter = listHandphoneAdapter

    }

    private fun showRecyclerGrid() {
        rvHandphones.layoutManager = GridLayoutManager(this, 2)
        val adapter = ListHandphoneAdapter(list)
        rvHandphones.adapter = adapter
        setItemClickCallback(adapter)
    }

    private fun setItemClickCallback(adapter: ListHandphoneAdapter) {
        adapter.setOnItemClickCallback(object : ListHandphoneAdapter.OnItemClickCallback {
            override fun onItemClicked(data: Handphone) {
                showSelectedHandphone(data)
            }
        })
    }

    private fun showSelectedHandphone(handphone: Handphone) {
        Toast.makeText(this, "Kamu memilih " + handphone.name, Toast.LENGTH_SHORT).show()
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId) {
            R.id.action_change_view -> {
                if (isListView) {
                    showRecyclerGrid()
                    item.title = "List View"
                } else {
                    showRecyclerList()
                    item.title = "Grid View"
                }
                isListView = !isListView
                true
            }
            else -> super.onOptionsItemSelected(item)
        }
    }
}