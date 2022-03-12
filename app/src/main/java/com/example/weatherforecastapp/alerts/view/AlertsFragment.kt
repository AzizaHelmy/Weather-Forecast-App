package com.example.weatherforecastapp.alerts.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.weatherforecastapp.databinding.AddAlertDialogBinding
import com.example.weatherforecastapp.databinding.FragmentAlertsBinding
import java.util.*

class AlertsFragment : Fragment() {
    private lateinit var binding: FragmentAlertsBinding
    private lateinit var builder: AlertDialog.Builder
    private lateinit var bindingDialog: AddAlertDialogBinding
    private lateinit var dialog: AlertDialog
    private lateinit var mTimePicker: TimePickerDialog
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertsBinding.inflate(LayoutInflater.from(context), container, false)
        bindingDialog = AddAlertDialogBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.fabAddAlert.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                showAddAlertDialoge()

            }
        })
        bindingDialog.ivClose.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                dialog.dismiss()
            }
        })
        bindingDialog.tvCalender.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val calendar = Calendar.getInstance()
                val year = calendar.get(Calendar.YEAR)
                val month = calendar.get(Calendar.MONTH)
                val day = calendar.get(Calendar.DAY_OF_MONTH)

                val datePickerDialog = DatePickerDialog(requireContext(), DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    bindingDialog.tvCalender.text = "$dayOfMonth $monthOfYear, $year" }, year, month, day)
                datePickerDialog.show()
            }
        })
        bindingDialog.tvStartTime.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val (hour, minute) = showTimePicker()
                mTimePicker = TimePickerDialog(requireContext(),
                    { view, hourOfDay, minute ->
                        bindingDialog.tvStartTime.text = String.format("%d : %d", hourOfDay, minute)
                    }, hour, minute, false
                )
                bindingDialog.tvStartTime.setOnClickListener {
                    mTimePicker.show()
                }
            }

        })
        bindingDialog.tvEndTime.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                val (hour, minute) = showTimePicker()
                mTimePicker = TimePickerDialog(requireContext(),
                    { view, hourOfDay, minute ->
                        bindingDialog.tvEndTime.text = String.format("%d : %d", hourOfDay, minute)
                    }, hour, minute, false
                )
                bindingDialog.tvEndTime.setOnClickListener {
                    mTimePicker.show()
                }
            }

        })
        bindingDialog.addAlertBtn.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View?) {
                //get the data and set it into rv
                dialog.dismiss()
            }
        })
    }

    private fun showTimePicker(): Pair<Int, Int> {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        return Pair(hour, minute)
    }

    //==========================================================================
    private fun showAddAlertDialoge() {
        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bindingDialog.root)
        dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)

    }


}