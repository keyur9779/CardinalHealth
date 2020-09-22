package com.custombinding;

import androidx.databinding.BindingAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.data.remote.Resource;
import com.view.base.BaseAdapter;

import java.util.List;

/**
 * Binding adapters
 * <p>
 */
final public class CustomListBinding {

    private CustomListBinding() {
        // Private Constructor to hide the implicit one
    }

    @BindingAdapter(value = "resource")
    public static void setResource(RecyclerView recyclerView, Resource resource) {
        RecyclerView.Adapter adapter = recyclerView.getAdapter();
        if (adapter == null)
            return;

        if (resource == null || resource.getData() == null)
            return;

        if (adapter instanceof BaseAdapter) {
            ((BaseAdapter) adapter).setData((List) resource.getData());
        }
    }

}
