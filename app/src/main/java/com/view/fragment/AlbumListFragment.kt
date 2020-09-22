package com.view.fragment

import android.app.SearchManager
import android.content.Context
import android.os.Bundle
import android.view.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.test.R
import com.data.local.entity.AlbumEntity
import com.data.remote.Resource
import com.data.remote.Status
import com.test.databinding.FragmentListBinding
import com.utils.Constants
import com.utils.FragmentUtils
import com.view.adapter.AlbumListAdapter
import com.view.base.BaseFragment
import com.view.callbacks.AlbumListCallback
import com.viewmodel.AlbumListViewModel

/**
 * The article list fragment which will list the popular articles

 */
class AlbumListFragment : BaseFragment<AlbumListViewModel, FragmentListBinding?>(), AlbumListCallback {
    public override fun getViewModel(): Class<AlbumListViewModel>? {
        return AlbumListViewModel::class.java
    }

    override val layoutRes: Int
        get() = R.layout.fragment_list

    override fun onAlbumClicked(albumDataEntity: AlbumEntity) {
        if (null != activity) {
            val args = Bundle()
            args.putString(Constants.BUNDLE_KEY_ARTICLE_URL, albumDataEntity.thumbnailUrl)
            val detailFragment = AlbumDetailFragment()
            detailFragment.arguments = args
            FragmentUtils.replaceFragment(activity as AppCompatActivity?, detailFragment, R.id.fragContainer, true, FragmentUtils.TRANSITION_SLIDE_LEFT_RIGHT)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        dataBinding = DataBindingUtil.inflate(inflater, layoutRes, container, false)

        super.onCreateView(inflater, container, savedInstanceState)
        setHasOptionsMenu(true)
        dataBinding!!.recyclerView.layoutManager = LinearLayoutManager(activity)
        dataBinding!!.recyclerView.adapter = AlbumListAdapter(this)
        return dataBinding!!.root
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        viewModel?.albumListData
                ?.observe(this, Observer { listResource: Resource<List<AlbumEntity?>?>? ->
                    if (null != listResource && (listResource.status == Status.ERROR || listResource.status == Status.SUCCESS)) {
                        dataBinding!!.loginProgress.visibility = View.GONE
                    }
                    dataBinding!!.resource = listResource


                    // If the cached data is already showing then no need to show the error
                    if (null != dataBinding!!.recyclerView.adapter && dataBinding!!.recyclerView.adapter!!.itemCount > 0) {
                        dataBinding!!.errorText.visibility = View.GONE
                    }
                })
    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        if (null == activity) return
        val searchView: SearchView
        activity!!.menuInflater.inflate(R.menu.menu_main, menu)

        // Associate searchable configuration with the SearchView
        val searchManager = activity!!.getSystemService(Context.SEARCH_SERVICE) as SearchManager
        searchView = menu.findItem(R.id.action_search)
                .actionView as SearchView
        searchView.setSearchableInfo(searchManager
                .getSearchableInfo(activity!!.componentName))
        searchView.maxWidth = Int.MAX_VALUE

        // listening to search query text change
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                // filter recycler view when query submitted
                if (null != dataBinding!!.recyclerView.adapter) (dataBinding!!.recyclerView.adapter as AlbumListAdapter?)!!.filter.filter(query)
                return false
            }

            override fun onQueryTextChange(query: String): Boolean {
                // filter recycler view when text is changed
                if (null != dataBinding!!.recyclerView.adapter) (dataBinding!!.recyclerView.adapter as AlbumListAdapter?)!!.filter.filter(query)
                return false
            }
        })
        super.onCreateOptionsMenu(menu, inflater)
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        val id = item.itemId
        return if (id == R.id.action_search) {
            true
        } else super.onOptionsItemSelected(item)
    }

    companion object {
        fun newInstance(): AlbumListFragment {
            val args = Bundle()
            val fragment = AlbumListFragment()
            fragment.arguments = args
            return fragment
        }
    }
}