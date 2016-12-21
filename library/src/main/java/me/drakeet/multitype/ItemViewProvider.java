/*
 * Copyright 2016 drakeet. https://github.com/drakeet
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package me.drakeet.multitype;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView.ViewHolder;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import java.util.List;

/***
 * @author drakeet
 */
public abstract class ItemViewProvider<T, V extends ViewHolder> {

    /* internal */ int position;
    /* internal */ List<?> items ;


    /* @formatter:off */

    @NonNull
    protected abstract V onCreateViewHolder(@NonNull LayoutInflater inflater, @NonNull ViewGroup parent);

    protected abstract void onBindViewHolder(@NonNull V holder, @NonNull T t);

    /* @formatter:on */


    /**
     * Get the adapter position of current item,
     * the internal position equals to RecyclerView.ViewHolder#getAdapterPosition().
     *
     * @return the adapter position
     * @since v2.3.0
     */
    protected final int getPosition() {
        return position;
    }

    /**
     * 获取所在的数据列表，以便用来判断当前所处的“环境”，
     * 以便做一些特殊的操作，比如位置不同显示的ui不同（分割线）
     * @return 数据列表items
     * since v2.3.1
     */
    protected List<?> getItems() {
        return items;
    }
    /**
     * 判断当前item是否是当前这种viewType的开头
     * @param t 当前数据类型
     * @return true：是这种viewType的开头，否则false
     * @since v2.3.2
     */
    protected boolean isViewTypeStart(T t){
        int position = getPosition();
        if(position==0){//已经是顶部了
            return true;
        }
        Object preViewTypeBean = getItems().get(position - 1);//上一个类型
        if(t!=null&&preViewTypeBean!=null&&!t.getClass().getName().equals(preViewTypeBean.getClass().getName())){
            return true;
        }
        return false;
    }

    /**
     * 判断当前item是否是当前这种viewType的结尾
     * @param t 当前数据类型
     * @return true：是这种viewType的结尾，否则false
     * @since v2.3.2
     */
    protected boolean isViewTypeEnd(T t){
        int position = getPosition();
        if(position+1==items.size()){//已经是尾部了
            return true;
        }
        Object nextViewTypeBean = getItems().get(position + 1);//上一个类型
        if(t!=null&&nextViewTypeBean!=null&&!t.getClass().getName().equals(nextViewTypeBean.getClass().getName())){
            return true;
        }
        return false;
    }
}