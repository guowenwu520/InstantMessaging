package com.cd.myluntan.presenter;

import com.cd.myluntan.contract.SearchContract;
import com.cd.myluntan.entrty.Dynamic;

import java.util.ArrayList;

public class SearchPresenter implements SearchContract.Presenter {
    private SearchContract.View view;
    private ArrayList<Dynamic> searchList;

    private int currentPage = 1;//当前页，默认第一页
    private int pages = 1;//总页数，获取服务端数据
    private int rows = 20;//每页显示行数
    private int satrt = 0;//当前下标
    private int end = 20;//结束下标

    public SearchPresenter(SearchContract.View view) {
        this.view = view;
        this.searchList = new ArrayList<>();
    }

    @Override
    public void loadSearchMessage(String message) {
        //message 非空
        if (message != null && !message.isEmpty()) {
            //去服务器拉取数据

            //判断页面数
            getCurrentPage();
            //从数据库拉取数据 getSatrt(); getEnd();

            //加载数据
            searchList.clear();
            view.searchSuccess();
        } else {
            //加载失败
            view.searchFailed("没有搜索内容");
        }
    }

    @Override
    public void refreshSearch(String searchStr) {
        //searchStr 非空
        if (searchStr != null && !searchStr.isEmpty()) {
            //1.去服务器拉取数据 rows条数据
            //1.1.1拉去成功 获取总页数pages 当前页1
            pages = 5;
            currentPage = 1;
            //1.1.2设置数据数据
            searchList.clear();
            view.refreshSearchSuccess();
            //1.2.1失败
        } else {
            //加载失败
            view.searchFailed("没有搜索内容");
        }
    }

    @Override
    public void loadMoreSearch(String searchStr) {
        //searchStr 非空
        if (searchStr != null && !searchStr.isEmpty()) {
            //1.去服务器拉取数据 rows条数据
            //1.1.1拉去成功 获取总页数pages 当前页1
            pages = 5;
            currentPage++;
            //1.1.2设置数据数据
            searchList.clear();
            view.searchSuccess();
            //1.2.1失败
        } else {
            //加载失败
            view.searchFailed("没有搜索内容");
        }
    }

    public ArrayList<Dynamic> getSearchList() {
        return searchList;
    }

    public int getCurrentPage() {
        if (currentPage <= 1) {
            currentPage = 1;
        }
        if (currentPage >= pages) {
            currentPage = pages;
        }
        return currentPage;
    }

    public int getPages() {
        return pages;
    }

    public int getSatrt() {
        return (currentPage - 1) * rows;
    }

    public int getEnd() {
        return currentPage * rows;
    }
}
