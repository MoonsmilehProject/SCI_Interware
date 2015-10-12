package com.example.czamora.sci_interware;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.weiwangcn.betterspinner.library.BetterSpinner;

import java.util.ArrayList;
import java.util.List;

public class Principal extends Fragment {

    BetterSpinner dia;
    private static final int ITEMS_COUNT = 20;
    private List<String> mItems;

    private RecyclerView mRecyclerView;
    private RecyclerView.LayoutManager mLayoutManager;
    private RecyclerView.Adapter<CustomViewHolder> mAdapter;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        View v = inflater.inflate(R.layout.fragment_principal, container, false);

        initData();
        initRecyclerView(v);

        dia = (BetterSpinner)v.findViewById(R.id.dia);


        String[] dias = new String[] {
                "Hoy","Ayer"};

        //ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                //android.R.layout.simple_dropdown_item_1line, dias);

        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity().getApplicationContext(),
                R.layout.custom_spinner, dias);


        dia.setAdapter(adapter);

        final View actionOportunidad = v.findViewById(R.id.action_oportunidad);
        final View actionProyecto2 = v.findViewById(R.id.action_proyecto);
        final View actionRapida2 = v.findViewById(R.id.action_rapida);

        actionProyecto2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                actionOportunidad.setVisibility(actionOportunidad.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                actionRapida2.setVisibility(actionRapida2.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
            }
        });

        actionRapida2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                actionOportunidad.setVisibility(actionOportunidad.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                actionProyecto2.setVisibility(actionProyecto2.getVisibility() == View.GONE ? View.VISIBLE : View.GONE);
                Toast.makeText(getActivity().getApplicationContext(), "Captura realizada correctamente", Toast.LENGTH_SHORT).show();
            }
        });

        return v;
        }

    /************** RECYCLER VIEW **************/
    private void initData() {
        mItems = new ArrayList<>();
        for (int i = 0; i < ITEMS_COUNT; i++) {
            mItems.add("Item " + (i + 1));
        }
    }

    private void initRecyclerView(View v) {
        mRecyclerView = (RecyclerView)v.findViewById(R.id.recyclerView);
        mLayoutManager = new LinearLayoutManager(getActivity().getApplicationContext());
//        mLayoutManager = new GridLayoutManager(this, 2);
//        mLayoutManager = new StaggeredGridLayoutManager(3, StaggeredGridLayoutManager.VERTICAL);
        mRecyclerView.setLayoutManager(mLayoutManager);

        mAdapter = new RecyclerView.Adapter<CustomViewHolder>() {
            @Override
            public CustomViewHolder onCreateViewHolder(ViewGroup viewGroup, final int i) {
                View view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1
                        , viewGroup, false);
                view.setBackgroundResource(android.R.drawable.list_selector_background);
                return new CustomViewHolder(view);
            }

            @Override
            public void onBindViewHolder(CustomViewHolder viewHolder, int i) {
                viewHolder.mTextView.setText(mItems.get(i));
                viewHolder.mTextView.setPressed(false);
            }

            @Override
            public int getItemCount() {
                return mItems.size();
            }
        };
        mRecyclerView.setAdapter(mAdapter);

        SwipeDismissRecyclerViewTouchListener touchListener =
                new SwipeDismissRecyclerViewTouchListener(
                        mRecyclerView,
                        new SwipeDismissRecyclerViewTouchListener.DismissCallbacks() {
                            @Override
                            public boolean canDismiss(int position) {
                                return true;
                            }

                            @Override
                            public void onDismiss(RecyclerView recyclerView, int[] reverseSortedPositions) {
                                for (int position : reverseSortedPositions) {
                                    mItems.remove(position);
                                }
                                // do not call notifyItemRemoved for every item, it will cause gaps on deleting items
                                mAdapter.notifyDataSetChanged();
                            }
                        });
        mRecyclerView.setOnTouchListener(touchListener);
        // Setting this scroll listener is required to ensure that during ListView scrolling,
        // we don't look for swipes.
        mRecyclerView.setOnScrollListener(touchListener.makeScrollListener());
        mRecyclerView.addOnItemTouchListener(new RecyclerItemClickListener(mRecyclerView,
                new OnItemClickListener() {
                    @Override
                    public void onItemClick(View view, int position) {
                        Toast.makeText(getActivity().getApplicationContext(), "Clicked " + mItems.get(position),
                                Toast.LENGTH_SHORT).show();
                    }
                }));
    }

    private class CustomViewHolder extends RecyclerView.ViewHolder {

        private TextView mTextView;

        public CustomViewHolder(View itemView) {
            super(itemView);

            mTextView = (TextView) itemView.findViewById(android.R.id.text1);
        }
    }

    public interface OnItemClickListener {
        public void onItemClick(View view, int position);
    }

    public class RecyclerItemClickListener implements RecyclerView.OnItemTouchListener {
        private OnItemClickListener mListener;

        private static final long DELAY_MILLIS = 100;

        private RecyclerView mRecyclerView;
        private GestureDetector mGestureDetector;
        private boolean mIsPrepressed = false;
        private boolean mIsShowPress = false;
        private View mPressedView = null;

        public void onRequestDisallowInterceptTouchEvent (boolean disallowIntercept){

        }

        public RecyclerItemClickListener(RecyclerView recyclerView, OnItemClickListener listener) {
            mListener = listener;
            mRecyclerView = recyclerView;
            mGestureDetector = new GestureDetector(recyclerView.getContext(), new GestureDetector.SimpleOnGestureListener() {
                @Override
                public boolean onDown(MotionEvent e) {
                    mIsPrepressed = true;
                    mPressedView = mRecyclerView.findChildViewUnder(e.getX(), e.getY());
                    super.onDown(e);
                    return false;
                }

                @Override
                public void onShowPress(MotionEvent e) {
                    if (mIsPrepressed && mPressedView != null) {
                        mPressedView.setPressed(true);
                        mIsShowPress = true;
                    }
                    super.onShowPress(e);
                }

                @Override
                public boolean onSingleTapUp(MotionEvent e) {
                    if (mIsPrepressed && mPressedView != null) {
                        mPressedView.setPressed(true);
                        final View pressedView = mPressedView;
                        pressedView.postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                pressedView.setPressed(false);
                            }
                        }, DELAY_MILLIS);
                        mIsPrepressed = false;
                        mPressedView = null;
                    }
                    return true;
                }
            });
        }

        @Override
        public boolean onInterceptTouchEvent(RecyclerView view, MotionEvent e) {
            View childView = view.findChildViewUnder(e.getX(), e.getY());
            if (childView != null && mListener != null && mGestureDetector.onTouchEvent(e)) {
                mListener.onItemClick(childView, view.getChildPosition(childView));
            } else if (e.getActionMasked() == MotionEvent.ACTION_UP && mPressedView != null && mIsShowPress) {
                mPressedView.setPressed(false);
                mIsShowPress = false;
                mIsPrepressed = false;
                mPressedView = null;
            }
            return false;
        }

        @Override
        public void onTouchEvent(RecyclerView view, MotionEvent motionEvent) {
        }
    }


}
