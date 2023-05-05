package com.example.mobileapp_assignmentv1.activities

import android.app.Activity
import android.content.ClipData
import android.content.Intent
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import androidx.appcompat.widget.SearchView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.mobileapp_assignmentv1.R
import com.example.mobileapp_assignmentv1.adapters.adapter
import com.example.mobileapp_assignmentv1.adapters.adapters
import com.example.mobileapp_assignmentv1.databinding.FixturesListBinding
import com.example.mobileapp_assignmentv1.main.Main
import com.example.mobileapp_assignmentv1.models.ArrayListStoreFixtures
import com.example.mobileapp_assignmentv1.models.Fixtures


class ListFixtures : AppCompatActivity(), adapters {

    lateinit var app: Main
    private lateinit var binding: FixturesListBinding
    lateinit var adapter: adapter
    //lateinit var menuHome:
    var position:Int = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FixturesListBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.toolbar.title = title
        setSupportActionBar(binding.toolbar)

        app = application as Main
adapter=adapter(app.fixtures.findAll(),this)
        val layoutManager = LinearLayoutManager(this)
        binding.recyclerView.layoutManager = layoutManager
        binding.recyclerView.adapter = adapter
        //adapter= binding.recyclerView.adapter as adapter
        (binding.recyclerView.adapter)?.notifyDataSetChanged()


      //  return true
    }

    private fun filter(text: String) {
        // creating a new array list to filter our data.
        val filteredlist: ArrayList<Fixtures> = ArrayList()

        // running a for loop to compare elements.
        for (item in app.fixtures.findAll()) {
            // checking if the entered string matched with any item of our recycler view.
            if (item.team1.toLowerCase().contains(text.toLowerCase())) {
                // if the item is matched we are
                // adding it to our filtered list.
                filteredlist.add(item)
            }
        }
        if (filteredlist.isEmpty()) {
            // if no item is added in filtered list we are
            // displaying a toast message as no data found.
            Toast.makeText(this, "No Data Found..", Toast.LENGTH_SHORT).show()
        } else {
            // at last we are passing that filtered
            // list to our adapter class.
            adapter.filterList(filteredlist)
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        menuInflater.inflate(R.menu.menu_home_screen, menu)
        return super.onCreateOptionsMenu(menu)
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.add -> {
                val launcherIntent = Intent(this, CreateFixtures::class.java)
                getResult.launch(launcherIntent)
            }
            R.id.search ->{
                // below line is to get our menu item.
                val searchItem: MenuItem = item

                // getting search view of our item.
                val searchView: SearchView = searchItem.getActionView() as SearchView
                // below line is to call set on query text listener method.
                searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener,
                    android.widget.SearchView.OnQueryTextListener {
                    override fun onQueryTextSubmit(p0: String?): Boolean {
                        return false
                    }

                    override fun onQueryTextChange(msg: String): Boolean {
                        // inside on query text change method we are
                        // calling a method to filter our recycler view.
                        filter(msg)
                        return false
                    }
                })
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private val getResult =
        registerForActivityResult(
            ActivityResultContracts.StartActivityForResult()
        ) {
            if (it.resultCode == Activity.RESULT_OK) {
                (binding.recyclerView.adapter)?.
                notifyItemRangeChanged(0,app.fixtures.findAll().size)
            }
            else if (it.resultCode == Activity.RESULT_FIRST_USER) {
                (binding.recyclerView.adapter)?.notifyItemRemoved(position)
            }
        }
    override fun fixturesClick(dataClassClubNames: Fixtures, pos:Int) {
        position=pos
        val launcherIntent = Intent(this, CreateFixtures::class.java)
        launcherIntent.putExtra("Edit", dataClassClubNames)
        getResult.launch(launcherIntent)
    }
}

/*
1. The ListFixtures class extends the AppCompatActivity class and implements an adapters interface.

2. The class declares a Main variable called app and initializes it later on. This Main variable is
likely a reference to an instance of the Main class, which is a custom class defined elsewhere in the code.

3. In the onCreate() method, the code inflates the FixturesListBinding layout using the layoutInflater,
sets the ContentView to the root view of the binding, sets the title of the binding.toolbar, and sets
the binding.toolbar as the action bar.

4. The code then initializes a LinearLayoutManager and sets it as the layout manager for the binding.recyclerView
using binding.recyclerView.layoutManager = layoutManager.

5. The code creates an instance of an adapter class (defined elsewhere in the code) using adapter(app.fixtures.findAll(),this)
 and sets it as the adapter for the binding.recyclerView using binding.recyclerView.adapter = ....

6. The onCreateOptionsMenu() method inflates a menu resource file called menu_home_screen and sets it
as the menu for the activity.

7. The onOptionsItemSelected() method is called when a menu item is selected. If the selected item has
an ID of R.id.add, the code launches a new activity called CreateFixtures using an Intent.

8. The code defines a getResult variable using the registerForActivityResult() method. This variable
listens for results from the CreateFixtures activity and updates the binding.recyclerView adapter accordingly.

9. The fixturesClick() method is called when a fixture item in the binding.recyclerView is clicked.
It sets the position variable and launches the CreateFixtures activity with an Intent and an extra dataClassClubNames object.

Overall, this code sets up a RecyclerView with a custom adapter, inflates a menu resource file, and
listens for menu item clicks and RecyclerView item clicks. The getResult variable listens for results
from a separate activity and updates the RecyclerView adapter accordingly.
 */