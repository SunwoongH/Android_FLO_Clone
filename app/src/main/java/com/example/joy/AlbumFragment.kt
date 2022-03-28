package com.example.joy

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.example.joy.databinding.FragmentAlbumBinding

class AlbumFragment : Fragment() {
    lateinit var binding: FragmentAlbumBinding

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

        // 좋아요 버튼 on/off
        binding.albumLikeOffIv.setOnClickListener {
            setLikeStatus(false)
        }

        binding.albumLikeOnIv.setOnClickListener {
            setLikeStatus(true)
        }

        binding.songMyLikeOffIv.setOnClickListener {
            setMyLikeStatus(false)
        }

        binding.songMyLikeOnIv.setOnClickListener {
            setMyLikeStatus(true)
        }

        // Toast 메시지 출력
        binding.songLilacLayout.setOnClickListener {
            Toast.makeText(activity, "LILAC", Toast.LENGTH_SHORT).show()
        }
        binding.songSmileyLayout.setOnClickListener {
            Toast.makeText(activity, "SMILEY(Feat. BIBI)", Toast.LENGTH_SHORT).show()
        }
        binding.songRainLayout.setOnClickListener {
            Toast.makeText(activity, "Rain", Toast.LENGTH_SHORT).show()
        }
        binding.songParachuteLayout.setOnClickListener {
            Toast.makeText(activity, "parachute", Toast.LENGTH_SHORT).show()
        }
        binding.songShapeOfYouLayout.setOnClickListener {
            Toast.makeText(activity, "Shape of You", Toast.LENGTH_SHORT).show()
        }
        binding.songPowerUpLayout.setOnClickListener {
            Toast.makeText(activity, "Power Up", Toast.LENGTH_SHORT).show()
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

    private fun setMyLikeStatus(isLike: Boolean) {
        if (isLike) {
            binding.songMyLikeOffIv.visibility = View.VISIBLE
            binding.songMyLikeOnIv.visibility = View.GONE
        } else {
            binding.songMyLikeOffIv.visibility = View.GONE
            binding.songMyLikeOnIv.visibility = View.VISIBLE
        }
    }
}