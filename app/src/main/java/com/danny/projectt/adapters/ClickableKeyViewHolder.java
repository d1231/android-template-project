package com.danny.projectt.adapters;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.danny.projectt.Key;
import com.danny.projectt.R;

import butterknife.Bind;
import butterknife.ButterKnife;

class ClickableKeyViewHolder extends RecyclerView.ViewHolder {


    @Bind(R.id.letter)
    TextView textView;

    public ClickableKeyViewHolder(View itemView) {

        super(itemView);

        ButterKnife.bind(this, itemView);

    }

    public void bind(Key key, KeyboardAdapter.KeyClickedListener listener) {

        textView.setText(key.name());

        textView.setOnClickListener(v -> listener.onKeyClicked(key));
    }


}
