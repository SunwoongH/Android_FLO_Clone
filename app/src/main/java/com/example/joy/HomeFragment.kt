package com.example.joy

import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.example.joy.databinding.FragmentHomeBinding

class HomeFragment : Fragment() {
    lateinit var binding: FragmentHomeBinding
    private lateinit var handler: Handler
    private var currentPage: Int = 0

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentHomeBinding.inflate(inflater, container, false)

        // 앨범 사진 클릭하면 AlbumFragment로 전환
        binding.itemAlbumCoverImgCardView01.setOnClickListener {
            (context as MainActivity).supportFragmentManager.beginTransaction().replace(R.id.main_frm, AlbumFragment()).commitAllowingStateLoss()
        }

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