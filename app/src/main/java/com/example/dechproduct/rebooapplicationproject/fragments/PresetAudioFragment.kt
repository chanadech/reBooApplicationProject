package com.example.dechproduct.rebooapplicationproject.fragments

import android.os.Build
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.SeekBar
import androidx.annotation.RequiresApi
import androidx.lifecycle.ViewModelProvider
import com.example.dechproduct.rebooapplicationproject.R
import com.example.dechproduct.rebooapplicationproject.databinding.FragmentPresetAudioBinding
import com.example.dechproduct.rebooapplicationproject.model.Categories
import com.example.dechproduct.rebooapplicationproject.model.Preset
import com.example.dechproduct.rebooapplicationproject.viewmodel.PresetAudioViewModel
import kotlinx.android.synthetic.main.fragment_preset_audio.*


class PresetAudioFragment : Fragment(R.layout.fragment_preset_audio) {

    private var _binding : FragmentPresetAudioBinding? = null
    private val binding get() =  _binding!!
    private lateinit var viewModel: PresetAudioViewModel


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPresetAudioBinding.inflate(
            inflater, container, false
        )
        viewModel = ViewModelProvider(this).get(PresetAudioViewModel::class.java)
        binding.ibCover.setOnClickListener { record() }
        binding.seekBar.setOnSeekBarChangeListener(changePitch())
        binding.ibFavorite.setOnClickListener {addPreset()}
        binding.ibDelete.setOnClickListener {deletePreset()}

        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setUpCategories()

    }

    private fun setUpCategories(){
        val categories = mutableListOf<String>()
        Categories.values().forEach { categories.add(it.name) }
        val arrayAdapter =
            ArrayAdapter(requireActivity(), android.R.layout.simple_spinner_item, categories)
        category_spinner.adapter = arrayAdapter

    }

    @RequiresApi(Build.VERSION_CODES.M)
    private fun record() {
        viewModel.record()
    }

    private fun changePitch(): SeekBar.OnSeekBarChangeListener {
        // Set a SeekBar change listener
        return object : SeekBar.OnSeekBarChangeListener {

            override fun onProgressChanged(seekBar: SeekBar, i: Int, b: Boolean) {
                // Display the current progress of SeekBar
                binding.pitch.text = "$i"
            }

            override fun onStartTrackingTouch(seekBar: SeekBar) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar) {
            }
        }
    }

    private fun getPitch(): Float {
        return binding.pitch.text.toString().toFloat()
    }

    private fun getPreset(): Preset {
        return Preset(binding.categorySpinner.selectedItem.toString(), getPitch(), 1f)
    }

    private fun addPreset() {
        viewModel.addPreset(getPreset())
    }

    private fun deletePreset() {
        viewModel.deletePreset()
    }

    override fun onDestroy() {
        _binding = null
        super.onDestroy()
    }
}