package com.example.a.ui.fragment;

import android.arch.lifecycle.LiveData;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.a.R;
import com.example.a.entity.Link;
import com.example.a.presentor.HistoryView;
import com.example.a.presentor.Presenter;
import com.example.a.ui.adapter.LinkAdapter;


import java.util.Date;
import java.util.List;


public class HistoryFragment extends Fragment implements HistoryView {
    View view;
    RecyclerView recyclerView;
    LinkAdapter linkAdapter = new LinkAdapter();

    Presenter presenter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if(presenter == null){
            // я блять в душе не ебу как так работает )0)
            presenter = new Presenter(this, this);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.history_fragment, container, false);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        // инициализация recyclerView и LayoutManager
        recyclerView = view.findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(linearLayoutManager);

        // добавление ссылки в бд // ВРЕМЕННО
        Link exampleLink = new Link("example", 1, new Date(System.currentTimeMillis()));
        Link exampleLink2 = new Link("example2", 1, new Date(System.currentTimeMillis()));
        presenter.insertLink(exampleLink);
        presenter.insertLink(exampleLink2);

        //по смыслу должно быть все понятно
        presenter.getImageList();
        recyclerView.setAdapter(linkAdapter);

    }

    @Override
    public void setLinksList(final LiveData<List<Link>> linkList) {
        // Красиво, правда ?) подписываемся на лайв дату и пхаем список ссылок в сет
        linkList.observe(this, links -> linkAdapter.setData(links));
        linkAdapter.notifyDataSetChanged();
    }

    // TO DO: заветруть сюда интенты и тд
    @Override
    public void exportToAppB() {

    }
}