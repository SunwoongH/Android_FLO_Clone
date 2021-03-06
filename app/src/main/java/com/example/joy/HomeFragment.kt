package com.example.joy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.joy.adapter.AlbumRVAdapter
import com.example.joy.adapter.BannerVPAdapter
import com.example.joy.adapter.PanelVPAdapter
import com.example.joy.data.Album
import com.example.joy.data.SongDatabase
import com.example.joy.databinding.FragmentHomeBinding
import com.google.gson.Gson

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private var currentPage: Int = 0
    private var albumData = ArrayList<Album>()
    private lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!
        albumData.addAll(songDB.albumDao().getAlbums())

        val albumRVAdapter = AlbumRVAdapter(albumData)
        binding.homeTodayMusicAlbumRv.adapter = albumRVAdapter
        binding.homeTodayMusicAlbumRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)

        albumRVAdapter.setMyItemClickListener(object: AlbumRVAdapter.MyItemClickListener {
            override fun onItemClick(album: Album) {
                changeAlbumFragment(album)
            }
            override fun onPlayClick(album: Album) {
                (requireActivity() as MainActivity).changeAlbumSong(album)
            }
//            override fun onRemoveAlbum(position: Int) {
//                albumRVAdapter.removeItem(position)
//            }
        })

        // 홈 패널
        val panelAdapter = PanelVPAdapter(this)
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        panelAdapter.addFragment(PanelFragment(R.drawable.img_first_album_default))
        binding.homePannelVp.adapter = panelAdapter
        val springDotsIndicator = binding.homeIndicator
        springDotsIndicator.setViewPager2(binding.homePannelVp)

        handler = Handler(Looper.getMainLooper())

        val autoViewPager = AutoViewPager(panelAdapter)
        autoViewPager.start()

        // 홈 광고 배너
        val bannerAdapter = BannerVPAdapter(this)
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp))
        bannerAdapter.addFragment(BannerFragment(R.drawable.img_home_viewpager_exp2))
        binding.homeBannerVp.adapter = bannerAdapter

        return binding.root
    }

    private fun changeAlbumFragment(album: Album) {
        (context as MainActivity).supportFragmentManager.beginTransaction()
            .replace(R.id.main_frm, AlbumFragment().apply {
                arguments = Bundle().apply {
                    val gson = Gson()
                    val albumJson = gson.toJson(album)
                    putString("album", albumJson)
                }
            })
            .commitAllowingStateLoss()
    }

    private fun switchViewPager(panelAdapter: PanelVPAdapter) {
        currentPage = (currentPage + 1) % panelAdapter.itemCount
        binding.homePannelVp.setCurrentItem(currentPage, true)
    }

    inner class AutoViewPager(private val panelAdapter: PanelVPAdapter): Thread() {
        override fun run() {
            super.run()
            try {
                while (true) {
                    sleep(2500)
                    handler.post {
                        switchViewPager(panelAdapter)
                    }
                }
            } catch (e: InterruptedException) {
                Log.d("VP", "AutoViewPager Thread가 죽었습니다.")
            }
        }
    }
}