package com.example.joy

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.joy.adapter.SavedSongRVAdapter
import com.example.joy.data.Song
import com.example.joy.data.SongDatabase
import com.example.joy.databinding.FragmentLockerSavedsongBinding

class SavedSongFragment : Fragment() {
    lateinit var binding: FragmentLockerSavedsongBinding
    lateinit var songDB: SongDatabase

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentLockerSavedsongBinding.inflate(inflater, container, false)

        songDB = SongDatabase.getInstance(requireContext())!!

        val savedSongRVAdapter = SavedSongRVAdapter()
        binding.savedSongRv.adapter = savedSongRVAdapter
        binding.savedSongRv.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL, false)

        savedSongRVAdapter.addSongs(songDB.songDao().getLikedSongs(true) as ArrayList<Song>)

        savedSongRVAdapter.setMyItemClickListener(object: SavedSongRVAdapter.MyItemClickListener {
            override fun onRemoveSong(songId: Int, position: Int) {
                songDB.songDao().updateIsLikeById(false, songId)
                (requireActivity() as MainActivity).setLike(songId)
                savedSongRVAdapter.removeSong(position)
            }
        })

        return binding.root
    }
}