package com.example.joy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.joy.databinding.FragmentAlbumBinding
import com.google.android.material.tabs.TabLayoutMediator

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding
    private val tapInformation = arrayListOf("수록곡", "상세정보", "영상")

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)

        // 닫기 누르면 HomeFragment로 전환
        binding.albumBackIv.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, HomeFragment()).commitAllowingStateLoss()
        }

        // TabLayout와 ViewPager2의 Synchronization
        val albumAdapter = AlbumVPAdapter(this)
        binding.albumContentVp.adapter = albumAdapter
        TabLayoutMediator(binding.albumContentTb, binding.albumContentVp) {
            tap, position -> tap.text = tapInformation[position]
        }.attach()

        // 좋아요 버튼 on/off
        binding.albumLikeOffIv.setOnClickListener {
            setLikeStatus(false)
        }
        binding.albumLikeOnIv.setOnClickListener {
            setLikeStatus(true)
        }

        return binding.root
    }

    private fun setLikeStatus(isLike: Boolean) {
        if (isLike) {
            binding.albumLikeOffIv.visibility = View.VISIBLE
            binding.albumLikeOnIv.visibility = View.GONE
        } else {
            binding.albumLikeOffIv.visibility = View.GONE
            binding.albumLikeOnIv.visibility = View.VISIBLE
        }
    }
}