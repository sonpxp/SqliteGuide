package com.cloudxanh.examplesqlite

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.cloudxanh.examplesqlite.adapter.FeedAdapter
import com.cloudxanh.examplesqlite.database.dao.FeedReaderDao
import com.cloudxanh.examplesqlite.database.helper.DatabaseHelper
import com.cloudxanh.examplesqlite.databinding.ActivityMainBinding
import com.cloudxanh.examplesqlite.model.Feed

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding
    private lateinit var adapter: FeedAdapter
    private lateinit var dao: FeedReaderDao
    private var dbHelper: DatabaseHelper? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        dbHelper = DatabaseHelper.getInstance(this)
        if (dbHelper != null) dao = FeedReaderDao(dbHelper!!)

        setupRecycleView()
        addDateExample()
    }

    private fun setupRecycleView() {
        val listFeed = mutableListOf<Feed>()
        val listFeed1 = dao.loadListFeed()

        adapter = FeedAdapter(object : FeedAdapter.OnItemClickListener {
            override fun onFeedItemClick(feed: Feed) {
                Toast.makeText(this@MainActivity, feed.title, Toast.LENGTH_SHORT).show()
                dao.delete(feed.title)
                setupRecycleView()
            }
        })

        // adapter.submitList(Feed.MOCKED_ITEMS)
        adapter.submitList(listFeed1)
        binding.rcvFeed.layoutManager = LinearLayoutManager(this)
        binding.rcvFeed.adapter = adapter
    }

    private fun addDateExample() {
        var i = 0
        //clearData()
        binding.fbAddItem.setOnClickListener {
            i++
            val feed = Feed("Wikipedia $i", "library $i")
            dao.insert(feed)
            Toast.makeText(this@MainActivity, "Click insert $i", Toast.LENGTH_SHORT).show()
            setupRecycleView()
        }

    }

    private fun clearData() {
        dao.deleteAll()
    }

    override fun onDestroy() {
        super.onDestroy()
        dbHelper?.close()
    }
}