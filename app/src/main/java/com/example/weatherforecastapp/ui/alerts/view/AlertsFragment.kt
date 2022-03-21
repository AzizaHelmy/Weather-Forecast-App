package com.example.weatherforecastapp.ui.alerts.view

import android.annotation.SuppressLint
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.activity.OnBackPressedCallback
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.Navigation
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.work.*
import com.example.weatherforecastapp.R
import com.example.weatherforecastapp.databinding.AddAlertDialogBinding
import com.example.weatherforecastapp.databinding.FragmentAlertsBinding
import com.example.weatherforecastapp.ui.alerts.AlertWorker
import com.example.weatherforecastapp.ui.alerts.model.Alert
import com.example.weatherforecastapp.ui.alerts.viewmodel.AlertViewModel
import com.google.android.material.snackbar.Snackbar
import java.util.*
import java.util.concurrent.TimeUnit

class AlertsFragment : Fragment(), AlertOnClickListener {
    private lateinit var binding: FragmentAlertsBinding
    private lateinit var builder: AlertDialog.Builder
    private lateinit var bindingDialog: AddAlertDialogBinding
    private lateinit var dialog: AlertDialog
    private lateinit var mTimePicker: TimePickerDialog
    private lateinit var alertAdapter: AlertAdapter
    private lateinit var alerts: List<Alert>

    private var startDate: Long = 0L
    private var endDate: Long = 0L
    private var startTime: Long = 0L
    private var endTime: Long = 0L


    //KTX
    private val alertViewModel by viewModels<AlertViewModel>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val pressedCallback: OnBackPressedCallback = object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                Navigation.findNavController(view!!).navigate(R.id.homeFragment)
            }
        }
        requireActivity().onBackPressedDispatcher.addCallback(this, pressedCallback)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlertsBinding.inflate(LayoutInflater.from(context), container, false)
        if(parentFragment !=null){
           container?.removeAllViews()
        }
        bindingDialog =
            AddAlertDialogBinding.inflate(LayoutInflater.from(context), container, false)
        return binding.root
    }

    @SuppressLint("SetTextI18n")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        alertViewModel.getAllAlerts().observe(requireActivity()) {
            alerts = it
            setUpRecyclerView()
            checkEmptyList()
        }
        binding.fabAddAlert.setOnClickListener {
            //Navigation.findNavController(view).navigate(R.id.action_alertsFragment_to_dialoge)
              showAddAlertDialoge()
        }
        bindingDialog.ivClose.setOnClickListener { dialog.dismiss() }
        bindingDialog.tvStartDate.setOnClickListener {
            val calendar = Calendar.getInstance()
            val year = calendar.get(Calendar.YEAR)
            val month = calendar.get(Calendar.MONTH)
            val day = calendar.get(Calendar.DAY_OF_MONTH)
            val datePickerDialog = DatePickerDialog(
                requireContext(),
                DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
                    bindingDialog.tvStartDate.text = "$dayOfMonth/$monthOfYear/$year"
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
                    bindingDialog.tvEndDate.text = "$dayOfMonth/$monthOfYear/$year"
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
                    bindingDialog.tvStartTime.text = String.format("%d : %d ", hourOfDay, minute )
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
            val alert = Alert("", bindingDialog.tvStartDate.text.toString(), bindingDialog.tvEndDate.text.toString(), bindingDialog.tvStartTime.text.toString(), bindingDialog.tvEndTime.text.toString())
            alertViewModel.insertAlert(alert)
            checkEmptyList()
             setWorker()
            dialog.dismiss()
        }
    }

    //==========================================
    private fun setWorker() {

        val periodicWorkRequest = PeriodicWorkRequest.Builder(AlertWorker::class.java, 24, TimeUnit.HOURS)
            .setInputData(Data.Builder()
                    .putLong("alertTime", startTime)
                    .putString("alertName", "Alert")
                    .build()).build()
        WorkManager.getInstance(requireContext()).enqueue(periodicWorkRequest)
    }
    //=============================================
    private fun setUpRecyclerView() {
        val layoutManager = LinearLayoutManager(requireActivity())
        layoutManager.orientation = LinearLayoutManager.VERTICAL
        binding.rvAlerts.layoutManager = layoutManager
        alertAdapter = AlertAdapter(alerts, requireContext(), this)
        binding.rvAlerts.adapter = alertAdapter
    }
    //================================================
    private fun showTimePicker(): Pair<Int, Int> {
        val mcurrentTime = Calendar.getInstance()
        val hour = mcurrentTime.get(Calendar.HOUR_OF_DAY)
        val minute = mcurrentTime.get(Calendar.MINUTE)
        return Pair(hour, minute)
    }
    //===================================================================
    private fun showAddAlertDialoge() {
        builder = AlertDialog.Builder(requireContext())
        builder.setCancelable(false)
        builder.setView(bindingDialog.root)
        dialog = builder.create()
        dialog.show()
        dialog.window?.setBackgroundDrawableResource(android.R.color.transparent)
        dialog.setCancelable(false)
    }
    //===================================================================
    override fun onOptionClicked(alert: Alert) {
        //if need update => call update , call dialoge
        //if need to delet =>
        alertViewModel.deleteAlert(alert)
        showSnackBar(alert)
        checkEmptyList()
        alertAdapter.notifyDataSetChanged()
        WorkManager.getInstance(requireContext()).cancelAllWorkByTag(alert.id.toString())
    }

    //=========================================
    private fun checkEmptyList() {
        if (alerts.isEmpty()) {
            binding.ivEmpty.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.VISIBLE
            binding.rvAlerts.visibility = View.GONE
        } else {
            binding.ivEmpty.visibility = View.GONE
            binding.rvAlerts.visibility = View.VISIBLE
            binding.tvEmpty.visibility = View.GONE
        }
    }

    //==================================================
    private fun showSnackBar(alert: Alert) {
        val snackbar = Snackbar.make(binding.layoutAlert, "Removed from Fav!", Snackbar.LENGTH_LONG)
        snackbar.setTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        snackbar.setActionTextColor(ContextCompat.getColor(requireContext(), R.color.black))
        snackbar.setBackgroundTint(ContextCompat.getColor(requireContext(), R.color.purple_700))
        snackbar.setAction("undo") {
            alertViewModel.insertAlert(alert)
            Toast.makeText(requireContext(), "added Again!", Toast.LENGTH_SHORT).show()
        }.show()
    }


}