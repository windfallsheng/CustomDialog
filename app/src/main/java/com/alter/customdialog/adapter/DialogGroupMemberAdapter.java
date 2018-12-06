package com.alter.customdialog.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.alter.customdialog.R;
import com.alter.customdialog.entity.GroupMemberEntity;
import com.jaeger.ninegridimageview.NineGridImageView;
import com.jaeger.ninegridimageview.NineGridImageViewAdapter;
import com.nostra13.universalimageloader.core.ImageLoader;

import java.util.List;


/**
 * @CreateDate: 2018/1/26
 * @Author: lzsheng
 * @Description: {@link com.alter.customdialog.view.CommonShareToDialog} 类的数据填充适配器，用来展示成员及群组头像及名称，根据不同的数据类型，展示不同的UI效果
 * @Version:
 */
public class DialogGroupMemberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private Context mContext;
    private List<GroupMemberEntity> mGroupMemberEntities;
    private OnRecyclerViewItemClick onRecyclerViewItemClick;
    private NineGridImageViewAdapter<String> mGridImageAdapter;

    private final int TYPE_MEMEBER_SINGLE = 1;       // 单个成员，显示头像、左边显示名字
    private final int TYPE_MEMEBER_MULTIPLE = 2;     // 多个成员，只显示头像
    private final int TYPE_GROUP_SINGLE = 3;         // 单个群组，显示头像、左边显示名字
    private final int TYPE_GROUP_MULTIPLE = 4;       // 多个群组，只显示头像
    private final int TYPE_MEMBER_MULTIPLE_FULL = 5; // 多个成员，显示头像、下边显示名字

    public DialogGroupMemberAdapter(Context context) {
        this.mContext = context;
        mGridImageAdapter = new NineGridImageViewAdapter<String>() {

            @Override
            protected void onDisplayImage(Context context, ImageView imageView, String s) {
                ImageLoader.getInstance().displayImage(s, imageView);
            }

            @Override
            protected ImageView generateImageView(Context context) {
                return super.generateImageView(context);
            }

            @Override
            protected void onItemImageClick(Context context, int index, List<String> photoList) {
//                showBigPicture(context, photoList.get(index));
            }
        };
    }

    public void setGroupMemberEntities(List<GroupMemberEntity> groupMemberEntities) {
        mGroupMemberEntities = groupMemberEntities;
    }

    public void setOnRecyclerViewItemClick(OnRecyclerViewItemClick onRecyclerViewItemClick) {
        this.onRecyclerViewItemClick = onRecyclerViewItemClick;
    }

    //根据数据的类型memeberType,返回不同的ItemViewType
    @Override
    public int getItemViewType(int position) {
        if (mGroupMemberEntities != null && mGroupMemberEntities.size() > 0) {
            int memberType = mGroupMemberEntities.get(position).getMemeberType();
            if (memberType == TYPE_MEMEBER_SINGLE) {
                return TYPE_MEMEBER_SINGLE;
            } else if (memberType == TYPE_MEMEBER_MULTIPLE) {
                return TYPE_MEMEBER_MULTIPLE;
            } else if (memberType == TYPE_GROUP_SINGLE) {
                return TYPE_GROUP_SINGLE;
            } else if (memberType == TYPE_GROUP_MULTIPLE) {
                return TYPE_GROUP_MULTIPLE;
            } else if (memberType == TYPE_MEMBER_MULTIPLE_FULL) {
                return TYPE_MEMBER_MULTIPLE_FULL;
            }
        }
        return 0;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_MEMEBER_SINGLE:
                HolderMemeberSingle holderMemeberSingle = new HolderMemeberSingle(
                        LayoutInflater.from(mContext).inflate(R.layout.item_member_single, parent, false));
                return holderMemeberSingle;
            case TYPE_MEMEBER_MULTIPLE:
                HolderMemeberMultiple holderMemeberMultiple = new HolderMemeberMultiple(
                        LayoutInflater.from(mContext).inflate(R.layout.item_member_multiple, parent, false));
                return holderMemeberMultiple;
            case TYPE_GROUP_SINGLE:
                HolderGroupSingle holderGroupSingle = new HolderGroupSingle(
                        LayoutInflater.from(mContext).inflate(R.layout.item_group_single, parent, false));
                return holderGroupSingle;
            case TYPE_GROUP_MULTIPLE:
                HolderGroupMultiple holderGroupMultiple = new HolderGroupMultiple(
                        LayoutInflater.from(mContext).inflate(R.layout.item_group_multiple, parent, false));
                return holderGroupMultiple;
            case TYPE_MEMBER_MULTIPLE_FULL:
                HolderMemberMultipleFull holderMemberMultipleFull = new HolderMemberMultipleFull(
                        LayoutInflater.from(mContext).inflate(R.layout.item_member_multiple_full, parent, false));
                return holderMemberMultipleFull;
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (mGroupMemberEntities != null) {
            GroupMemberEntity groupMemberEntity = mGroupMemberEntities.get(position);
            int memberType = groupMemberEntity.getMemeberType();
            switch (memberType) {
                case TYPE_MEMEBER_SINGLE:
                    ImageLoader.getInstance().displayImage(groupMemberEntity.getImageUrls().get(0), ((HolderMemeberSingle) holder).ivMemberPic);
                    ((HolderMemeberSingle) holder).tvName.setText(groupMemberEntity.getName());
                    break;
                case TYPE_MEMEBER_MULTIPLE:
                    ImageLoader.getInstance().displayImage(groupMemberEntity.getImageUrls().get(0), ((HolderMemeberMultiple) holder).ivMemberPic);
                    break;
                case TYPE_GROUP_SINGLE:
                    ((HolderGroupSingle) holder).ivMemberPic.setAdapter(mGridImageAdapter);
                    ((HolderGroupSingle) holder).ivMemberPic.setImagesData(groupMemberEntity.getImageUrls());
                    ((HolderGroupSingle) holder).tvName.setText(groupMemberEntity.getName());
                    break;
                case TYPE_GROUP_MULTIPLE:
                    ((HolderGroupMultiple) holder).ivMemberPic.setAdapter(mGridImageAdapter);
                    ((HolderGroupMultiple) holder).ivMemberPic.setImagesData(groupMemberEntity.getImageUrls());
                    break;
                case TYPE_MEMBER_MULTIPLE_FULL:
                    ImageLoader.getInstance().displayImage(groupMemberEntity.getImageUrls().get(0), ((HolderMemberMultipleFull) holder).ivMemberPic);
                    ((HolderMemberMultipleFull) holder).tvName.setText(groupMemberEntity.getName());
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public int getItemCount() {
        if (mGroupMemberEntities != null && mGroupMemberEntities.size() > 0) {
            return mGroupMemberEntities.size();
        }
        return 0;
    }

    class HolderMemeberSingle extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivMemberPic;
        TextView tvName;

        public HolderMemeberSingle(View itemView) {
            super(itemView);
            ivMemberPic = (ImageView) itemView.findViewById(R.id.imageview_pic_member_single);
            tvName = (TextView) itemView.findViewById(R.id.textview_name_single);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onRecyclerViewItemClick != null) {
                onRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
            }
        }
    }

    class HolderMemeberMultiple extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivMemberPic;

        public HolderMemeberMultiple(View itemView) {
            super(itemView);
            ivMemberPic = (ImageView) itemView.findViewById(R.id.imageview_pic_member_multiple);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onRecyclerViewItemClick != null) {
                onRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
            }
        }
    }

    class HolderGroupSingle extends RecyclerView.ViewHolder implements View.OnClickListener {

        NineGridImageView ivMemberPic;
        TextView tvName;

        public HolderGroupSingle(View itemView) {
            super(itemView);
            ivMemberPic = (NineGridImageView) itemView.findViewById(R.id.imageview_pic_group_single);
            tvName = (TextView) itemView.findViewById(R.id.textview_name_group_single);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onRecyclerViewItemClick != null) {
                onRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
            }
        }
    }


    class HolderGroupMultiple extends RecyclerView.ViewHolder implements View.OnClickListener {

        NineGridImageView ivMemberPic;

        public HolderGroupMultiple(View itemView) {
            super(itemView);
            ivMemberPic = (NineGridImageView) itemView.findViewById(R.id.imageview_pic_group_multiple);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onRecyclerViewItemClick != null) {
                onRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
            }
        }
    }

    class HolderMemberMultipleFull extends RecyclerView.ViewHolder implements View.OnClickListener {

        ImageView ivMemberPic;
        TextView tvName;

        public HolderMemberMultipleFull(View itemView) {
            super(itemView);
            ivMemberPic = (ImageView) itemView.findViewById(R.id.imageview_pic_member_full);
            tvName = (TextView) itemView.findViewById(R.id.textview_name_full);
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View view) {
            if (onRecyclerViewItemClick != null) {
                onRecyclerViewItemClick.onItemClick(view, getAdapterPosition());
            }
        }
    }

    /**
     * item点击接口
     */
    public interface OnRecyclerViewItemClick {
        void onItemClick(View view, int position);
    }
}
