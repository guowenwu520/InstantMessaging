package com.cd.myluntan.contract;

public interface SearchContract {
    interface View{
        void searchSuccess();
        void searchFailed(String err);
        void refreshSearchSuccess();
    }
    interface Presenter{
        void loadSearchMessage(String message);

        void refreshSearch(String searchStr);

        void loadMoreSearch(String searchStr);
    }
}
