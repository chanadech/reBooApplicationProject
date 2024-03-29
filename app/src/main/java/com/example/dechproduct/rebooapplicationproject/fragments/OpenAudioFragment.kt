package com.example.dechproduct.rebooapplicationproject.fragments

import android.content.Context
import android.media.MediaMetadataRetriever
import android.net.Uri
import android.os.Bundle
import android.os.Environment
import android.provider.MediaStore
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.annotation.NonNull
import androidx.fragment.app.viewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dechproduct.rebooapplicationproject.R
import com.example.dechproduct.rebooapplicationproject.adapter.OpenAudioActivityAdapter
import com.example.dechproduct.rebooapplicationproject.databinding.FragmentOpenAudioBinding
import com.example.dechproduct.rebooapplicationproject.model.MockExampleDataItem
import com.example.dechproduct.rebooapplicationproject.viewmodel.OpenAudioViewModel
import kotlinx.android.synthetic.main.fragment_open_audio.*
import androidx.fragment.app.setFragmentResultListener
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import kotlinx.coroutines.flow.collect
import java.io.File
import java.io.IOException
import java.lang.Exception

//Normal

class OpenAudioFragment : Fragment(), OpenAudioActivityAdapter.OnItemClickListener {

    private val viewModel: OpenAudioViewModel by viewModels()

    private var _binding: FragmentOpenAudioBinding? = null
    private val binding get() = _binding!!


    private val exampleDataItem = generateAudioList(500)
    private val adapter = OpenAudioActivityAdapter(exampleDataItem, this)


    override fun onCreateView(
            inflater: LayoutInflater,
            container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentOpenAudioBinding.inflate(
                inflater, container, false
        )

        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val binding = FragmentOpenAudioBinding.bind(view)

        binding.apply {
            recycler_view.adapter = adapter
            recycler_view.layoutManager = LinearLayoutManager(context)
            recycler_view.setHasFixedSize(true)

            addItem.setOnClickListener { viewModel.onAddNewTaskClick() }
        }

        viewLifecycleOwner.lifecycleScope.launchWhenStarted {
            viewModel.tasksEvent.collect { event ->
                when (event) {
                    is OpenAudioViewModel.ExampleAddEvent.NavigateToPlayTaskScreen -> {
                        val action =
                                OpenAudioFragmentDirections.actionOpenAudioFragmentToPlayAudioFragment()
                        findNavController().navigate(action)
                    }
                }
            }
        }
    }


    //Mock เฉยๆ ให้ back ไป แยก algo แล้วเอาเข้า viewmodel เองเน้อ
    private fun generateAudioList(size: Int): List<MockExampleDataItem> {
        /*
        val list = ArrayList <MockExampleDataItem>()
        for (i in 0 until size){
            val drawable = when (i%3){
                0 -> R.drawable.ic_favorite
                1 -> R.drawable.ic_home
                else -> R.drawable.ic_music
            }
            val item = MockExampleDataItem(drawable, "Item $i", "Audio List Song Record ","12/2/22",12.22)
            list += item
        }
        return list
         */
        var ItemList = ArrayList<MockExampleDataItem>()
        val STORED_PATH = "/storage/emulated/0"
        val FILES = File(STORED_PATH).listFiles()
        val FILE_NAMES = arrayOfNulls<String>(FILES.size)
        FILES.mapIndexed { index, item ->
            FILE_NAMES[index] = item?.name}
            var _Call = 0

        Log.w("File", FILE_NAMES.size.toString())
        try {

            for (FileName in FILE_NAMES) {

                var it = File(STORED_PATH + File.separator + FileName)

                if(it.extension.equals("3gp")){
                    Log.w("File", it.toString())

                    var File_Duration = "0"
                    try{
                        var Retriever = MediaMetadataRetriever()
                        Retriever.setDataSource(context, Uri.fromFile(it))
                        File_Duration = Retriever.extractMetadata(MediaMetadataRetriever.METADATA_KEY_DURATION).toString()
                    }
                    catch (e:Exception){
                    }
                    finally{
                        ItemList.add(MockExampleDataItem(R.drawable.ic_music, "Item $_Call",
                                it.name, it.lastModified().toString(), File_Duration!!.toDouble()))
                        _Call++
                    }
                }
            }

        } catch (t: Throwable) {
            Toast.makeText(context, "No Items Found!", Toast.LENGTH_SHORT).show()
        } finally {
            return ItemList
        }

    }

    override fun onItemClick(position: Int) {
        Toast.makeText(context, "Item $position clicked", Toast.LENGTH_SHORT).show()
        val clickedItem = exampleDataItem[position]
        clickedItem.textName = "Clicked"
        adapter.notifyItemChanged(position)
    }


    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}



