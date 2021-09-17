package com.example.wordsapp

import android.os.Bundle
import android.view.*
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.wordsapp.databinding.FragmentLetterListBinding

// 얘는 어떻게 생성된거임? ㅋㅋㅋ

class LetterListFragment : Fragment() {
    private var _binding: FragmentLetterListBinding? = null
    //property with underScore typically used for indirect access

    private val binding get() = _binding!!
    //get() means this property is get-only.
    //it means you can't assign 'binding' to something-else

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentLetterListBinding.inflate(inflater, container, false)
        val view = binding.root
        return view
    }

    //create a property for the recycler view
    private lateinit var recyclerView: RecyclerView

    //set the value of recycler view by this method
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        recyclerView = binding.recyclerView
        chooseLayout()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }

    /* fragment 와 Activity 는 onCreateOptionMenu() 를 사용함에 있어 다름.
    Activity class 는 menuInflater 라는 프로퍼티를 가지지만, Fragment 는 없다.
    대신 menuInflater 가 onCreateOptionMenu()를 직접 통과한다.
    또한 fragment 에서는 onCreateOptionMenu() 가 return statement 가 필요없음
    */

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.layout_menu, menu)

        val layoutButton = menu.findItem(R.id.action_switch_layout)
        setIcon(layoutButton)

    }
    private fun setIcon(menuItem: MenuItem?) {
        if (menuItem == null) {
            return
        }

        menuItem.icon =
            if (isLinearLayoutManager) ContextCompat.getDrawable(this, R.drawable.ic_grid_layout)
            else ContextCompat.getDrawable(this, R.drawable.ic_linear_layout)

    }
    //choose layout of recyclerView and set its adapter
    private fun chooseLayout() {
        if (isLinearLayoutManager) {
            recyclerView.layoutManager = LinearLayoutManager(this)
        } else {
            recyclerView.layoutManager = GridLayoutManager(this, 4)
        }
        recyclerView.adapter = LetterAdapter()
    }
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        return when (item.itemId){
            R.id.action_switch_layout-> {
                isLinearLayoutManager = !isLinearLayoutManager
                chooseLayout()
                setIcon(item)

                return true
            }
            else -> super.onOptionsItemSelected(item)
        }

    }

}
