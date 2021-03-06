package com.example.dechproduct.rebooapplicationproject.fragments

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.dechproduct.rebooapplicationproject.R
import com.example.dechproduct.rebooapplicationproject.adapter.EffectCatalogAdapter
import com.example.dechproduct.rebooapplicationproject.model.MockEffectCatalogDataItem
import com.example.dechproduct.rebooapplicationproject.model.MockExampleDataItem
import kotlinx.android.synthetic.main.fragment_effect_catalog.*


class EffectCatalogFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_effect_catalog, container, false)



    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val exampleDataItem = generateAudioList(500)
        recycler_view_effect_catalog.adapter = EffectCatalogAdapter(exampleDataItem)
        recycler_view_effect_catalog.layoutManager = LinearLayoutManager(context)
        recycler_view_effect_catalog.setHasFixedSize(true)

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onDestroy() {
        super.onDestroy()
    }



    //Mock เฉยๆ ให้ back ไป sep algo แล้วเอาเข้า viewmodel เองเน้อ
    private fun generateAudioList(size:Int) : List<MockEffectCatalogDataItem>{
        val list = ArrayList <MockEffectCatalogDataItem>()
        for (i in 0 until size){
            val drawable = when (i%3){
                0 -> R.drawable.ic_favorite
                1 -> R.drawable.ic_home
                else -> R.drawable.ic_music
            }
            val item = MockEffectCatalogDataItem(drawable, "Item $i", "TEST EFFECT","Pitch 5.01")
            list += item
        }
        return list


    }


}