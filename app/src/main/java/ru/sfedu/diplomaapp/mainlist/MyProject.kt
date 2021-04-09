package ru.sfedu.diplomaapp.mainlist

import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.navigation.NavController
import androidx.navigation.Navigation
import ru.sfedu.diplomaapp.R
import ru.sfedu.diplomaapp.backgroundActivity.CreateTask


class MyProject : Fragment() {
    var navController: NavController? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_my_project, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        navController = Navigation.findNavController(view)

        view.findViewById<View>(R.id.fab).setOnClickListener { v: View? ->
            val builder = AlertDialog.Builder(view.context)
            val alert: AlertDialog = builder.create()
            val view1 = View.inflate(view.context, R.layout.alertdialog, null)
            alert.setView(view1)
            view1.findViewById<TextView>(R.id.addProject).setOnClickListener {
                val intent = Intent(context, CreateTask::class.java)
                startActivity(intent)
            }
            view1.findViewById<TextView>(R.id.back).setOnClickListener{
                alert.dismiss()
            }
            alert.show()

        }
    }
}