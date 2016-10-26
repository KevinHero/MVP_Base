package com.apanda.base.Utils

import android.util.SparseArray
import android.view.View

object ViewHolderUtil {
    // I added a generic return type to reduce the casting noise in client code
    @SuppressWarnings("unchecked")
    operator fun <T : View> get(view: View, id: Int): T {
        var viewHolder: SparseArray<View>? = view.tag as SparseArray<View>
        if (viewHolder == null) {
            viewHolder = SparseArray<View>()
            view.tag = viewHolder
        }
        var childView: View? = viewHolder.get(id)
        if (childView == null) {
            childView = view.findViewById(id)
            viewHolder.put(id, childView)
        }
        return childView as T?
    }


    /*
	 * 在getview里面的用法
	 *
	 * @Override public View getView(int position, View convertView, ViewGroup
	 * parent) {
	 *
	 * if (convertView == null) { convertView = LayoutInflater.from(context)
	 * .inflate(R.layout.banana_phone, parent, false); }
	 *
	 * ImageView bananaView = ViewHolder.get(convertView, R.id.banana); TextView
	 * phoneView = ViewHolder.get(convertView, R.id.phone);
	 *
	 * BananaPhone bananaPhone = getItem(position);
	 * phoneView.setText(bananaPhone.getPhone());
	 * bananaView.setImageResource(bananaPhone.getBanana());
	 *
	 * return convertView; }
	 */


}
