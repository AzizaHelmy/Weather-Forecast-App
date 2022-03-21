package com.example.weatherforecastapp.ui.alerts

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.example.weatherforecastapp.databinding.AddAlertDialogBinding
import com.example.weatherforecastapp.ui.alerts.model.Alert
import java.util.*
import java.util.concurrent.TimeUnit

class Dialoge: DialogFragment() {

    private lateinit var builder: AlertDialog.Builder
    private lateinit var bindingDialog: AddAlertDialogBinding
    private lateinit var dialog: AlertDialog
    private lateinit var mTimePicker: TimePickerDialog
    private var startDate: Long = 0L
    private var endDate: Long = 0L
    private var startTime: Long = 0L
    private var endTime: Long = 0L

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        bindingDialog =
            AddAlertDialogBinding.inflate(LayoutInflater.from(context), container, false)
        bindingDialog.ivClose.setOnClickListener { dialog.dismiss() }
        bindingDialog.tvStartDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    bindingDialog.tvStartDate.text = "$dayOfMonth $monthOfYear,$year"
                    startDate = calendar.timeInMillis
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        bindingDialog.tvEndDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)

            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    bindingDialog.tvEndDate.text = "$dayOfMonth $monthOfYear, $year"
                    endDate = calendar.timeInMillis
                },
                year,
                month,
                day
            )
            datePickerDialog.show()
        }
        bindingDialog.tvStartTime.setOnClickListener {
            val (hour, minute) = showTimePicker()
            mTimePicker = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    bindingDialog.tvStartTime.text = String.format("%d : %d", hourOfDay, minute)
                    startTime =
                        (TimeUnit.MINUTES.toSeconds(minute.toLong()) + TimeUnit.HOURS.toSeconds(hour.toLong()))
                }, hour, minute, false
            )
            bindingDialog.tvStartTime.setOnClickListener {
                mTimePicker.show()
            }
        }
        bindingDialog.tvEndTime.setOnClickListener {
            val (hour, minute) = showTimePicker()
            mTimePicker = TimePickerDialog(
                requireContext(),
                { view, hourOfDay, minute ->
                    bindingDialog.tvEndTime.text = String.format("%d : %d", hourOfDay, minute)
                    endTime =
                        (TimeUnit.MINUTES.toSeconds(minute.toLong()) + TimeUnit.HOURS.toSeconds(hour.toLong()))

                }, hour, minute, false
            )
            bindingDialog.tvEndTime.setOnClickListener {
                mTimePicker.show()
            }
        }

        bindingDialog.addAlertBtn.setOnClickListener {
            //get the data and set it into rv
            // get alert obj and pass it to viewmodel
            //val alert = Alert("", startDate, endDate, startTime, endTime, "Rain", "Azza")
           // alertViewModel.insertAlert(alert)
            //checkEmptyList()
            // setWorker()
            dialog.dismiss()
        }

        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bindingDialog.root)
        dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
        return super.onCreateView(inflater, container, savedInstanceState)


    }
    private fun showTimePicker(): Pair<Int, Int> {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        return Pair(hour, minute)
    }

}