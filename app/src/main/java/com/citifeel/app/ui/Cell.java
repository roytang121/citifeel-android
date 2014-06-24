package com.citifeel.app.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.citifeel.app.R;
import com.citifeel.app.model.CellModel;
import com.squareup.picasso.Picasso;

/**
 * Created by roytang on 24/6/14.
 * Base class for a cell
 */
public class Cell extends LinearLayout{

    private ImageView image;
    private TextView region, landmark, tags;
    CellModel model;

    public Cell(Context context) {
        super(context);
        init();
    }

    public Cell(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public Cell(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);
        init();
    }

    private void init() {
        Cell.inflate(getContext(), R.layout.view_cell, this);

        /* view resources */
        image = (ImageView) findViewById(R.id.image);
        region = (TextView) findViewById(R.id.region);
        landmark = (TextView) findViewById(R.id.landmark);
        tags = (TextView) findViewById(R.id.tags);

        /*debug*/
        model = new CellModel();
        update();
    }

    public void setModel(CellModel model) {
        this.model = model;
        update();
    }

    private void update() {
        if (model == null) return;

        /* set the view resources by model's data */
        Picasso.with(getContext()).load("https://fbcdn-sphotos-h-a.akamaihd.net/hphotos-ak-xpa1/t1.0-9/1525592_10204357461803810_1208550778448891846_n.jpg")
            .fit()
            .centerCrop()
            .into(image);
    }
}
